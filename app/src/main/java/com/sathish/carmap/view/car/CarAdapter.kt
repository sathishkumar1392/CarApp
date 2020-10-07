package com.sathish.carmap.view.car

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sathish.carmap.data.model.CarResponseApiItem
import com.sathish.carmap.databinding.AdapterCarDetailsBinding

class CarAdapter : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    private val item: MutableList<CarResponseApiItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: AdapterCarDetailsBinding =
            AdapterCarDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount() = item.size


    fun update(data: List<CarResponseApiItem>) {
        item.clear()
        item.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: AdapterCarDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(carResponseApiItem: CarResponseApiItem) {
            binding.itemDetails = carResponseApiItem
        }
    }

}