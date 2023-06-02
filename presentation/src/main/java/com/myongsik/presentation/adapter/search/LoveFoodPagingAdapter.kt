package com.myongsik.presentation.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.myongsik.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.ItemLoveRestaurantBinding

//관심목록 페이징 어댑터
class LoveFoodPagingAdapter(listener : OnSearchViewHolderClick)  : PagingDataAdapter<Restaurant, LoveFoodViewHolder>(
    BookDiffCallback
) {
    private val mCallback = listener

    override fun onBindViewHolder(holder: LoveFoodViewHolder, position: Int) {
        val pagedBook = getItem(position)
        pagedBook?.let { restaurant ->
            holder.bind(restaurant)
            holder.itemView.setOnClickListener {
                if(holder.itemView.findViewById<ImageView>(R.id.item_love_location_iv).visibility == View.VISIBLE){
                    holder.itemView.findViewById<ImageView>(R.id.item_love_location_iv).visibility = View.GONE
                    holder.itemView.findViewById<TextView>(R.id.item_love_distance_tv).visibility = View.GONE
                    holder.itemView.findViewById<TextView>(R.id.item_love_location_tv).visibility = View.GONE
                    holder.itemView.findViewById<ImageView>(R.id.item_love_phone_iv).visibility = View.GONE
                    holder.itemView.findViewById<TextView>(R.id.item_love_phone_tv).visibility = View.GONE
                    holder.itemView.findViewById<ConstraintLayout>(R.id.item_love_detail_cl).visibility = View.GONE
                }else{
                    holder.itemView.findViewById<ImageView>(R.id.item_love_location_iv).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.item_love_distance_tv).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.item_love_location_tv).visibility = View.VISIBLE
                    holder.itemView.findViewById<ImageView>(R.id.item_love_phone_iv).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.item_love_phone_tv).visibility = View.VISIBLE
                    holder.itemView.findViewById<ConstraintLayout>(R.id.item_love_detail_cl).visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoveFoodViewHolder {
        return LoveFoodViewHolder(
            ItemLoveRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false), mCallback
        )
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