package com.myongsik.myongsikandroid.ui.adapter.food

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.food.GetRankRestaurant
import com.myongsik.myongsikandroid.data.model.food.OnSearchViewHolderClick
import com.myongsik.myongsikandroid.databinding.ItemRestaurantRankingBinding

class RankRestaurantViewHolder(
    private val binding: ItemRestaurantRankingBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(getRankRestaurant: GetRankRestaurant) {
        val context = binding.root.context
        val distance = if (getRankRestaurant.distance.length >= 4) {
            "${getRankRestaurant.distance[0]}.${getRankRestaurant.distance[1]}km"
        } else {
            "${getRankRestaurant.distance}m"
        }

        val placeName =
            if (getRankRestaurant.name.length >= 13)
                "${getRankRestaurant.name.substring(0, 12)}..."
            else
                getRankRestaurant.name

        with(binding) {
            itemFoodPhoneTv.setOnClickListener {
                val text = itemFoodPhoneTv.text
                if (context.getString(R.string.is_null_phone_number) != text) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$text"))
                    context.startActivity(intent)
                }
            }
            itemFoodLocationTv.text = getRankRestaurant.address
            val text = rankingTitleTv.text.toString().format(getRankRestaurant.scrapCount)
            rankingTitleTv.text = text

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
            itemFoodObject.text = getRankRestaurant.category
            itemFoodPhoneTv.text = getRankRestaurant.contact
            weekFoodAfternoonTv.text = distance
        }
    }
}
