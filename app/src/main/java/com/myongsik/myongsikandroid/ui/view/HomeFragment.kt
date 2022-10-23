package com.myongsik.myongsikandroid.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.databinding.FragmentHomeBinding
import com.myongsik.myongsikandroid.ui.adapter.HomeTodayFoodAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.Constant.DINNER
import com.myongsik.myongsikandroid.util.Constant.LUNCH_A_GOOD
import com.myongsik.myongsikandroid.util.Constant.LUNCH_B_GOOD
import com.myongsik.myongsikandroid.util.FoodEvaluation
import com.myongsik.myongsikandroid.util.MyongsikApplication
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment(){

    private var _binding : FragmentHomeBinding?= null
    private val binding : FragmentHomeBinding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeTodayFoodAdapter: HomeTodayFoodAdapter
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    //Context 를 불러오기 위해
    override fun onAttach(context: Context) {

        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    //네트워크 상태 확인
    private fun getNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = (activity as MainActivity).mainViewModel
        setUpRecyclerView()

        //네트워크 연결 되어있는지 확인
        if(!getNetworkConnected(mainActivity.applicationContext)){
            //실패했을 경우
            binding.todayNotFoodCl.visibility = View.VISIBLE
            binding.rvTodaySearchResult.visibility = View.INVISIBLE
            binding.todayDayNotFoodTv.visibility = View.INVISIBLE
            binding.todayDayNotNoticeTv.text = "네트워크 상태를 확인해주세요."
        }else {
            //네트워크 성공했을 때

            //홈화면 식단 조회
            mainViewModel.todayGetFoodFun()

            //주간 식단 보러가기 버튼
            binding.bt.setOnClickListener {
                val action = HomeFragmentDirections.actionFragmentHomeToWeekFoodsFragment()
                it.findNavController().navigate(action)
            }

            //상단 아이콘 주간 식단 보러가기 버튼
            binding.homeWeekIcBt.setOnClickListener {
                val action = HomeFragmentDirections.actionFragmentHomeToWeekFoodsFragment()
                it.findNavController().navigate(action)
            }

            //홈화면 LiveData
            mainViewModel.todayGetFood.observe(viewLifecycleOwner) {

                val dayDate = it.localDateTime.substring(0, 4)
                val dayMonth = it.localDateTime.substring(5, 7)
                val dayDay = it.localDateTime.substring(8, 10)
                val day = it.dayOfTheWeek
                if (it.errorCode == "F0000") {
                    //주말이라 식단 조회 실패했을 때
                    //토요일일 경우
                    //파란색
                    if (day == "토요일")
                        binding.todayDayNotFoodTv.setTextColor(Color.parseColor("#274984"))
                    //빨간색
                    else if (day == "일요일")
                        binding.todayDayNotFoodTv.setTextColor(Color.parseColor("#E31F1F"))

                    binding.todayDayNotNoticeTv.text = it.message
                    binding.todayDayNotFoodTv.text = "${dayDate}년 ${dayMonth}월 ${dayDay}일 $day"
                    binding.todayNotFoodCl.visibility = View.VISIBLE
                    binding.rvTodaySearchResult.visibility = View.INVISIBLE
                } else {
                    val food = it.data
                    homeTodayFoodAdapter.submitList(food)
                    binding.rvTodaySearchResult.visibility = View.VISIBLE
                    binding.todayNotFoodCl.visibility = View.INVISIBLE
                }
            }

            //하루가 지났을 때 AlarmManager 가 실행되면서 DataStore 가 초기화됐을 때
            if (MyongsikApplication.prefs.getString("key", "null") == "gg") {
                //하루가 지나고 DataStore 초기화 후 prefs 값을 변경시켜줌으로써 다음에는 초기화 안되게 막아둠
                LUNCH_A_GOOD = ""
                LUNCH_B_GOOD = ""
                DINNER = ""
                defaultDataStore()
                MyongsikApplication.prefs.setString("key", "todayStart")
            } else {
                getLaunchEvaluation()
                getLaunchBEvaluation()
                getDinnerEvaluation()
            }
        }
    }

    private fun defaultDataStore() {
        lifecycleScope.launch {
            mainViewModel.defaultDataStore()
        }
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