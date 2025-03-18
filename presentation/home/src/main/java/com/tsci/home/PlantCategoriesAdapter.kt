package com.tsci.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsci.home.databinding.ItemPlantCategoryBinding
import com.tsci.home.model.CategoryUiModel

class PlantCategoriesAdapter(
    private val categories: List<CategoryUiModel>
) : RecyclerView.Adapter<PlantCategoriesAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(
        private val binding: ItemPlantCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryUiModel) {
            binding.textViewPlantCategoryName.text = item.name

            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .into(binding.imageViewPlantCategoryImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemPlantCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categories[position]
        holder.bind(item)
    }
}