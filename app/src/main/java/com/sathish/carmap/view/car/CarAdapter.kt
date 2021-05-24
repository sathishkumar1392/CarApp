package com.sathish.carmap.view.car

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sathish.carmap.data.model.CarResponseApiItem
import com.sathish.carmap.databinding.AdapterCarDetailsBinding

class CarAdapter : RecyclerView.Adapter<CarAdapter.ViewHolder>() {


    private val callBack = object : DiffUtil.ItemCallback<CarResponseApiItem>(){
        override fun areItemsTheSame(oldItem: CarResponseApiItem, newItem: CarResponseApiItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CarResponseApiItem, newItem: CarResponseApiItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: AdapterCarDetailsBinding =
            AdapterCarDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }


    class ViewHolder(private val binding: AdapterCarDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carResponseApiItem: CarResponseApiItem) {
            binding.itemDetails = carResponseApiItem
        }
    }

    override fun getItemCount(): Int  = differ.currentList.size

}