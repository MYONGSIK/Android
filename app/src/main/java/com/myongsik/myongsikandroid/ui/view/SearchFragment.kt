package com.myongsik.myongsikandroid.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myongsik.myongsikandroid.databinding.FragmentSearchBinding
import com.myongsik.myongsikandroid.ui.adapter.SearchFoodAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModelProviderFactory
import com.myongsik.myongsikandroid.util.Constant.SEARCH_FOODS_TIME_DELAY
import java.util.*

class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding?= null
    private val binding : FragmentSearchBinding
        get() = _binding!!

    private val intRandom = Random().nextInt(19)

    private val foodList = arrayOf(
        "부대찌개", "국밥", "마라탕", "중식", "한식", "카페", "족발", "술집", // 8
        "파스타", "커피", "삼겹살", "치킨", "떡볶이", "햄버거", "피자", "초밥", // 8
        "회", "곱창", "냉면", "닭발" //4  -> 총 20개
    )

    private val searchViewModel : SearchViewModel by viewModels{
        SearchViewModelProviderFactory()
    }

    private lateinit var searchFoodAdapter: SearchFoodAdapter
    private lateinit var searchRecommendAdapter : SearchFoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        println("랜덤 값 : $intRandom")

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

        //뷰가 생성될 때 마다 위의 배열에서의 랜덤값
        searchViewModel.searchRecommendFood(foodList[intRandom])

        //추천 viewModel
        searchViewModel.resultRecommendSearch.observe(viewLifecycleOwner){ response ->
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
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 검색하기 시작했는데, editText 가 비어있지 않으면 검색을 하고있다는 뜻이니까
                //검색 리사이클러뷰는 보이게, 나머지는 다 안보이게
                if(binding.tlSearch.text.toString().isNotEmpty()){
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

        //검색 viewmodel
        searchViewModel.resultSearch.observe(viewLifecycleOwner){ response ->
            val foods = response.documents
            searchFoodAdapter.submitList(foods)
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
    }

    private fun searchBooks(){
        var startTime = System.currentTimeMillis()
        var endTime : Long

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

    //검색 리사이클러뷰
    private fun setUpRecyclerView(){
        searchFoodAdapter = SearchFoodAdapter()
        binding.searchMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = searchFoodAdapter
        }
    }

    //추천 리사이클러뷰
    private fun setUpRecommendRecyclerView(){
        searchRecommendAdapter = SearchFoodAdapter()
        binding.searchMyongjiRecommend.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = searchRecommendAdapter
        }
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}