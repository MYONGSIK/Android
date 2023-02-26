package com.myongsik.myongsikandroid.ui.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.food.OnLoveClick
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemLoveRestaurantBinding

//관심목록 페이징 어댑터
class LoveFoodPagingAdapter(listener : OnLoveClick)  : PagingDataAdapter<Restaurant, LoveFoodViewHolder>(BookDiffCallback) {
    private val mCallback = listener

    override fun onBindViewHolder(holder: LoveFoodViewHolder, position: Int) {
        val pagedBook = getItem(position)
        pagedBook?.let { restaurant ->
            holder.bind(restaurant)
            holder.itemView.setOnClickListener {
                if(holder.itemView.findViewById<ImageView>(R.id.item_love_location_iv).visibility == View.VISIBLE){
                    holder.itemView.findViewById<ImageView>(R.id.item_love_location_iv).visibility = View.GONE
                    holder.itemView.findViewById<TextView>(R.id.item_love_location_tv).visibility = View.GONE
                    holder.itemView.findViewById<ImageView>(R.id.item_love_phone_iv).visibility = View.GONE
                    holder.itemView.findViewById<TextView>(R.id.item_love_phone_tv).visibility = View.GONE
                    holder.itemView.findViewById<ConstraintLayout>(R.id.item_love_detail_iv).visibility = View.GONE
                }else{
                    holder.itemView.findViewById<ImageView>(R.id.item_love_location_iv).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.item_love_location_tv).visibility = View.VISIBLE
                    holder.itemView.findViewById<ImageView>(R.id.item_love_phone_iv).visibility = View.VISIBLE
                    holder.itemView.findViewById<TextView>(R.id.item_love_phone_tv).visibility = View.VISIBLE
                    holder.itemView.findViewById<ConstraintLayout>(R.id.item_love_detail_iv).visibility = View.VISIBLE
                }
            }
            holder.itemView.findViewById<ConstraintLayout>(R.id.item_love_detail_iv).setOnClickListener {
                onItemClickListener?.let{ it(restaurant) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoveFoodViewHolder {
        return LoveFoodViewHolder(
            ItemLoveRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false), mCallback
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