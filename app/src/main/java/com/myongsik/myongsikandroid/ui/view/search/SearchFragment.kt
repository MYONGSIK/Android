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
import com.myongsik.myongsikandroid.data.model.food.OnLoveClick
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
class SearchFragment : Fragment(), OnLoveClick {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val intRandom = Random().nextInt(19)

    //back button
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


        //검색 리사이클러뷰
        setUpRecyclerView()

        //검색 viewModel
        searchBooks()

        //추천 리사이클러뷰 10개씩 나오게끔
        setUpRecommendRecyclerView()

        //검색 어댑터와 LoadState Adapter 를 연결
        setupLoadState()

        //뷰가 생성될 때 마다 위의 배열에서의 랜덤값
        searchViewModel.searchRecommendFood(foodList[intRandom])


        //검색 아이콘 클릭했을 때
        binding.searchIcIv.setOnClickListener {
            //검색 백버튼, 검색 et 보이게
            binding.searchBackBt.visibility = View.VISIBLE
            binding.tlSearch.visibility = View.VISIBLE

            //검색 아이콘, 명지 맛집 안보이게
            binding.searchTopTv.visibility = View.INVISIBLE
            binding.searchIcIv.visibility = View.INVISIBLE
        }

        binding.searchBackBt.setOnClickListener {
            //검색 백버튼, 검색 et 안보이게
            binding.searchBackBt.visibility = View.INVISIBLE
            binding.tlSearch.visibility = View.INVISIBLE

            binding.tvEmptylist.visibility = View.INVISIBLE

            //검색 아이콘, 명지 맛집 보이게
            binding.searchTopTv.visibility = View.VISIBLE
            binding.searchIcIv.visibility = View.VISIBLE

            //검색 하던거 null 로 변환
            binding.tlSearch.text = null
        }

        //추천 viewModel
        searchViewModel.resultRecommendSearch.observe(viewLifecycleOwner) { response ->
            val foods = response.documents
            searchRecommendAdapter.submitList(foods)
        }

        binding.tlSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력하기 전에는 검색 리사이클러뷰를 안보이게하고, 나머지는 다 보이게
                binding.searchMyongjiRv.visibility = View.INVISIBLE

                binding.goodCafeDrinkTv.visibility = View.VISIBLE
                binding.horizonSv.visibility = View.VISIBLE
                binding.goodPlaceMyongji.visibility = View.VISIBLE
                binding.searchMyongjiRecommend.visibility = View.VISIBLE

                //검색결과가 없습니다 안보이게
                binding.tvEmptylist.visibility = View.INVISIBLE
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 검색하기 시작했는데, editText 가 비어있지 않으면 검색을 하고있다는 뜻이니까
                //검색 리사이클러뷰는 보이게, 나머지는 다 안보이게
                if (binding.tlSearch.text.toString().isNotEmpty()) {
                    //검색 리사이클러뷰 보이게
                    binding.searchMyongjiRv.visibility = View.VISIBLE

                    //밑에 추천 뷰 모두 안보이게
                    binding.goodCafeDrinkTv.visibility = View.INVISIBLE
                    binding.horizonSv.visibility = View.INVISIBLE
                    binding.goodPlaceMyongji.visibility = View.INVISIBLE
                    binding.searchMyongjiRecommend.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {


            }
        })

        //검색 페이징
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchPagingResult.collectLatest {
                searchFoodAdapter.submitData(it)
            }
        }

        //명지 맛집 클릭했을 때
        binding.itemSearchHashtag1.setOnClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToTagFragment("맛집")
            findNavController().navigate(action)
        }

        //명지 카페 클릭했을 떄
        binding.itemSearchHashtag2.setOnClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToTagFragment("카페")
            findNavController().navigate(action)
        }

        //명지 술집 클릭했을 때
        binding.itemSearchHashtag3.setOnClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToTagFragment("술집")
            findNavController().navigate(action)
        }

        //명지 빵집 클릭했을 때
        binding.itemSearchHashtag4.setOnClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToTagFragment("빵집")
            findNavController().navigate(action)
        }


    }

    //검색 기능
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

    //검색 리사이클러뷰
    private fun setUpRecyclerView() {
        searchFoodAdapter = SearchFoodPagingAdapter(this)
        binding.searchMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter = searchFoodAdapter.withLoadStateFooter(
                footer = SearchFoodLoadStateAdapter()
            )
        }

        //검색 리사이클러뷰 아이템 클릭
        searchFoodAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToRestaurantFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun setupLoadState() {
        //load State 값을 받아옴
        searchFoodAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source //source 의 값을 받아옴

            //리스트가 비어있는지 판단을 함
            val isListEmpty = searchFoodAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading && binding.tlSearch.text.toString().isNotEmpty()

            //검색결과가 없으면 noResult 를 보여줌
            binding.tvEmptylist.isVisible = isListEmpty

            //로딩중일떄는 프로그레스바를 보이게함
            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading

        }
    }


    //추천 리사이클러뷰
    private fun setUpRecommendRecyclerView() {
        searchRecommendAdapter = SearchFoodAdapter(this)
        binding.searchMyongjiRecommend.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = searchRecommendAdapter
        }

        //추천 리사이클러뷰 아이템 클릭
        searchRecommendAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToRestaurantFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //뒤로가기 버튼 시 앱 종료
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


    override fun isItem(string: String) {

    }
}