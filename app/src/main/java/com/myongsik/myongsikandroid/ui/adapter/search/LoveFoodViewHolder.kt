package com.myongsik.myongsikandroid.ui.adapter.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.data.model.food.OnLoveClick
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemLoveRestaurantBinding
import java.text.DecimalFormat

//관심목록에서 사용하는 RV Holder
class LoveFoodViewHolder(
    private val binding : ItemLoveRestaurantBinding,
    private val mCallback: OnLoveClick
    ) : RecyclerView.ViewHolder(binding.root){

    fun bind(restaurant: Restaurant) {

        val distance = if (restaurant.distance.length >= 4) {
            "${restaurant.distance[0]}.${restaurant.distance[1]}km"
        } else {
            "${restaurant.distance}m"
        }
        val phone = if (restaurant.phone == "") "전화번호가 없습니다." else restaurant.phone

        val placeName =
            if (restaurant.place_name.length >= 13)
                "${restaurant.place_name.substring(0, 12)}..."
            else
                restaurant.place_name



        itemView.apply {
            binding.itemLoveLoveIv.setOnClickListener() {
//                if(mCallback.isItem(restaurant)){
//                    Snackbar.make(this, "이미 찜하셨습니다.", Snackbar.LENGTH_SHORT).show()
//                }
                mCallback.addItem(restaurant)
                binding.itemLoveLoveIv.visibility = View.INVISIBLE
                binding.itemLoveLoveFillIv.visibility = View.VISIBLE
                Snackbar.make(this, "찜 완료!", Snackbar.LENGTH_SHORT).show()
            }
            binding.itemLoveLoveFillIv.setOnClickListener() {
                mCallback.deleteItem(restaurant)
                binding.itemLoveLoveIv.visibility = View.VISIBLE
                binding.itemLoveLoveFillIv.visibility = View.INVISIBLE
                Snackbar.make(this, "찜 목록에서 삭제되었습니다.", Snackbar.LENGTH_SHORT).show()

            }
            binding.itemLoveName.text = placeName
            binding.itemLoveObject.text = restaurant.category_group_name
            binding.itemLoveDistanceTv.text = distance
            binding.itemLoveLocationTv.text = restaurant.road_address_name
            binding.itemLovePhoneTv.text = phone
        }
    }
}