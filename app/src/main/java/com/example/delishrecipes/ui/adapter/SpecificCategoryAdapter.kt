package com.example.delishrecipes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delishrecipes.databinding.ItemSpecificCategoryListBinding
import com.example.delishrecipes.models.Meal

class SpecificCategoryAdapter(private val onMealClicked: (Meal) -> Unit) :
    ListAdapter<Meal, SpecificCategoryAdapter.SpecificCategoryViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecificCategoryViewHolder {
        val binding = ItemSpecificCategoryListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpecificCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecificCategoryViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal)
    }

    inner class SpecificCategoryViewHolder(private val binding: ItemSpecificCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            Glide.with(binding.ivRecipe.context).load(meal.strMealThumb)
                .into(binding.ivRecipe)
            binding.tvRecipeName.text = meal.strMeal
            binding.root.setOnClickListener {
                onMealClicked(meal)
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
}