package com.myongsik.myongsikandroid.ui.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.food.OnLoveClick
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemRestaurantFoodBinding

//카카오 api 불러온 리사이클러뷰 아이템 어댑터
//검색화면 추천에서 쓰일 것 (10개 까지만 보여주기 때문에)
class SearchFoodAdapter(listener : OnLoveClick) : ListAdapter<Restaurant, SearchFoodViewHolder>(BookDiffCallback) {
    private val mCallback = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFoodViewHolder {
        return SearchFoodViewHolder(
            ItemRestaurantFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false), mCallback
        )
    }

    override fun onBindViewHolder(holder: SearchFoodViewHolder, position: Int) {
        val foodResult = currentList[position]
        holder.bind(foodResult)
        val cl = holder.itemView.findViewById<ConstraintLayout>(R.id.item_food_detail_cl)
        cl.setOnClickListener {
            onItemClickListener?.let{ it(foodResult) }
        }

    }

    private var onItemClickListener : ((Restaurant) -> Unit)? = null
    fun setOnItemClickListener(listener : (Restaurant) -> Unit){
        onItemClickListener = listener
    }

    companion object{
        private val BookDiffCallback = object : DiffUtil.ItemCallback<Restaurant>(){
            override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }
        }
    }


}