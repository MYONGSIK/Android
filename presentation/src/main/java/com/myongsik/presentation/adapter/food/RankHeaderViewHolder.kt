package com.myongsik.presentation.adapter.food

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.presentation.R
import com.myongsik.presentation.databinding.HeaderSearchRankingBinding


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

            itemSearchHashtagRice.setOnClickListener {
                clickCallback.onHashtagGoodFoodClick()
            }
            itemSearchHashtagCafe.setOnClickListener {
                clickCallback.onHashtagGoodCafeClick()
            }
            itemSearchHashtagPub.setOnClickListener {
                clickCallback.onHashtagGoodDrinkClick()
            }
            itemSearchHashtagBread.setOnClickListener {
                clickCallback.onHashtagGoodBreadClick()
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
