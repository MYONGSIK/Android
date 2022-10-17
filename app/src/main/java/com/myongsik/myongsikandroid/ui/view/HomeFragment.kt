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
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentHomeBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.FoodEvaluation
import kotlinx.coroutines.launch

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

        //홈화면 식단 조회
        mainViewModel.todayGetFoodFun()

        //홈화면 LiveData
        mainViewModel.todayGetFood.observe(viewLifecycleOwner){
            val dayDate = it.localDateTime.substring(0, 4)
            val dayMonth = it.localDateTime.substring(5, 7)
            val dayDay = it.localDateTime.substring(8, 10)
            val day = it.dayOfTheWeek

            val day2 = it.data[0].dayOfTheWeek //평일날

            if(it.errorCode == "F0000") {
                //주말이라 식단 조회 실패했을 때
                binding.todayAfternoonFood.visibility = View.INVISIBLE
                binding.todayEveningFood.visibility = View.INVISIBLE
                binding.todayNotFoodCl.visibility = View.VISIBLE
                binding.todayDayNotFoodTv.text = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day"
            }else{
                //평일 식단 조회 성공했을 때
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

        //처음 실행했을 때 평가 값 불러오기
        getLaunchEvaluation()
        getDinnerEvaluation()

        //중식 맛있어요 버튼
        binding.todayAfternoonGoodCl.setOnClickListener {
            mainViewModel.saveLunchEvaluation("good")
            Snackbar.make(view, "다른 학우분들께도 추천해주세요!", Snackbar.LENGTH_SHORT).show()
//            mainViewModel.saveEvaluation("good")
            getLaunchEvaluation()
        }

        //중식 맛없어요 버튼
        binding.todayAfternoonHateCl.setOnClickListener {
            mainViewModel.saveLunchEvaluation("hate")
            Snackbar.make(view, "다음 음식을 기대해 주세요!", Snackbar.LENGTH_SHORT).show()
//            mainViewModel.saveEvaluation("hate")
            /*
            이것을 왜 여기다가 적으면 다음 조회에서 왜 기존걸로 들어가는지?
            시간이 걸리는 작업이라? ;;
             */
            getLaunchEvaluation()
        }

        //석식 맛있어요 버튼
        binding.todayEveningGoodCl.setOnClickListener {
            mainViewModel.saveDinnerEvaluation("good")
            Snackbar.make(view, "다른 학우분들께도 추천해주세요!", Snackbar.LENGTH_SHORT).show()
            getDinnerEvaluation()
        }

        //석식 맛없어요 버튼
        binding.todayEveningHateCl.setOnClickListener {
            mainViewModel.saveDinnerEvaluation("hate")
            Snackbar.make(view, "다음 음식을 기대해 주세요!", Snackbar.LENGTH_SHORT).show()
            getDinnerEvaluation()
        }
    }

    //중식 맛 평가 불러오기
    //test 성공하면 바로 석식도, 근데 다른 날짜에도 똑같이 나오는게 아닌지?
    // -> WorkManager 를 통해서 하루에 한 번 초기화 할 수 있음 -> 그 전에 서버에 통신하여 맛있어요, 맛없어요 수를 셀 수 있음
    // 특정 시간에 서버에 전송 후, 초기화를 진행해주어야할듯.
    private fun getLaunchEvaluation() {
        lifecycleScope.launch{
            when(mainViewModel.getLunchEvaluation()){
                FoodEvaluation.GOOD.value -> {
                    binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#274984")) //회색
                    binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#274984"))
                    binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171")) //파란색
                    binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))
                }
                FoodEvaluation.HATE.value -> {
                    binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#274984"))
                    binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#274984"))
                    binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
                    binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
                }
                else -> {
                    binding.todayAfternoonGoodTv.setTextColor(Color.parseColor("#717171"))
                    binding.todayAfternoonHateTv.setTextColor(Color.parseColor("#717171"))
                    binding.todayAfternoonGoodIv.setColorFilter(Color.parseColor("#717171"))
                    binding.todayAfternoonHateIv.setColorFilter(Color.parseColor("#717171"))
                }
            }
        }
    }

    //석식 평가 불러오기
    private fun getDinnerEvaluation() {
        lifecycleScope.launch{
            when(mainViewModel.getDinnerEvaluation()){
                FoodEvaluation.GOOD.value -> {
                    binding.todayEveningGoodTv.setTextColor(Color.parseColor("#274984")) //회색
                    binding.todayEveningGoodIv.setColorFilter(Color.parseColor("#274984"))
                    binding.todayEveningHateTv.setTextColor(Color.parseColor("#717171")) //파란색
                    binding.todayEveningHateIv.setColorFilter(Color.parseColor("#717171"))
                }
                FoodEvaluation.HATE.value -> {
                    binding.todayEveningHateTv.setTextColor(Color.parseColor("#274984"))
                    binding.todayEveningHateIv.setColorFilter(Color.parseColor("#274984"))
                    binding.todayEveningGoodTv.setTextColor(Color.parseColor("#717171"))
                    binding.todayEveningGoodIv.setColorFilter(Color.parseColor("#717171"))
                }
                else -> {
                    binding.todayEveningGoodTv.setTextColor(Color.parseColor("#717171"))
                    binding.todayEveningHateTv.setTextColor(Color.parseColor("#717171"))
                    binding.todayEveningGoodIv.setColorFilter(Color.parseColor("#717171"))
                    binding.todayEveningHateIv.setColorFilter(Color.parseColor("#717171"))
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}