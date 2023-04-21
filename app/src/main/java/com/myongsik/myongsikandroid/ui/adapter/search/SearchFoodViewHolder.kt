package com.myongsik.myongsikandroid.ui.adapter.search

import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemRestaurantFoodBinding
import com.myongsik.myongsikandroid.util.CommonUtil

//카카오 api 불러온 리사이클러뷰 아이템 뷰홀더
class SearchFoodViewHolder(
    private val binding: ItemRestaurantFoodBinding,
    private val clickCallback: OnSearchViewHolderClick,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(restaurant: Restaurant) {
        val context = binding.root.context
        val distance = CommonUtil.distanceMapper(restaurant.distance)

        val placeName =
            if (restaurant.place_name.length >= 13)
                "${restaurant.place_name.substring(0, 12)}..."
            else
                restaurant.place_name


        with(binding) {
            // 찜콩 리스트
            itemFoodLoveIv.visibility = View.INVISIBLE
            itemFoodLoveFillIv.visibility = View.INVISIBLE

            itemFoodLoveIv.setOnClickListener {
                clickCallback.addItem(restaurant)
                itemFoodLoveIv.visibility = View.INVISIBLE
                itemFoodLoveFillIv.visibility = View.VISIBLE
                Snackbar.make(it, "찜 완료!", Snackbar.LENGTH_SHORT).show()
            }

            itemFoodLoveFillIv.setOnClickListener {
                clickCallback.deleteItem(restaurant)
                itemFoodLoveIv.visibility = View.VISIBLE
                itemFoodLoveFillIv.visibility = View.INVISIBLE
                Snackbar.make(it, "찜 목록에서 삭제되었습니다.", Snackbar.LENGTH_SHORT).show()
            }

            itemFoodPhoneTv.setOnClickListener {
                val text = itemFoodPhoneTv.text
                if (context.getString(R.string.is_null_phone_number) != text) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$text"))
                    context.startActivity(intent)
                }
            }

            itemFoodLocationTv.setOnClickListener {
                val naverMapUrl = "nmap://search?query=${itemFoodLocationTv.text}"
                val uri = Uri.parse(naverMapUrl)
                val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                mapIntent.setPackage("com.nhn.android.nmap")
                if (mapIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(mapIntent)
                } else {
                    context.run {
                        Toast.makeText(this, getString(R.string.please_install_naver_map), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            itemFoodDetailCl.setOnClickListener {
                clickCallback.clickDirectButton(restaurant)
            }

            val addressSpannableString = SpannableString(restaurant.road_address_name)
            addressSpannableString.setSpan(UnderlineSpan(), 0, addressSpannableString.length, 0)
            itemFoodLocationTv.text = addressSpannableString

            if (restaurant.phone != context.getString(R.string.is_null_phone_number) && restaurant.phone.isNotEmpty()) {
                val phoneSpannableString = SpannableString(restaurant.phone)
                phoneSpannableString.setSpan(UnderlineSpan(), 0, phoneSpannableString.length, 0)
                itemFoodPhoneTv.text = phoneSpannableString
            } else {
                itemFoodPhoneTv.text = context.getString(R.string.is_null_phone_number)
            }

            itemFoodName.text = placeName
            itemFoodObject.text = restaurant.category_group_name
            weekFoodAfternoonTv.text = distance
        }
    }
}
