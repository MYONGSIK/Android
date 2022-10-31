package com.myongsik.myongsikandroid.ui.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.databinding.FragmentLoveBinding
import com.myongsik.myongsikandroid.ui.adapter.search.LoveFoodPagingAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModelProviderFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoveFragment : Fragment() {

    private var _binding : FragmentLoveBinding?= null
    private val binding : FragmentLoveBinding
        get() = _binding!!

    //viewModel 생성
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

        binding.loveBackBt.setOnClickListener {
            findNavController().popBackStack()
        }

        setUpRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.loveFoods.collectLatest { pagedData ->
                    loveFoodAdapter.submitData(pagedData)
                }
            }
        }

    }

    //리사이클러뷰 어댑터를 페이징어댑터로 변경
    private fun setUpRecyclerView(){
//        bookSearchAdapter = BookSearchAdapter()
        loveFoodAdapter = LoveFoodPagingAdapter()
        binding.loveMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = loveFoodAdapter
        }
        loveFoodAdapter.setOnItemClickListener {
            val action  = LoveFragmentDirections.actionFragmentLoveToFragmentRestaurant(it)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}