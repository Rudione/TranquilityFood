package com.example.tranquilityfood.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tranquilityfood.databinding.CategoryItemBinding
import com.example.tranquilityfood.pojo.Category

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoryMealViewHolder>() {
    private var list = ArrayList<Category>()
    var onItemClick: ((Category) -> Unit)? = null

    fun setCategoryList(categoriesList : List<Category>) {
        this.list = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryMealViewHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        return CategoryMealViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(list[position].strCategoryThumb)
            .into(holder.binding.categoryImg)

        holder.binding.categoryTvName.text = list[position].strCategory

       holder.itemView.setOnClickListener {
           onItemClick!!.invoke(list[position])
       }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}