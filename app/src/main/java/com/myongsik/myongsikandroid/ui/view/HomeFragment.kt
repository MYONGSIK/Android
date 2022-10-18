package com.myongsik.myongsikandroid.ui.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentHomeBinding
import com.myongsik.myongsikandroid.ui.adapter.HomeFoodAdapter
import com.myongsik.myongsikandroid.ui.adapter.HomeTodayFoodAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
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
//        getLaunchEvaluation()
//        getDinnerEvaluation()
//
//        //중식 맛있어요 버튼
//        binding.todayAfternoonGoodCl.setOnClickListener {
//            mainViewModel.saveLunchEvaluation("good")
//            Snackbar.make(view, "다른 학우분들께도 추천해주세요!", Snackbar.LENGTH_SHORT).show()
////            mainViewModel.saveEvaluation("good")
//            getLaunchEvaluation()
//        }
//
//        //중식 맛없어요 버튼
//        binding.todayAfternoonHateCl.setOnClickListener {
//            mainViewModel.saveLunchEvaluation("hate")
//            Snackbar.make(view, "다음 음식을 기대해 주세요!", Snackbar.LENGTH_SHORT).show()
////            mainViewModel.saveEvaluation("hate")
//            /*
//            이것을 왜 여기다가 적으면 다음 조회에서 왜 기존걸로 들어가는지?
//            시간이 걸리는 작업이라? ;;
//             */
//            getLaunchEvaluation()
//        }
//
//        //석식 맛있어요 버튼
//        binding.todayEveningGoodCl.setOnClickListener {
//            mainViewModel.saveDinnerEvaluation("good")
//            Snackbar.make(view, "다른 학우분들께도 추천해주세요!", Snackbar.LENGTH_SHORT).show()
//            getDinnerEvaluation()
//        }
//
//        //석식 맛없어요 버튼
//        binding.todayEveningHateCl.setOnClickListener {
//            mainViewModel.saveDinnerEvaluation("hate")
//            Snackbar.make(view, "다음 음식을 기대해 주세요!", Snackbar.LENGTH_SHORT).show()
//            getDinnerEvaluation()
//        }
    }

    //중식 맛 평가 불러오기
    //test 성공하면 바로 석식도, 근데 다른 날짜에도 똑같이 나오는게 아닌지?
    // -> WorkManager 를 통해서 하루에 한 번 초기화 할 수 있음 -> 그 전에 서버에 통신하여 맛있어요, 맛없어요 수를 셀 수 있음
    // 특정 시간에 서버에 전송 후, 초기화를 진행해주어야할듯.
    // 리사이클러뷰로 변경하면서 이 기능 업데이트 필요
//    private fun getLaunchEvaluation() {
//        lifecycleScope.launch{
//            when(mainViewModel.getLunchEvaluation()){
//                FoodEvaluation.GOOD.value -> {
//                    binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#274984")) //회색
//                    binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#274984"))
//                    binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171")) //파란색
//                    binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))
//                }
//                FoodEvaluation.HATE.value -> {
//                    binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#274984"))
//                    binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#274984"))
//                    binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
//                    binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
//                }
//                else -> {
//                    binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
//                    binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171"))
//                    binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
//                    binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))
//                }
//            }
//        }
//    }
//
//    //석식 평가 불러오기
//    private fun getDinnerEvaluation() {
//        lifecycleScope.launch{
//            when(mainViewModel.getDinnerEvaluation()){
//                FoodEvaluation.GOOD.value -> {
//                    binding.todayEveningGoodTv.setTextColor(Color.parseColor("#274984")) //회색
//                    binding.todayEveningGoodIv.setColorFilter(Color.parseColor("#274984"))
//                    binding.todayEveningHateTv.setTextColor(Color.parseColor("#717171")) //파란색
//                    binding.todayEveningHateIv.setColorFilter(Color.parseColor("#717171"))
//                }
//                FoodEvaluation.HATE.value -> {
//                    binding.todayEveningHateTv.setTextColor(Color.parseColor("#274984"))
//                    binding.todayEveningHateIv.setColorFilter(Color.parseColor("#274984"))
//                    binding.todayEveningGoodTv.setTextColor(Color.parseColor("#717171"))
//                    binding.todayEveningGoodIv.setColorFilter(Color.parseColor("#717171"))
//                }
//                else -> {
//                    binding.todayEveningGoodTv.setTextColor(Color.parseColor("#717171"))
//                    binding.todayEveningHateTv.setTextColor(Color.parseColor("#717171"))
//                    binding.todayEveningGoodIv.setColorFilter(Color.parseColor("#717171"))
//                    binding.todayEveningHateIv.setColorFilter(Color.parseColor("#717171"))
//                }
//            }
//        }
//    }

    private fun setUpRecyclerView(){
        homeTodayFoodAdapter = HomeTodayFoodAdapter()
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