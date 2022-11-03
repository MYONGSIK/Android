package com.myongsik.myongsikandroid.ui.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemRestaurantFoodBinding

//검색 화면 페이징 어댑터
class SearchFoodPagingAdapter : PagingDataAdapter<Restaurant, SearchFoodViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFoodViewHolder {
        return SearchFoodViewHolder(
            ItemRestaurantFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchFoodViewHolder, position: Int) {
        val pagedFood = getItem(position)
        pagedFood?.let { restaurant ->
            holder.bind(restaurant)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let{ it(restaurant) }
            }
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