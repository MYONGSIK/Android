package com.myongsik.presentation.view.search

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.myongsik.data.model.food.GetRankRestaurant
import com.myongsik.data.model.kakao.Restaurant
import com.myongsik.data.model.kakao.toRankRestaurant
import com.myongsik.myongsikandroid.base.BaseFragment
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentSearchBinding
import com.myongsik.presentation.adapter.food.OnScrapViewHolderClick
import com.myongsik.presentation.adapter.food.RankHeaderAdapter
import com.myongsik.presentation.adapter.food.RankRestaurantAdapter
import com.myongsik.presentation.adapter.search.OnSearchViewHolderClick
import com.myongsik.presentation.adapter.search.SearchFoodPagingAdapter
import com.myongsik.presentation.adapter.state.SearchFoodLoadStateAdapter
import com.myongsik.presentation.viewmodel.food.HomeViewModel
import com.myongsik.presentation.viewmodel.search.SearchViewModel
import com.myongsik.myongsikandroid.util.CommonUtil
import com.myongsik.myongsikandroid.util.Constant.SEARCH_FOODS_TIME_DELAY
import com.myongsik.myongsikandroid.util.DataStoreKey
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint class SearchFragment : BaseFragment<FragmentSearchBinding>(),
    OnSearchViewHolderClick,
    OnScrapViewHolderClick {

    //검색 뷰모델, 현재 의존성 주입 안함
    private val searchViewModel by viewModels<SearchViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()

    //검색 어댑터 -> PagingAdapter
    private lateinit var searchFoodAdapter: SearchFoodPagingAdapter

    //추천 어댑터
    private lateinit var rankRestaurantAdapter: RankRestaurantAdapter

    private var backKeyPressTime = 0L
    private lateinit var currentMenu: String
    private val foodList: Array<String> by lazy { resources.getStringArray(R.array.food_list) }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        MyongsikApplication.prefs.setString("newUser", "notNew")
        setUpRecyclerView()
        setUpRankRestaurantRV()
        searchBooks()
        setupLoadState()
        initRefreshLayout()
        getFoodData()
        initViews()
    }

    override fun initListener() {
        initRankObserve()
        initRecomendObserve()
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchPagingResult.collectLatest {
                searchFoodAdapter.submitData(it)
            }
        }

        settingBackPressedCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.searchTopV.visibility == View.VISIBLE) {
                    if (System.currentTimeMillis() > backKeyPressTime + 2000) {
                        backKeyPressTime = System.currentTimeMillis()
                        Snackbar.make(
                            binding.fragmentSearch, getString(R.string.back_button_warning), Snackbar.LENGTH_SHORT
                        ).show()

                    } else if (System.currentTimeMillis() <= backKeyPressTime + 2000) {
                        activity?.finish()
                    }
                } else {
                    searchVisibleControl()
                }
            }
        })
    }

    private fun initViews() = with(binding) {
        searchIcIv.setOnClickListener {
            searchBackBt.visibility = View.VISIBLE
            tlSearch.visibility = View.VISIBLE
            searchTopV.visibility = View.INVISIBLE
            searchFindV.visibility = View.VISIBLE
            searchTopTv.visibility = View.INVISIBLE
            searchIcIv.visibility = View.INVISIBLE
            tlSearch.requestFocus()
            CommonUtil.showKeyboard(tlSearch, requireActivity())
        }

        searchBackBt.setOnClickListener {
            CommonUtil.hideKeyboard(requireActivity())
            searchVisibleControl()
        }

        tlSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchMyongjiRv.visibility = View.INVISIBLE
                searchMyongjiRank.visibility = View.VISIBLE
                tvEmptylist.visibility = View.INVISIBLE
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (tlSearch.text.toString().isNotEmpty()) {
                    searchMyongjiRv.visibility = View.VISIBLE
                    searchMyongjiRank.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun initRankObserve() {
        homeViewModel.rankRestaurantResponse.observe(viewLifecycleOwner) {
            binding.refreshLayout.isRefreshing = false
            val response = it.data.content
            rankRestaurantAdapter.submitList(response)
        }
    }

    private fun initRecomendObserve() {
        currentMenu = getString(R.string.rank_sort_menu_popularity)
        searchViewModel.resultRecommendSearch.observe(this) { response ->
            binding.refreshLayout.isRefreshing = false
            val foods = response.toRankRestaurant()
            rankRestaurantAdapter.submitList(foods)
        }
    }

    private fun searchVisibleControl() {
        with(binding) {
            searchBackBt.visibility = View.INVISIBLE
            tlSearch.visibility = View.INVISIBLE
            searchTopV.visibility = View.VISIBLE
            searchFindV.visibility = View.INVISIBLE
            tvEmptylist.visibility = View.INVISIBLE
            searchTopTv.visibility = View.VISIBLE
            searchIcIv.visibility = View.VISIBLE
            tlSearch.text = null
        }
    }

    private fun initRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener {
            val randomPosition = Random.nextInt(foodList.size)
            if (currentMenu == getString(R.string.rank_sort_menu_popularity) || currentMenu == getString(R.string.rank_sort_menu_distance)) {
                getFoodData()
            } else {
                searchViewModel.searchRecommendFood(foodList[randomPosition])
            }
        }
    }

    private fun searchBooks() {
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

    private fun setUpRecyclerView() {
        searchFoodAdapter = SearchFoodPagingAdapter(this)
        binding.searchMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter = searchFoodAdapter.withLoadStateFooter(
                footer = SearchFoodLoadStateAdapter()
            )
        }
    }

    private fun setupLoadState() {
        searchFoodAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source

            val isListEmpty = searchFoodAdapter.itemCount < 1 && loadState.refresh is LoadState.NotLoading && binding.tlSearch.text.toString().isNotEmpty()

            binding.tvEmptylist.isVisible = isListEmpty

            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
        }
    }

    private fun setUpRankRestaurantRV() {
        lifecycleScope.launch {
            val headerAdapter = RankHeaderAdapter(this@SearchFragment, getSorTypePosition())
            rankRestaurantAdapter = RankRestaurantAdapter(this@SearchFragment)
            binding.searchMyongjiRank.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = ConcatAdapter(headerAdapter, rankRestaurantAdapter)
            }
        }
    }

    override fun addItem(restaurant: Restaurant) {

    }

    override fun deleteItem(restaurant: Restaurant) {

    }

    override fun isItem(string: String) {

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
            x = getRankRestaurant.latitude ?: " ",
            y = getRankRestaurant.longitude ?: " "
        )
        val action = SearchFragmentDirections.actionFragmentSearchToRestaurantFragment(restaurant)
        findNavController().navigate(action)
    }

    override fun onHashtagGoodFoodClick() {
        val action = SearchFragmentDirections.actionFragmentSearchToTagFragment(getString(R.string.tag_good_restaurant))
        findNavController().navigate(action)
    }

    override fun onHashtagGoodCafeClick() {
        val action = SearchFragmentDirections.actionFragmentSearchToTagFragment(getString(R.string.tag_good_cafe))
        findNavController().navigate(action)
    }

    override fun onHashtagGoodDrinkClick() {
        val action = SearchFragmentDirections.actionFragmentSearchToTagFragment(getString(R.string.tag_good_drink))
        findNavController().navigate(action)
    }

    override fun onHashtagGoodBreadClick() {
        val action = SearchFragmentDirections.actionFragmentSearchToTagFragment(getString(R.string.tag_good_bread))
        findNavController().navigate(action)
    }

    override fun onSelectSortMenu(sort: String) {
        currentMenu = sort
        homeViewModel.saveSortType(DataStoreKey.SORT_TYPE, sort)
        val randomPosition = Random.nextInt(foodList.size)
        when (sort) {
            getString(R.string.rank_sort_menu_popularity) -> {
                homeViewModel.getRankRestaurant()
            }
            getString(R.string.rank_sort_menu_suggestion) -> {
                searchViewModel.searchRecommendFood(foodList[randomPosition])
            }
            getString(R.string.rank_sort_menu_distance) -> {
                homeViewModel.getDistanceRestaurant()
            }
            else -> {
                return
            }
        }
    }

    private fun getFoodData() {
        lifecycleScope.launch {
            val randomPosition = Random.nextInt(foodList.size)
            when (homeViewModel.getCurrentSortType()) {
                getString(R.string.rank_sort_menu_suggestion) -> {
                    searchViewModel.searchRecommendFood(foodList[randomPosition])
                }
                getString(R.string.rank_sort_menu_distance) -> {
                    homeViewModel.getDistanceRestaurant()
                }
                else -> {
                    homeViewModel.getRankRestaurant()
                }
            }
        }
    }

    private suspend fun getSorTypePosition(): Int {
        val sortTypePosition = when (homeViewModel.getCurrentSortType()) {
            getString(R.string.rank_sort_menu_suggestion) -> {
                1
            }
            getString(R.string.rank_sort_menu_distance) -> {
                2
            }
            else -> {
                0
            }
        }
        return sortTypePosition
    }
}