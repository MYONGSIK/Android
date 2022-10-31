package com.myongsik.myongsikandroid.ui.adapter.search

import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemLoveRestaurantBinding
import java.text.DecimalFormat

//관심목록에서 사용하는 RV Holder
class LoveFoodViewHolder(
    private val binding : ItemLoveRestaurantBinding
) : RecyclerView.ViewHolder(binding.root){

    val dec = DecimalFormat("#,###")

    fun bind(restaurant: Restaurant){

        itemView.apply{
            binding.itemFoodName.text = restaurant.place_name
            binding.itemFoodObject.text = restaurant.category_group_name
        }
    }
}