package com.myongsik.presentation.adapter.food

import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.data.model.food.GetRankRestaurant
import com.myongsik.presentation.R
import com.myongsik.presentation.databinding.ItemRestaurantRankingBinding
import com.myongsik.presentation.util.CommonUtil

class RankRestaurantViewHolder(
    private val binding: ItemRestaurantRankingBinding,
    private val clickCallback: OnScrapViewHolderClick,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(getRankRestaurant: GetRankRestaurant) {
        val context = binding.root.context
        val distance = CommonUtil.distanceMapper(getRankRestaurant.distance)

        val placeName =
            if (getRankRestaurant.name.length >= 13)
                "${getRankRestaurant.name.substring(0, 12)}..."
            else {
                getRankRestaurant.name
            }

        with(binding) {
            val addressSpannableString = SpannableString(getRankRestaurant.address)
            addressSpannableString.setSpan(UnderlineSpan(), 0, addressSpannableString.length, 0)
            itemFoodLocationTv.text = addressSpannableString

            if (getRankRestaurant.contact != context.getString(R.string.is_null_phone_number) && getRankRestaurant.contact.isNotEmpty()) {
                val phoneSpannableString = SpannableString(getRankRestaurant.contact)
                phoneSpannableString.setSpan(UnderlineSpan(), 0, phoneSpannableString.length, 0)
                itemFoodPhoneTv.text = phoneSpannableString
            } else {
                itemFoodPhoneTv.text = context.getString(R.string.is_null_phone_number)
            }

            if (getRankRestaurant.scrapCount == null) {
                isVisibleRankingTitle(false)
            } else {
                isVisibleRankingTitle(true)
                rankingTitleTv1.text = getRankRestaurant.scrapCount.toString()
            }

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

    private fun isVisibleRankingTitle(isVisible: Boolean) {
        binding.apply {
            rankingTitleTv.isVisible = isVisible
            rankingTitleTv1.isVisible = isVisible
            rankingTitleTv2.isVisible = isVisible

        }
    }
}
