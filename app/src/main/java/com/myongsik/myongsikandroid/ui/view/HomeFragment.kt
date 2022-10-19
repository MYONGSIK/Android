package com.myongsik.myongsikandroid.ui.view

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentHomeBinding
import com.myongsik.myongsikandroid.ui.adapter.HomeFoodAdapter
import com.myongsik.myongsikandroid.ui.adapter.HomeTodayFoodAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.Constant.DINNER
import com.myongsik.myongsikandroid.util.Constant.LUNCH_A_GOOD
import com.myongsik.myongsikandroid.util.Constant.LUNCH_B_GOOD
import com.myongsik.myongsikandroid.util.FoodEvaluation
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding?= null
    private val binding : FragmentHomeBinding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeTodayFoodAdapter: HomeTodayFoodAdapter

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
        setUpRecyclerView()

        //홈화면 식단 조회
        mainViewModel.todayGetFoodFun()

        //주간 식단 보러가기 버튼
        binding.bt.setOnClickListener {
            val action = HomeFragmentDirections.actionFragmentHomeToFragmentWeekFood()
            it.findNavController().navigate(action)
        }

        //상단 아이콘 주간 식단 보러가기 버튼
        binding.homeWeekIcBt.setOnClickListener {
            val action = HomeFragmentDirections.actionFragmentHomeToFragmentWeekFood()
            it.findNavController().navigate(action)
        }

        //홈화면 LiveData
        mainViewModel.todayGetFood.observe(viewLifecycleOwner){
            if(it.errorCode == "F0000") {
                //주말이라 식단 조회 실패했을 때
                binding.todayNotFoodCl.visibility = View.VISIBLE
                binding.rvTodaySearchResult.visibility = View.INVISIBLE
            }else{
                val food = it.data
                homeTodayFoodAdapter.submitList(food)
                binding.rvTodaySearchResult.visibility = View.VISIBLE
                binding.todayNotFoodCl.visibility = View.INVISIBLE
            }
        }

        //처음 실행했을 때 평가 값 불러오기
        //홈화면 리사이클러뷰로 변경하면서 업데이틒 필요함
        getLaunchEvaluation()
        getLaunchBEvaluation()
        getDinnerEvaluation()

    }

    //중식 맛 평가 불러오기
    //test 성공하면 바로 석식도, 근데 다른 날짜에도 똑같이 나오는게 아닌지?
    // -> WorkManager 를 통해서 하루에 한 번 초기화 할 수 있음 -> 그 전에 서버에 통신하여 맛있어요, 맛없어요 수를 셀 수 있음
    // 특정 시간에 서버에 전송 후, 초기화를 진행해주어야할듯.
    // 리사이클러뷰로 변경하면서 이 기능 업데이트 필요
    private fun getLaunchEvaluation() {
        lifecycleScope.launch{
            LUNCH_A_GOOD = when(mainViewModel.getLunchEvaluation()){
                FoodEvaluation.GOOD.value -> {
                    "good"
                }
                FoodEvaluation.HATE.value -> {
                    "hate"
                }
                else -> {
                    ""
                }
            }
        }
    }

    private fun getLaunchBEvaluation() {
        lifecycleScope.launch{
            LUNCH_B_GOOD = when(mainViewModel.getLunchBEvaluation()){
                FoodEvaluation.GOOD.value -> {
                    "good"
                }
                FoodEvaluation.HATE.value -> {
                    "hate"
                }
                else -> {
                    ""
                }
            }
        }
    }

    private fun getDinnerEvaluation() {
        lifecycleScope.launch{
            DINNER = when(mainViewModel.getDinnerEvaluation()){
                FoodEvaluation.GOOD.value -> {
                    "good"
                }
                FoodEvaluation.HATE.value -> {
                    "hate"
                }
                else -> {
                    ""
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        homeTodayFoodAdapter = HomeTodayFoodAdapter(mainViewModel)
        binding.rvTodaySearchResult.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = homeTodayFoodAdapter
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}