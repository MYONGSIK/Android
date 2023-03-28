package com.myongsik.myongsikandroid.ui.adapter.food

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.HeaderSearchRankingBinding


class RankHeaderViewHolder(
    binding : HeaderSearchRankingBinding,
    private val clickCallback: OnScrapViewHolderClick,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {

            ArrayAdapter.createFromResource(
                binding.root.context,
                R.array.sort_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.spSort.adapter = adapter
            }

            itemSearchHashtag1.setOnClickListener{
                clickCallback.onHashtagGoodFoodClick()
            }
            itemSearchHashtag2.setOnClickListener{
                clickCallback.onHashtagGoodCafeClick()
            }
            itemSearchHashtag3.setOnClickListener{
                clickCallback.onHashtagGoodDrinkClick()
            }
            itemSearchHashtag4.setOnClickListener{
                clickCallback.onHashtagGoodBreadClick()
            }
            spSort.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    clickCallback.onSelectSortMenu(parent?.getItemAtPosition(position) as? String ?:"")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }
        }
    }
}
