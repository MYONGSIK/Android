package com.myongsik.myongsikandroid.ui.adapter.food

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.food.GetRankRestaurant
import com.myongsik.myongsikandroid.databinding.ItemRestaurantRankingBinding

class RankRestaurantViewHolder(
    private val binding: ItemRestaurantRankingBinding,
    private val clickCallback: OnScrapViewHolderClick,
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
            else {
                getRankRestaurant.name
            }



        with(binding) {
            itemFoodName.text = placeName
            itemFoodObject.text = getRankRestaurant.category
            itemFoodLocationTv.text = getRankRestaurant.address
            weekFoodAfternoonTv.text = distance

            if (getRankRestaurant.contact.isEmpty()) {
                itemFoodPhoneTv.text = context.getString(R.string.is_null_phone_number)
            } else {
                itemFoodPhoneTv.text = getRankRestaurant.contact
            }

            if (getRankRestaurant.scrapCount == null) {
                isVisibleRankingTitle(false)
            } else {
                isVisibleRankingTitle(true)
                rankingTitleTv1.text = getRankRestaurant.scrapCount.toString()
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
                clickCallback.clickRankDirectButton(getRankRestaurant)
            }
        }
    }

    private fun isVisibleRankingTitle(isVisible:Boolean) {
        binding.apply {
            rankingTitleTv.isVisible = isVisible
            rankingTitleTv1.isVisible = isVisible
            rankingTitleTv2.isVisible = isVisible

        }
    }
}
