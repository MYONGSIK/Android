package com.myongsik.myongsikandroid.ui.view.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.myongsik.myongsikandroid.data.model.food.OnSearchViewHolderClick
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.FragmentSearchBinding
import com.myongsik.myongsikandroid.ui.adapter.search.SearchFoodAdapter
import com.myongsik.myongsikandroid.ui.adapter.search.SearchFoodPagingAdapter
import com.myongsik.myongsikandroid.ui.adapter.state.SearchFoodLoadStateAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModel
import com.myongsik.myongsikandroid.util.Constant.SEARCH_FOODS_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment(), OnSearchViewHolderClick {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val intRandom = Random().nextInt(19)

    private lateinit var callback: OnBackPressedCallback

    private val foodList = arrayOf(
        "부대찌개", "국밥", "마라탕", "중식", "한식", "카페", "족발", "술집", // 8
        "파스타", "커피", "삼겹살", "치킨", "떡볶이", "햄버거", "피자", "초밥", // 8
        "회", "곱창", "냉면", "닭발" //4  -> 총 20개
    )

    //검색 뷰모델, 현재 의존성 주입 안함
    private val searchViewModel by viewModels<SearchViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    //검색 어댑터 -> PagingAdapter
    private lateinit var searchFoodAdapter: SearchFoodPagingAdapter

    //추천 어댑터
    private lateinit var searchRecommendAdapter: SearchFoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpRecommendRecyclerView()
        searchBooks()

        setupLoadState()

        searchViewModel.searchRecommendFood(foodList[intRandom])
        binding.searchIcIv.setOnClickListener {
            binding.searchBackBt.visibility = View.VISIBLE
            binding.tlSearch.visibility = View.VISIBLE

            binding.searchTopTv.visibility = View.INVISIBLE
            binding.searchIcIv.visibility = View.INVISIBLE
        }

        binding.searchBackBt.setOnClickListener {
            binding.searchBackBt.visibility = View.INVISIBLE
            binding.tlSearch.visibility = View.INVISIBLE

            binding.tvEmptylist.visibility = View.INVISIBLE

            binding.searchTopTv.visibility = View.VISIBLE
            binding.searchIcIv.visibility = View.VISIBLE

            binding.tlSearch.text = null
        }

        searchViewModel.resultRecommendSearch.observe(viewLifecycleOwner){ response ->
            val foods = response.documents
            searchRecommendAdapter.submitList(foods)
        }

        binding.tlSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.searchMyongjiRv.visibility = View.INVISIBLE

                binding.goodCafeDrinkTv.visibility = View.VISIBLE
                binding.horizonSv.visibility = View.VISIBLE
                binding.goodPlaceMyongji.visibility = View.VISIBLE
                binding.searchMyongjiRecommend.visibility = View.VISIBLE

                binding.tvEmptylist.visibility = View.INVISIBLE
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.tlSearch.text.toString().isNotEmpty()){
                    binding.searchMyongjiRv.visibility = View.VISIBLE
                    binding.goodCafeDrinkTv.visibility = View.INVISIBLE
                    binding.horizonSv.visibility = View.INVISIBLE
                    binding.goodPlaceMyongji.visibility = View.INVISIBLE
                    binding.searchMyongjiRecommend.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchPagingResult.collectLatest {
                searchFoodAdapter.submitData(it)
            }
        }

        binding.itemSearchHashtag1.setOnClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToTagFragment("맛집")
            findNavController().navigate(action)
        }

        binding.itemSearchHashtag2.setOnClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToTagFragment("카페")
            findNavController().navigate(action)
        }

        binding.itemSearchHashtag3.setOnClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToTagFragment("술집")
            findNavController().navigate(action)
        }

        binding.itemSearchHashtag4.setOnClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToTagFragment("빵집")
            findNavController().navigate(action)
        }
}

    private fun searchBooks(){
        var startTime = System.currentTimeMillis()
        var endTime: Long

        binding.tlSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if (endTime - startTime >= SEARCH_FOODS_TIME_DELAY) {
                text?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        searchViewModel.searchPagingFood(query)
                    }
                }
            }
            startTime = endTime
        }
    }

    private fun setUpRecyclerView(){
        searchFoodAdapter = SearchFoodPagingAdapter(this)
        binding.searchMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter = searchFoodAdapter.withLoadStateFooter(
                footer = SearchFoodLoadStateAdapter()
            )
        }

        searchFoodAdapter.setOnItemClickListener {
            val action  = SearchFragmentDirections.actionFragmentSearchToRestaurantFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun setupLoadState(){
        searchFoodAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source

            val isListEmpty = searchFoodAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading && binding.tlSearch.text.toString().isNotEmpty()

            binding.tvEmptylist.isVisible = isListEmpty

            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
        }
    }

    private fun setUpRecommendRecyclerView(){
        searchRecommendAdapter = SearchFoodAdapter(this)
        binding.searchMyongjiRecommend.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = searchRecommendAdapter
        }

        searchRecommendAdapter.setOnItemClickListener {
            val action  = SearchFragmentDirections.actionFragmentSearchToRestaurantFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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

    override fun isItem(string: String){

    }

    override fun clickDirectButton(restaurant: Restaurant) {
        val action = SearchFragmentDirections.actionFragmentSearchToRestaurantFragment(restaurant)
        findNavController().navigate(action)
    }
}