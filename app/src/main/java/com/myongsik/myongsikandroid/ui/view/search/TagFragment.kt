package com.myongsik.myongsikandroid.ui.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.myongsik.myongsikandroid.data.model.food.OnLoveClick
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.FragmentTagBinding
import com.myongsik.myongsikandroid.ui.adapter.search.SearchFoodPagingAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

//모아뒀으기 골라보세요 화면
@AndroidEntryPoint
class TagFragment : Fragment(), OnLoveClick {

    private var _binding : FragmentTagBinding?= null
    private val binding : FragmentTagBinding
        get() = _binding!!

    private val args : TagFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val searchViewModel : SearchViewModel by viewModels{
        viewModelFactory
    }
    private val mainViewModel by activityViewModels<MainViewModel>()

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

    private fun setUpRecyclerView(){
        tagFoodAdapter = SearchFoodPagingAdapter(this)
        binding.moaMyongjiRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = tagFoodAdapter
        }

        tagFoodAdapter.setOnItemClickListener {
            val action = TagFragmentDirections.actionFragmentTagToFragmentRestaurant(it)
            findNavController().navigate(action)
        }

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
}