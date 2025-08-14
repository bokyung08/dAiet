//
//package com.example.daiet
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.daiet.databinding.ItemMealBinding
//
//class MealAdapter(private val meals: List<String>) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
//
//    inner class MealViewHolder(val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
//        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MealViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
//        holder.binding.mealName.text = meals[position]
//    }
//
//    override fun getItemCount() = meals.size
//}
