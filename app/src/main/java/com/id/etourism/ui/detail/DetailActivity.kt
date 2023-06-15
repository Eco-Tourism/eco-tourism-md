package com.id.etourism.ui.detail

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.id.etourism.R
import com.id.etourism.databinding.ActivityDetailBinding
import com.id.etourism.databinding.ActivityMainBinding
import com.id.etourism.ml.ModelCitcat
import com.id.etourism.ui.location.LocationActivity
import com.id.etourism.ui.main.MainActivity
import com.id.etourism.ui.profile.ProfileActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAction()
        supportActionBar?.hide()
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val extras = intent.extras
        if (extras != null) {
            show(
                extras.getString(MainActivity.EXTRA_NAME, ""),
                extras.getString(MainActivity.EXTRA_CATEGORY, ""),
                extras.getString(MainActivity.EXTRA_ADDRESS, ""),
                extras.getString(MainActivity.EXTRA_LOCATION, ""),
                extras.getString(MainActivity.EXTRA_DESCRIPTION, ""),
                extras.getString(MainActivity.EXTRA_RATING, ""),
                extras.getString(MainActivity.EXTRA_IMAGE, ""),


                )

//            binding.btnLocation.setOnClickListener{
//                val lat = extras.getLong(MainActivity.EXTRA_LAT)
//                val long =extras.getLong(MainActivity.EXTRA_LONG)
//                val gmmIntentUri = Uri.parse("geo:$lat,$long")
//                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                mapIntent.setPackage("com.google.android.apps.maps")
//                mapIntent.resolveActivity(packageManager)?.let {
//                    startActivity(mapIntent)
//                }
        }
    }

    private fun initAction() {
        binding.imgBackDetail.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    fun show(name:String,category: String,address:String,location:String, description:String,rating:String, image: String ) {
        binding.tvVillage.text = name
        binding.tvCategory.text = category
        binding.tvAddress.text = address
        binding.tvDescription.text = description
        binding.tvRating.text = rating
        Glide.with(this)
            .load(image)
            .into(binding.ivVillage)

    }

}