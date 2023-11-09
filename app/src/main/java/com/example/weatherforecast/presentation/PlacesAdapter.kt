package com.example.weatherforecast.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.databinding.PlaceItemBinding
import com.example.weatherforecast.entity.PlaceInRepo
import javax.inject.Inject

class PlacesAdapter @Inject constructor(
    private val onClick: (PlaceInRepo) -> Unit
): ListAdapter<PlaceInRepo, PlaceViewHolder>(DiffutilCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = PlaceItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val placeItem = getItem(position)
        with(holder.binding) {
            name.text = placeItem.name
        }
        holder.binding.placeItemLayout.setOnClickListener {
            placeItem?.let {
                onClick(placeItem)
            }
        }
    }
}

class DiffutilCallback: DiffUtil.ItemCallback<PlaceInRepo>() {
    override fun areItemsTheSame(oldItem: PlaceInRepo, newItem: PlaceInRepo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PlaceInRepo, newItem: PlaceInRepo): Boolean {
        return oldItem.id == newItem.id
    }
}

class PlaceViewHolder @Inject constructor
    (val binding: PlaceItemBinding) :
    RecyclerView.ViewHolder(binding.root)
