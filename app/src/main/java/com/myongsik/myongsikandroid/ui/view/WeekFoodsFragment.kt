package com.myongsik.myongsikandroid.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.myongsik.myongsikandroid.databinding.FragmentWeekFoodsBinding
import com.myongsik.myongsikandroid.ui.adapter.ViewPagerAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

//주간 식단 조회 프래그먼트
@AndroidEntryPoint
class WeekFoodsFragment : Fragment() {

    private var _binding : FragmentWeekFoodsBinding?= null
    private val binding : FragmentWeekFoodsBinding
        get() = _binding!!

//    private lateinit var mainViewModel: MainViewModel
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeekFoodsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mainViewModel = (activity as MainActivity).mainViewModel

        mainViewModel.weekGetFoodFun()

        //뒤로가기 버튼
        binding.weekBackIcBt.setOnClickListener {
            it.findNavController().popBackStack()
        }

        mainViewModel.weekGetFood.observe(viewLifecycleOwner) {
            val food = it.data
            binding.viewPager2.adapter = ViewPagerAdapter(food)
            binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            val indicator = binding.indicator
            indicator.setViewPager(binding.viewPager2)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}