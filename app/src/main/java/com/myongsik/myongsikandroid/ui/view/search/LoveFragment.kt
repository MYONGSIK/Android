package com.myongsik.myongsikandroid.ui.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myongsik.myongsikandroid.data.model.food.GetRankRestaurant
import com.myongsik.myongsikandroid.data.model.food.OnSearchViewHolderClick
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.FragmentLoveBinding
import com.myongsik.myongsikandroid.ui.adapter.search.LoveFoodPagingAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//찜꽁 리스트 화면
class LoveFragment : Fragment(), OnSearchViewHolderClick {

    private var _binding : FragmentLoveBinding?= null
    private val binding : FragmentLoveBinding
        get() = _binding!!

    private lateinit var callback: OnBackPressedCallback

    private val mainViewModel by activityViewModels<MainViewModel>()

    private lateinit var loveFoodAdapter : LoveFoodPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.loveIsFood.collectLatest {
                    if(it.isEmpty()){
                        binding.favoriteEmptyLove.visibility = View.VISIBLE
                    } else {
                        binding.favoriteEmptyLove.visibility = View.INVISIBLE
                    }
                    mainViewModel.loveFoods.collectLatest { pagedData ->
                        loveFoodAdapter.submitData(pagedData)
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = LoveFragmentDirections.actionFragmentLoveToFragmentSearch()
                findNavController().navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun setUpRecyclerView(){
        loveFoodAdapter = LoveFoodPagingAdapter(this)
        binding.loveMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = loveFoodAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun addItem(restaurant: Restaurant) {
        mainViewModel.saveFoods(restaurant)
    }

    override fun deleteItem(restaurant: Restaurant) {
        mainViewModel.deleteFoods(restaurant)
    }

    override fun isItem(string: String) {
    }

    override fun clickDirectButton(restaurant: Restaurant) {
        val action  = LoveFragmentDirections.actionFragmentLoveToFragmentRestaurant(restaurant)
        findNavController().navigate(action)
    }
}