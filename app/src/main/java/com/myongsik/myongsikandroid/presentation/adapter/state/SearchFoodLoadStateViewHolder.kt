package com.myongsik.myongsikandroid.presentation.adapter.state

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.myongsik.myongsikandroid.databinding.ItemLoadStateBinding

class SearchFoodLoadStateViewHolder(
    private val binding : ItemLoadStateBinding,
) : RecyclerView.ViewHolder(binding.root){

    fun bind(loadState : LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }
}