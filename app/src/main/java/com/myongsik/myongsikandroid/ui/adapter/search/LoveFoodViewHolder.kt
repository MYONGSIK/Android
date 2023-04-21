package com.myongsik.myongsikandroid.ui.adapter.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemLoveRestaurantBinding
import com.myongsik.myongsikandroid.util.CommonUtil

//관심목록에서 사용하는 RV Holder
class LoveFoodViewHolder(
    private val binding: ItemLoveRestaurantBinding,
    private val mCallback: OnSearchViewHolderClick
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(restaurant: Restaurant) {
        val parentView = binding.root
        val distance = CommonUtil.distanceMapper(restaurant.distance)
        val phone = if (restaurant.phone == "") "전화번호가 없습니다." else restaurant.phone

        val placeName =
            if (restaurant.place_name.length >= 13)
                "${restaurant.place_name.substring(0, 12)}..."
            else
                restaurant.place_name

        binding.itemLoveLoveIv.setOnClickListener() {
            mCallback.addItem(restaurant)
            binding.itemLoveLoveIv.visibility = View.INVISIBLE
            binding.itemLoveLoveFillIv.visibility = View.VISIBLE
            Snackbar.make(parentView, "찜 완료!", Snackbar.LENGTH_SHORT).show()
        }

        binding.itemLoveLoveFillIv.setOnClickListener() {
            mCallback.deleteItem(restaurant)
            binding.itemLoveLoveIv.visibility = View.VISIBLE
            binding.itemLoveLoveFillIv.visibility = View.INVISIBLE
            Snackbar.make(parentView, "찜 목록에서 삭제되었습니다.", Snackbar.LENGTH_SHORT).show()

        }

        binding.itemLoveDetailCl.setOnClickListener {
            mCallback.clickDirectButton(restaurant)
        }

        binding.itemLoveName.text = placeName
        binding.itemLoveObject.text = restaurant.category_group_name
        binding.itemLoveDistanceTv.text = distance
        binding.itemLoveLocationTv.text = restaurant.road_address_name
        binding.itemLovePhoneTv.text = phone

    }
}