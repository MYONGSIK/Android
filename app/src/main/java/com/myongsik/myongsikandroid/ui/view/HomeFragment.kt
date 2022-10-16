package com.myongsik.myongsikandroid.ui.view

import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = (activity as MainActivity).mainViewModel

        binding.bt.setOnClickListener {
            val action = HomeFragmentDirections.actionFragmentHomeToFragmentWeekFood()
            it.findNavController().navigate(action)
        }

        mainViewModel.todayGetFoodFun()

        mainViewModel.todayGetFood.observe(viewLifecycleOwner){
            val dayDate = it.localDateTime.substring(0, 4)
            val dayMonth = it.localDateTime.substring(5, 7)
            val dayDay = it.localDateTime.substring(8, 10)
            val day = it.dayOfTheWeek

            val day2 = it.data[0].dayOfTheWeek //평일날

            if(it.errorCode == "F0000") {
                //주말 실패했을 때
                binding.todayAfternoonFood.visibility = View.INVISIBLE
                binding.todayEveningFood.visibility = View.INVISIBLE
                binding.todayNotFoodCl.visibility = View.VISIBLE
                binding.todayDayNotFoodTv.text = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day"
            }else{
                //평일 성공했을 때
                binding.todayEveningDay.text = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day2"
                binding.todayDay.text = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day2"

                //중식
                binding.todayFood1.text = it.data[0].food1
                binding.todayFood2.text = it.data[0].food2
                binding.todayFood3.text = it.data[0].food3
                binding.todayFood4.text = it.data[0].food4
                binding.todayFood5.text = it.data[0].food5
                binding.todayFood6.text = it.data[0].food6

                //석식
                binding.todayEveningFood1.text = it.data[1].food1
                binding.todayEveningFood2.text = it.data[1].food2
                binding.todayEveningFood3.text = it.data[1].food3
                binding.todayEveningFood4.text = it.data[1].food4
                binding.todayEveningFood5.text = it.data[1].food5
                binding.todayEveningFood6.text = it.data[1].food6


                binding.todayNotFoodCl.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}