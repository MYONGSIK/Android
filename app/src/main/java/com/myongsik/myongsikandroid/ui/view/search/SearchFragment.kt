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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.food.GetRankRestaurant
import com.myongsik.myongsikandroid.ui.adapter.food.OnScrapViewHolderClick
import com.myongsik.myongsikandroid.ui.adapter.search.OnSearchViewHolderClick
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.FragmentSearchBinding
import com.myongsik.myongsikandroid.ui.adapter.food.RankRestaurantAdapter
import com.myongsik.myongsikandroid.ui.adapter.search.SearchFoodAdapter
import com.myongsik.myongsikandroid.ui.adapter.search.SearchFoodPagingAdapter
import com.myongsik.myongsikandroid.ui.adapter.state.SearchFoodLoadStateAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModel
import com.myongsik.myongsikandroid.util.CommonUtil
import com.myongsik.myongsikandroid.util.Constant.SEARCH_FOODS_TIME_DELAY
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment(), OnSearchViewHolderClick, OnScrapViewHolderClick {

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
    private val mainViewModel by viewModels<MainViewModel>()

    //검색 어댑터 -> PagingAdapter
    private lateinit var searchFoodAdapter: SearchFoodPagingAdapter

    //추천 어댑터
    private lateinit var searchRecommendAdapter: SearchFoodAdapter
    private lateinit var rankRestaurantAdapter: RankRestaurantAdapter

    private var backKeyPressTime = 0L

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
        MyongsikApplication.prefs.setString("newUser", "notNew")

        setUpRecyclerView()
        setUpRankRestaurantRV()
        searchBooks()

        setupLoadState()

        mainViewModel.getRankRestaurant()
        binding.searchIcIv.setOnClickListener {
            binding.searchBackBt.visibility = View.VISIBLE
            binding.tlSearch.visibility = View.VISIBLE
            binding.searchTopV.visibility = View.INVISIBLE
            binding.searchFindV.visibility =View.VISIBLE
            binding.searchTopTv.visibility = View.INVISIBLE
            binding.searchIcIv.visibility = View.INVISIBLE
            binding.tlSearch.requestFocus()
            CommonUtil.showKeyboard(binding.tlSearch, requireActivity())
        }

        binding.searchBackBt.setOnClickListener {
            CommonUtil.hideKeyboard(requireActivity())
            binding.searchBackBt.visibility = View.INVISIBLE
            binding.tlSearch.visibility = View.INVISIBLE
            binding.searchTopV.visibility = View.VISIBLE
            binding.searchFindV.visibility =View.INVISIBLE
            binding.tvEmptylist.visibility = View.INVISIBLE
            binding.searchTopTv.visibility = View.VISIBLE
            binding.searchIcIv.visibility = View.VISIBLE

            binding.tlSearch.text = null
        }

        mainViewModel.rankRestaurantResponse.observe(viewLifecycleOwner){
            val response = it.data.content
            rankRestaurantAdapter.submitList(response)
        }

        binding.tlSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.searchMyongjiRv.visibility = View.INVISIBLE

                binding.goodCafeDrinkTv.visibility = View.VISIBLE
                binding.horizonSv.visibility = View.VISIBLE
                binding.goodPlaceMyongji.visibility = View.VISIBLE
                binding.searchMyongjiRank.visibility = View.VISIBLE
                binding.tvEmptylist.visibility = View.INVISIBLE
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.tlSearch.text.toString().isNotEmpty()){
                    binding.searchMyongjiRv.visibility = View.VISIBLE
                    binding.goodCafeDrinkTv.visibility = View.INVISIBLE
                    binding.horizonSv.visibility = View.INVISIBLE
                    binding.goodPlaceMyongji.visibility = View.INVISIBLE
                    binding.searchMyongjiRank.visibility = View.INVISIBLE
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

    private fun setUpRankRestaurantRV(){
        rankRestaurantAdapter = RankRestaurantAdapter(this)
        binding.searchMyongjiRank.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = rankRestaurantAdapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.searchTopV.visibility == View.VISIBLE){
                    if (System.currentTimeMillis() > backKeyPressTime + 2000) {
                        backKeyPressTime = System.currentTimeMillis()
                        Snackbar.make(
                            binding.fragmentSearch,
                            getString(R.string.back_button_warning),
                            Snackbar.LENGTH_SHORT
                        ).show()

                    } else if (System.currentTimeMillis() <= backKeyPressTime + 2000) {
                        activity?.finish()
                    }
                } else{
                    binding.searchBackBt.visibility = View.INVISIBLE
                    binding.tlSearch.visibility = View.INVISIBLE
                    binding.searchTopV.visibility = View.VISIBLE
                    binding.searchFindV.visibility =View.INVISIBLE
                    binding.tvEmptylist.visibility = View.INVISIBLE
                    binding.searchTopTv.visibility = View.VISIBLE
                    binding.searchIcIv.visibility = View.VISIBLE

                    binding.tlSearch.text = null
                }
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

    override fun clickRankDirectButton(getRankRestaurant: GetRankRestaurant) {
        val restaurant = Restaurant(
            address_name = getRankRestaurant.address,
            category_group_code = " ",
            category_group_name = getRankRestaurant.category,
            category_name = getRankRestaurant.category,
            distance = getRankRestaurant.distance,
            id = getRankRestaurant.code,
            phone = getRankRestaurant.contact,
            place_name = getRankRestaurant.name,
            place_url = getRankRestaurant.urlAddress,
            road_address_name = getRankRestaurant.address,
            x = " ",
            y = " "
        )
        val action = SearchFragmentDirections.actionFragmentSearchToRestaurantFragment(restaurant)
        findNavController().navigate(action)
    }
}