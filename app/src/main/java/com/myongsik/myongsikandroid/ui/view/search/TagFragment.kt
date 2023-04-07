package com.myongsik.myongsikandroid.ui.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.myongsik.myongsikandroid.BaseFragment
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.FragmentTagBinding
import com.myongsik.myongsikandroid.ui.adapter.search.OnSearchViewHolderClick
import com.myongsik.myongsikandroid.ui.adapter.search.SearchFoodPagingAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//모아뒀으기 골라보세요 화면
@AndroidEntryPoint
class TagFragment : BaseFragment<FragmentTagBinding>(), OnSearchViewHolderClick {

    private val args: TagFragmentArgs by navArgs()
    //검색 뷰모델
    private val searchViewModel by viewModels<SearchViewModel>()

    private lateinit var tagFoodAdapter: SearchFoodPagingAdapter
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTagBinding {
        return FragmentTagBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        val keyWord = args.tag

        setUpRecyclerView()
        binding.tagTopTv.text = "#명지${keyWord}"
        searchViewModel.searchPagingFood(keyWord)
    }

    override fun initListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchPagingResult.collectLatest {
                tagFoodAdapter.submitData(it)
            }
        }
    }

    private fun setUpRecyclerView(){
        tagFoodAdapter = SearchFoodPagingAdapter(this)
        binding.moaMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = tagFoodAdapter
        }

        binding.tagBackBt.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun clickDirectButton(restaurant: Restaurant) {
        val action = TagFragmentDirections.actionFragmentTagToFragmentRestaurant(restaurant)
        findNavController().navigate(action)
    }

    override fun addItem(restaurant: Restaurant) {

    }

    override fun deleteItem(restaurant: Restaurant) {

    }

    override fun isItem(string: String) {

    }
}