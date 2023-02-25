package com.myongsik.myongsikandroid.ui.view.food

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentHomeBinding
import com.myongsik.myongsikandroid.ui.adapter.food.HomeTodayFoodAdapter
import com.myongsik.myongsikandroid.ui.adapter.food.ViewPagerAdapter
import com.myongsik.myongsikandroid.ui.view.search.LoveFragmentDirections
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.Constant.DINNER
import com.myongsik.myongsikandroid.util.Constant.LUNCH_A_GOOD
import com.myongsik.myongsikandroid.util.Constant.LUNCH_B_GOOD
import com.myongsik.myongsikandroid.util.FoodEvaluation
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

//홈화면 일간 식단 조회 프래그먼트
@AndroidEntryPoint
class HomeFragment : Fragment()  {

    private var _binding : FragmentHomeBinding?= null
    private val binding : FragmentHomeBinding
        get() = _binding!!


    private val mainViewModel by activityViewModels<MainViewModel>()

    //back button
    private lateinit var callback: OnBackPressedCallback

//    private lateinit var homeTodayFoodAdapter: HomeTodayFoodAdapter
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

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //뒤로가기 버튼시 검색화면으로
                val action = HomeFragmentDirections.actionFragmentHomeToFragmentSearch()
                findNavController().navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    //네트워크 상태 확인
    private fun getNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.weekGetFoodFun()
        mainViewModel.weekGetFood.observe(viewLifecycleOwner) {
            val food = it.data
            binding.viewPager2.adapter = ViewPagerAdapter(food)
            binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            val indicator = binding.indicator
            indicator.setViewPager(binding.viewPager2)
            }

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0 -> {
                        val drawable = context?.let { it1 ->
                            ContextCompat.getDrawable(
                                it1,
                                R.drawable.home_arrow_left
                            )
                        }
                        val color = context?.let { it1 ->
                            ContextCompat.getColor(
                                it1,
                                R.color.home_arrow_gray_color
                            )
                        }
                        if (color != null) {
                            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                        }
                        binding.homeTodayArrowLeft.setImageDrawable(drawable)
                    }
                    1 -> {
                        val drawable = context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.home_arrow_right) }
                        val color = context?.let { it1 -> ContextCompat.getColor(it1, R.color.home_arrow_gray_color) }
                        if (color != null) {
                            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                        }
            binding.homeTodayArrowRight.setImageDrawable(drawable)
                    }
                }

            }
        })

        // 뷰 페이저 페이지 설정
        val now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        val dayOfWeek = now.dayOfWeek
        Log.e("dd", dayOfWeek.toString())
        when(LocalDateTime.now().dayOfWeek.toString()){
            "MONDAY" -> {
                binding.viewPager2.setCurrentItem(0, false)

            }
            "TUESDAY" -> binding.viewPager2.setCurrentItem(1, false)
            "WEDNESDAY" -> binding.viewPager2.setCurrentItem(2, false)
            "THURSDAY" -> binding.viewPager2.setCurrentItem(3, false)
            "FRIDAY" -> {
                binding.viewPager2.setCurrentItem(4, false)
            }
        }

        // 화살표 이벤트
        binding.homeTodayArrowLeft.setOnClickListener {
            val currentIndex = binding.viewPager2.currentItem
            binding.viewPager2.currentItem = currentIndex - 1
        }
        binding.homeTodayArrowRight.setOnClickListener {
            val currentIndex = binding.viewPager2.currentItem
            binding.viewPager2.currentItem = currentIndex + 1
        }

//        mainViewModel = (activity as MainActivity).mainViewModel
        //하루가 지났을 때 AlarmManager 가 실행되면서 DataStore 가 초기화됐을 때
        if (MyongsikApplication.prefs.getString("key", "null") == "gg") {
            //하루가 지나고 DataStore 초기화 후 prefs 값을 변경시켜줌으로써 다음에는 초기화 안되게 막아둠
            LUNCH_A_GOOD = ""
            LUNCH_B_GOOD = ""
            DINNER = ""
            defaultDataStore()
            MyongsikApplication.prefs.setString("key", "todayStart")
        }else{
            getLaunchEvaluation()
            getLaunchBEvaluation()
            getDinnerEvaluation()
        }



//        setUpRecyclerView()

        //네트워크 연결 되어있는지 확인
        if(!getNetworkConnected(mainActivity.applicationContext)){
            //실패했을 경우
            binding.todayNotFoodCl.visibility = View.VISIBLE
//            binding.viewPager2.visibility = View.INVISIBLE
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
//            binding.homeWeekIcBt.setOnClickListener {
//                val action = HomeFragmentDirections.actionFragmentHomeToWeekFoodsFragment()
//                it.findNavController().navigate(action)
//            }

            //홈화면 LiveData
            mainViewModel.todayGetFood.observe(viewLifecycleOwner) {

                val dayDate = it.localDateTime.substring(0, 4)
                val dayMonth = it.localDateTime.substring(5, 7)
                val dayDay = it.localDateTime.substring(8, 10)
                val day = it.dayOfTheWeek


                binding.homeTodayDateTv.text = "${dayMonth}월 ${dayDay}일"
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
                    binding.viewPager2.visibility = View.GONE
                    binding.indicator.visibility = View.GONE
                    binding.bt.visibility = View.GONE
                    binding.homeTodayArrowRight.visibility = View.GONE
                    binding.homeTodayArrowLeft.visibility = View.GONE
                } else {
                    val food = it.data
//                    homeTodayFoodAdapter.submitList(food)
                    binding.viewPager2.visibility = View.VISIBLE
                    binding.todayNotFoodCl.visibility = View.INVISIBLE
                }
            }
        }
    }

//    override fun onPageSelected(position: Int) {
//        // 페이지 변경 이벤트 처리 코드 작성
////        if(binding.viewPager2.currentItem == 0){
////            val drawable = this.context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.home_arrow_left) }
////            val color = this.context?.let { it1 -> ContextCompat.getColor(it1, R.color.home_arrow_gray_color) }
////            if (color != null) {
////                drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
////            }
////            binding.homeTodayArrowLeft.setImageDrawable(drawable)
////        }
////        if(binding.viewPager2.currentItem == 4){
////            val drawable = this.context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.home_arrow_right) }
////            val color = this.context?.let { it1 -> ContextCompat.getColor(it1, R.color.home_arrow_gray_color) }
////            if (color != null) {
////                drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
////            }
////            binding.homeTodayArrowRight.setImageDrawable(drawable)
////        }
//    }

//    override fun onPageScrollStateChanged(state: Int) {
//        // 페이지 스크롤 상태 변경 이벤트 처리 코드 작성
//    }
//
//    override fun onPageScrolled(
//        position: Int,
//        positionOffset: Float,
//        positionOffsetPixels: Int
//    ) {
//        // 페이지 스크롤 이벤트 처리 코드 작성
//    }

    //하루가 지났을 때 DataStore 를 초기화함
    private fun defaultDataStore() {
        lifecycleScope.launch {
            mainViewModel.defaultDataStore()
        }
    }

    //중식 평가 불러오기
    private fun getLaunchEvaluation() {
        lifecycleScope.launch{
            //현재 불러온 값에 따라 값을 저장
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

    //중식 B 평가 불러오기
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

//    private fun setUpRecyclerView(){
//        homeTodayFoodAdapter = HomeTodayFoodAdapter(mainViewModel)
//        binding.rvTodaySearchResult.apply {
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//            adapter = homeTodayFoodAdapter
//        }
//
//    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}