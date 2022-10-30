package com.myongsik.myongsikandroid.ui.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.myongsik.myongsikandroid.data.repository.SearchFoodRepositoryImpl
import com.myongsik.myongsikandroid.databinding.FragmentSearchBinding
import com.myongsik.myongsikandroid.ui.adapter.SearchFoodAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModelProviderFactory
import com.myongsik.myongsikandroid.util.Constant.SEARCH_FOODS_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint

class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding?= null
    private val binding : FragmentSearchBinding
        get() = _binding!!

//    private val mainViewModel by activityViewModels<MainViewModel>()
//    private val searchViewModel by viewModels<SearchViewModel>()
    private val searchViewModel : SearchViewModel by viewModels{
        SearchViewModelProviderFactory()
    }


    private lateinit var searchFoodAdapter: SearchFoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchBooks()
        setUpRecyclerView()

        binding.searchMyongjiRv.visibility = View.VISIBLE

        binding.goodCafeDrinkTv.visibility = View.INVISIBLE
        binding.horizonSv.visibility = View.INVISIBLE
        binding.goodPlaceMyongji.visibility = View.INVISIBLE
        binding.searchMyongjiRecommend.visibility = View.INVISIBLE

        super.onViewCreated(view, savedInstanceState)
    }

    private fun searchBooks(){
        var startTime = System.currentTimeMillis()
        var endTime : Long



//            binding.searchMyongjiRv.visibility = View.INVISIBLE
//
//            binding.goodCafeDrinkTv.visibility = View.VISIBLE
//            binding.horizonSv.visibility = View.VISIBLE
//            binding.goodPlaceMyongji.visibility = View.VISIBLE
//            binding.searchMyongjiRecommend.visibility = View.VISIBLE


        binding.tlSearch.addTextChangedListener{ text: Editable? ->
            endTime = System.currentTimeMillis()
            if(endTime - startTime >= SEARCH_FOODS_TIME_DELAY){
                text?.let {
                    val query = it.toString().trim()
                    if(query.isNotEmpty()){
                        searchViewModel.searchFood(query)
                    }
                }
            }
            startTime = endTime
        }
    }

    private fun setUpRecyclerView(){
        searchFoodAdapter = SearchFoodAdapter()
        binding.searchMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = searchFoodAdapter
        }
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}