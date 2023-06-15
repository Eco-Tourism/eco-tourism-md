package com.id.etourism.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.id.etourism.data.network.model.Wisata
import com.id.etourism.databinding.ItemRowBinding
import java.util.Locale

class MainAdapter(private var wisata: List<Wisata>) : RecyclerView.Adapter<MainAdapter.WisataViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null
//    var filterWisata = ArrayList<Wisata>()
//    init {
//        filterWisata = wisata as ArrayList<Wisata>
//    }
    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class WisataViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wisata: Wisata) {
            binding.root.setOnClickListener {
                wisata.Place_Id?.let { it1 -> onItemClickCallback?.onItemClicked(wisata, it1) }
            }
            binding.apply {
                Glide.with(itemView)
                    .load(wisata.Image)
                    .into(imgDestination)
                tvVillageName.text = wisata.Place_Name
                tvRating.text = wisata.Rating.toString()
                tvDescription.text = wisata.Description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        holder.bind(wisata[position])
    }

    override fun getItemCount(): Int = wisata.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Wisata,id:Long)
    }
    fun searchDataList(searchList: List<Wisata>) {
        wisata = searchList
        notifyDataSetChanged()
    }
}