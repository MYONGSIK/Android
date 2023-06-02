package com.myongsik.presentation.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myongsik.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.base.BaseFragment
import com.myongsik.myongsikandroid.databinding.FragmentLoveBinding
import com.myongsik.presentation.adapter.search.LoveFoodPagingAdapter
import com.myongsik.presentation.adapter.search.OnSearchViewHolderClick
import com.myongsik.presentation.viewmodel.search.LoveViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//찜꽁 리스트 화면
@AndroidEntryPoint
class LoveFragment : BaseFragment<FragmentLoveBinding>(), OnSearchViewHolderClick {

    private val loveViewModel by viewModels<LoveViewModel>()

    private lateinit var loveFoodAdapter : LoveFoodPagingAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoveBinding {
        return FragmentLoveBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        setUpRecyclerView()
    }

    override fun initListener() {
        loveViewModel.getLoveListRestaurant()
        loveViewModel.getLoveRestaurant()
        getFavoriteRestaurant()

        settingBackPressedCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = LoveFragmentDirections.actionFragmentLoveToFragmentSearch()
                findNavController().navigate(action)
            }
        })
    }

    private fun getFavoriteRestaurant(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                loveViewModel.loveListRestaurants.collectLatest {
                    if(it.isEmpty()){
                        binding.favoriteEmptyLove.visibility = View.VISIBLE
                    } else {
                        binding.favoriteEmptyLove.visibility = View.INVISIBLE
                    }
                    loveViewModel.loveRestaurants.collectLatest { pagedData ->
                        loveFoodAdapter.submitData(pagedData)
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        loveFoodAdapter = LoveFoodPagingAdapter(this)
        binding.loveMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = loveFoodAdapter
        }
    }

    override fun addItem(restaurant: Restaurant) {

    }

    override fun deleteItem(restaurant: Restaurant) {
        loveViewModel.deleteFoods(restaurant)
    }

    override fun isItem(string: String) {
    }

    override fun clickDirectButton(restaurant: Restaurant) {
        val action  = LoveFragmentDirections.actionFragmentLoveToFragmentRestaurant(restaurant)
        findNavController().navigate(action)
    }
}