package com.myongsik.myongsikandroid.ui.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.myongsik.myongsikandroid.data.model.food.OnSearchViewHolderClick
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.FragmentTagBinding
import com.myongsik.myongsikandroid.ui.adapter.search.SearchFoodPagingAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//모아뒀으기 골라보세요 화면
@AndroidEntryPoint
class TagFragment : Fragment(), OnSearchViewHolderClick {

    private var _binding: FragmentTagBinding? = null
    private val binding: FragmentTagBinding
        get() = _binding!!

    private val args: TagFragmentArgs by navArgs()

    //검색 뷰모델
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()


    //태그화면도 PagingAdapter 사용
    private lateinit var tagFoodAdapter: SearchFoodPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val keyWord = args.tag

        setUpRecyclerView()
        binding.tagTopTv.text = "#명지${keyWord}"

        searchViewModel.searchPagingFood(keyWord)
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchPagingResult.collectLatest {
                tagFoodAdapter.submitData(it)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    //해시태그 모아보기 리사이클러뷰
    private fun setUpRecyclerView() {
        tagFoodAdapter = SearchFoodPagingAdapter(this)
        binding.moaMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = tagFoodAdapter
        }

        //태그에서의 백버튼
        binding.tagBackBt.setOnClickListener {
            findNavController().popBackStack()
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
        val action = TagFragmentDirections.actionFragmentTagToFragmentRestaurant(restaurant)
        findNavController().navigate(action)
    }
}