package com.myongsik.myongsikandroid.ui.adapter.food

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.HeaderSearchRankingBinding


class RankHeaderViewHolder(
    binding: HeaderSearchRankingBinding,
    private val clickCallback: OnScrapViewHolderClick,
    sortType: Int,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            ArrayAdapter.createFromResource(
                binding.root.context,
                R.array.sort_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spSort.adapter = adapter
                spSort.setSelection(sortType, false)
            }

            itemSearchHashtag1.setOnClickListener {
                clickCallback.onHashtagGoodFoodClick()
            }
            itemSearchHashtag2.setOnClickListener {
                clickCallback.onHashtagGoodCafeClick()
            }
            itemSearchHashtag3.setOnClickListener {
                clickCallback.onHashtagGoodDrinkClick()
            }
            itemSearchHashtag4.setOnClickListener {
                clickCallback.onHashtagGoodBreadClick()
            }
            goodMap.setOnClickListener{
                clickCallback.onClickMap()
            }
            btArrow.setOnClickListener {
                spSort.performClick()
            }

            spSort.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    clickCallback.onSelectSortMenu(parent?.getItemAtPosition(position) as? String ?: "")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("HELLO", "")
                }
            }
        }
    }
}
