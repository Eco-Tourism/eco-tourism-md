package com.id.etourism.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.id.etourism.R
import com.id.etourism.adapter.MainAdapter
import com.id.etourism.data.local.SessionManager
import com.id.etourism.data.network.model.Wisata
import com.id.etourism.databinding.ActivityMainBinding
import com.id.etourism.data.local.dummy.DummyData
import com.id.etourism.ui.detail.DetailActivity
import com.id.etourism.ui.profile.ProfileActivity
import com.id.etourism.utils.ExceptionState
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.Interpreter
import timber.log.Timber
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.LongBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var menu: Menu? = null
    @Inject lateinit var sessionManager: SessionManager
    private lateinit var binding : ActivityMainBinding
    private val viewmodel : MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private val wisata = arrayListOf<Wisata>()
    private var predictions :FloatArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.tag("siap").d(DummyData.generateDummy().toString())
        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.bg_action_bar))
        val layoutManager = LinearLayoutManager(this)
        binding.rvVillage.layoutManager = layoutManager
        adapter = MainAdapter(wisata)
        binding.rvVillage.adapter = adapter
        initUi()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.profile -> {
                val profile = Intent(this@MainActivity, ProfileActivity::class.java)
                profile.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(profile)

            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initUi() {
        viewmodel.getWisata()
        viewmodel.data.observe(this){ state ->
            when(state){
                is ExceptionState.Loading -> {
                    Timber.tag("loading").e("loading...")
                }
                is ExceptionState.Failure -> {
                    Timber.tag("gagal").e(state.error)
                }
                is ExceptionState.Success -> {
                    Timber.tag("succes").e("${state.data}")
                    wisata.clear()
                    val dataSort = state.data.sortedBy { it.Place_Id }.mapIndexed { index, wisata ->
                        Wisata(Place_Id = index.toLong(), Place_Name = wisata.Place_Name, Description = wisata.Description,Category = wisata.Category,
                        City = wisata.City, Price = wisata.Price, Rating = wisata.Rating, Image = wisata.Image, Coordinate = wisata.Coordinate)
                    }
                    val intArray = arrayListOf<Float>()
                    for (data in dataSort){
                        intArray.add(data.Place_Id!!.toFloat())
                        wisata.add(data)
                    }
                    adapter.notifyDataSetChanged()
                    val fixData = arrayListOf<ArrayList<Float>>()
                    intArray.forEach {
                        fixData.add(arrayListOf(1f,it,0f))
                    }
                    Timber.e("model data $fixData")
                    try {
                        modelTflite(wisata)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {

                            return false
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            searchList(newText)
                            return false
                        }

                    })
                    adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: Wisata,id:Long) {
                            val splitdata = data.Rating.toString()
                            val extras = Bundle()
                            val intent = Intent(this@MainActivity,DetailActivity::class.java)
                            extras.putString(EXTRA_IMAGE,data.Image)
                            extras.putString(EXTRA_NAME,data.Place_Name)
                            extras.putString(EXTRA_CATEGORY,data.Category)
                            extras.putString(EXTRA_LOCATION,data.Coordinate)
                            extras.putString(EXTRA_ADDRESS,data.City)
                            extras.putString(EXTRA_RATING,"${splitdata[2]}.${splitdata[3]}")
                            extras.putString(EXTRA_DESCRIPTION,data.Description)
                                intent.putExtras(extras)
                            startActivity(intent)
                        }
                    })


                }
            }

        }
    }
    fun searchList(text: String) {
        val searchList = java.util.ArrayList<Wisata>()
        for (data in wisata) {
            if ((data.Place_Name?.lowercase())
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(data)
            }
        }
        adapter.searchDataList(searchList)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_LOCATION = "extra_location"
        const val EXTRA_ADDRESS = "extra_address"
        const val EXTRA_RATING = "extra_rating"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_IMAGE = "extra_image"
    }
    private fun modelTflite(inputArray: ArrayList<Wisata>) {
        /**
         *
         *
        val model = ModelCitcat.newInstance(this)
        val dummyData = arrayListOf<ArrayList<Float>>()
        dummyData.add(arrayListOf(1f,0f))
        val numRows = dummyData.size
        val numCols = dummyData[0].size
        val totalElements = dummyData.size * dummyData[0].size
        val byteBuffer = ByteBuffer.allocateDirect(totalElements * 4)
        byteBuffer.order(ByteOrder.nativeOrder())
        for (row in dummyData) {
        for (value in row) {
        byteBuffer.putFloat(value)
        }
        }
        Timber.tag("check1").e(byteBuffer.toString())
        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, numRows,numCols), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

        Timber.tag("check2").e(byteBuffer.toString() + "dan ${inputFeature0.buffer} check $totalElements")
        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val predictions = outputFeature0.floatArray
        Timber.e("result $predictions")
        // Releases model resources if no longer used.
        model.close()
         */
        val interpreter = Interpreter(loadModelFile())
        val inputData = longArrayOf(sessionManager.dataLogin?.kota!!.toLong(), sessionManager.dataLogin?.suasana!!.toLong())
        val inputBuffer: ByteBuffer = ByteBuffer.allocateDirect(inputData.size * 8)
        inputBuffer.order(ByteOrder.nativeOrder())
        val inputLongBuffer: LongBuffer = inputBuffer.asLongBuffer()
        inputLongBuffer.put(inputData)
        val inputTensorIndex = 0
        val inputShape = interpreter.getInputTensor(inputTensorIndex).shape()
        require(inputShape.size == 2 && inputShape[1] == inputData.size)
        inputBuffer.rewind()
        val outputTensorIndex = 0
        val outputShape = interpreter.getOutputTensor(outputTensorIndex).shape()
        val outputBuffer = Array(1) { FloatArray(outputShape[1]) }
        interpreter.run(inputBuffer, outputBuffer)
        val recommendationScores = outputBuffer[0]
        Timber.tag("recomend").e(recommendationScores.toString())
        Timber.tag("datasebelum").e(wisata[0].Rating.toString())
//        for (score in recommendationScores) {
//            Timber.tag("output").e("Recommendation score: $score")
//            wisata.forEach {
//                it.Rating = score.toDouble()
//            }
//        }
        recommendationScores.forEachIndexed { index, fl ->
            wisata[index].Rating = fl.toDouble()
        }
        Timber.tag("datasetelah").e(wisata[0].Rating.toString())
        adapter.notifyDataSetChanged()
    }

    private fun loadModelFile(): MappedByteBuffer {
        val assetFileDescriptor = assets.openFd("model-citcat.tflite")
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

}