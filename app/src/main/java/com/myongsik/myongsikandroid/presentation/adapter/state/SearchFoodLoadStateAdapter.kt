package com.myongsik.myongsikandroid.presentation.adapter.state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.myongsik.myongsikandroid.databinding.ItemLoadStateBinding

//Paging 에서 사용되는 LoadStateAdapter
class SearchFoodLoadStateAdapter() : LoadStateAdapter<SearchFoodLoadStateViewHolder>(){

    override fun onBindViewHolder(holder: SearchFoodLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): SearchFoodLoadStateViewHolder {
        return SearchFoodLoadStateViewHolder(
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}