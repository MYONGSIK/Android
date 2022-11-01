package com.myongsik.myongsikandroid.ui.adapter.state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.myongsik.myongsikandroid.databinding.ItemLoadStateBinding

//책 검색에서 현재 상황을 LoadStateAdapter 를 통해 파악함
//이를 BookSearchGridPagingAdapter 하고 연결
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