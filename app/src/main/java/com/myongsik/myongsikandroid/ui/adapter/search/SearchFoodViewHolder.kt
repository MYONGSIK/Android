package com.myongsik.myongsikandroid.ui.adapter.search

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.food.OnLoveClick
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemRestaurantFoodBinding

//카카오 api 불러온 리사이클러뷰 아이템 뷰홀더
class SearchFoodViewHolder(
    private val binding: ItemRestaurantFoodBinding,
    private val mCallback: OnLoveClick,
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(restaurant: Restaurant) {

        val distance = if (restaurant.distance.length >= 4) {
            "${restaurant.distance[0]}.${restaurant.distance[1]}km"
        } else {
            "${restaurant.distance}m"
        }

        val placeName =
            if (restaurant.place_name.length >= 13)
                "${restaurant.place_name.substring(0, 12)}..."
            else
                restaurant.place_name



        itemView.apply {
            with(binding) {
                // 찜콩 리스트
                itemFoodLoveIv.visibility = View.INVISIBLE
                itemFoodLoveFillIv.visibility = View.INVISIBLE

                itemFoodLoveIv.setOnClickListener {
                    mCallback.addItem(restaurant)
                    itemFoodLoveIv.visibility = View.INVISIBLE
                    itemFoodLoveFillIv.visibility = View.VISIBLE
                    Snackbar.make(it, "찜 완료!", Snackbar.LENGTH_SHORT).show()
                }
                itemFoodLoveFillIv.setOnClickListener {
                    mCallback.deleteItem(restaurant)
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



                itemFoodName.text = placeName
                itemFoodObject.text = restaurant.category_group_name
                weekFoodAfternoonTv.text = distance
                itemFoodLocationTv.text = restaurant.road_address_name

                itemFoodPhoneTv.apply {
                    text = if (restaurant.phone == "") {
                        this.context.getString(R.string.is_null_phone_number)
                    } else {
                        restaurant.phone
                    }
                }

            }

        }
    }


}
/*
"address_name":"서울 서대문구 홍제동 334-107",
"category_group_code":"CE7",
"category_group_name":"카페",
"category_name":"음식점 \u003e 카페 \u003e 커피전문점",
"distance":"1380","id":"956694628",
"phone":"010-4021-5293",
"place_name":"런어웨이",
"place_url":"http://place.map.kakao.com/956694628",
"road_address_name":"서울 서대문구 홍제내4길 4-11",
 */