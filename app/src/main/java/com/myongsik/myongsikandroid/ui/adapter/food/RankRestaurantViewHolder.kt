package com.myongsik.myongsikandroid.ui.adapter.food

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.Toast
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
            else
                getRankRestaurant.name

        with(binding) {

            val addressSpannableString = SpannableString(getRankRestaurant.address)
            addressSpannableString.setSpan(UnderlineSpan(), 0, addressSpannableString.length, 0)
            itemFoodLocationTv.text = addressSpannableString

            val phoneSpannableString = SpannableString(getRankRestaurant.contact)
            phoneSpannableString.setSpan(UnderlineSpan(), 0, phoneSpannableString.length, 0)
            itemFoodPhoneTv.text = phoneSpannableString


            rankingTitleTv1.text = getRankRestaurant.scrapCount.toString()
            itemFoodName.text = placeName
            itemFoodObject.text = getRankRestaurant.category
            weekFoodAfternoonTv.text = distance


            itemFoodPhoneTv.setOnClickListener {
                val text = itemFoodPhoneTv.text
                if (context.getString(R.string.is_null_phone_number) != text) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$text"))
                    context.startActivity(intent)
                }
            }


            itemFoodLocationTv.setOnClickListener {
                moveNaverMap(this)
            }

            itemFoodDetailCl.setOnClickListener {
                clickCallback.clickRankDirectButton(getRankRestaurant)
            }
        }
    }

    private fun moveNaverMap(binding: ItemRestaurantRankingBinding) {
        binding.root.context.run {
            val naverMapUrl = "nmap://search?query=${binding.itemFoodLocationTv.text}"
            val uri = Uri.parse(naverMapUrl)
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.nhn.android.nmap")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                Toast.makeText(this, getString(R.string.please_install_naver_map), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
