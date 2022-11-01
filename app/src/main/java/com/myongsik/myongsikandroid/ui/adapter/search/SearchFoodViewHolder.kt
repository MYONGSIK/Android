package com.myongsik.myongsikandroid.ui.adapter.search

import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.ItemRestaurantFoodBinding

//카카오 api 불러온 리사이클러뷰 아이템 뷰홀더
class SearchFoodViewHolder(
    private val binding : ItemRestaurantFoodBinding,
) : RecyclerView.ViewHolder(binding.root){

    fun bind(restaurant: Restaurant){
//        val dayDate = foodResult.toDay.substring(0, 4)
//        val dayMonth = foodResult.toDay.substring(5, 7)
//        val dayDay = foodResult.toDay.substring(8, 10)
//        val day = foodResult.dayOfTheWeek
        //1120
//        var distance = "${restaurant.distance}m"
        val distance = if(restaurant.distance.length >= 4){
           "${restaurant.distance[0]}.${restaurant.distance[1]}km"
        }else{
            "${restaurant.distance}m"
        }
        val phone = if(restaurant.phone == "") "전화번호가 없습니다." else restaurant.phone


        itemView.apply{
            binding.itemFoodName.text = restaurant.place_name
            binding.itemFoodObject.text = restaurant.category_group_name
            binding.weekFoodAfternoonTv.text = distance
            binding.itemFoodLocationTv.text = restaurant.road_address_name
            binding.itemFoodPhoneTv.text = phone
        }

//        binding.itemFoodPhoneTv.setOnClickListener {
//            // 어디에 전화를 걸건지 text 정보 받기
//            val input = binding.itemFoodPhoneTv.text.toString()
//            // Uri를 이용해서 정보 저장
//            val myUri = Uri.parse("tel:${input}")
//            // 전환할 정보 설정 - ACTION_DIAL
//            val myIntent = Intent(Intent.ACTION_DIAL, myUri)
//            // 이동
//            startActivity(myIntent)
//        }

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