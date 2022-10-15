package com.myongsik.myongsikandroid.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentHomeBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding?= null
    private val binding : FragmentHomeBinding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = (activity as MainActivity).mainViewModel

        binding.bt.setOnClickListener {
            val action = HomeFragmentDirections.actionFragmentHomeToFragmentWeekFood()
            it.findNavController().navigate(action)
        }

        mainViewModel.todayGetFoodFun()

        var day : String?
        var dayDate : String?
        var dayMonth : String?
        var dayDay : String?

        mainViewModel.todayGetFood.observe(viewLifecycleOwner){
            if(it.errorCode == "F0000") {
                binding.todayAfternoonFood.visibility = View.INVISIBLE
                binding.todayEveningFood.visibility = View.INVISIBLE
                binding.todayNotFoodCl.visibility = View.VISIBLE
                dayDate = it.localDateTime.substring(0, 4)
                dayMonth = it.localDateTime.substring(5, 7)
                dayDay = it.localDateTime.substring(8, 10)
                day = it.dayOfTheWeek
                binding.todayDayNotFoodTv.text = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day"
            }else{
                binding.todayNotFoodCl.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}