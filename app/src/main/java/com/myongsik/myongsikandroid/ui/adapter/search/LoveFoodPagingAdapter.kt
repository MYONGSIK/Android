package com.myongsik.myongsikandroid.ui.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemLoveRestaurantBinding

//관심목록 페이징 어댑터
class LoveFoodPagingAdapter : PagingDataAdapter<Restaurant, LoveFoodViewHolder>(BookDiffCallback) {

    override fun onBindViewHolder(holder: LoveFoodViewHolder, position: Int) {
        val pagedBook = getItem(position)
        pagedBook?.let { restaurant ->
            holder.bind(restaurant)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let{ it(restaurant) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoveFoodViewHolder {
        return LoveFoodViewHolder(
            ItemLoveRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
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