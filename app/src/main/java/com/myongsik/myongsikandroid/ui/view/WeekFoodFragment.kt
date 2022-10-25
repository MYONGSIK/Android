package com.myongsik.myongsikandroid.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.myongsik.myongsikandroid.databinding.FragmentWeekFoodBinding
import com.myongsik.myongsikandroid.ui.adapter.HomeFoodAdapter
import com.myongsik.myongsikandroid.ui.adapter.ViewPagerAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

//ViewPager 로 변경하면서 안쓰임
@AndroidEntryPoint
class WeekFoodFragment : Fragment() {

    private var _binding : FragmentWeekFoodBinding?= null
    private val binding : FragmentWeekFoodBinding
        get() = _binding!!

//    private lateinit var mainViewModel: MainViewModel
    private val mainViewModel by activityViewModels<MainViewModel>()

    private lateinit var homeFoodAdapter: HomeFoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeekFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mainViewModel = (activity as MainActivity).mainViewModel
        setUpRecyclerView()

        mainViewModel.weekGetFoodFun()

        mainViewModel.weekGetFood.observe(viewLifecycleOwner) {
            val food = it.data
            homeFoodAdapter.submitList(food)
        }

        //뒤로가기 버튼
        binding.weekBackIcBt.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerView(){
        homeFoodAdapter = HomeFoodAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 1, LinearLayoutManager.HORIZONTAL, false)
            adapter = homeFoodAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}