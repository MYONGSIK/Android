package com.myongsik.myongsikandroid.ui.view.food

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.databinding.FragmentHomeBinding
import com.myongsik.myongsikandroid.ui.adapter.food.MyPagerAdapter
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.Constant.DINNER
import com.myongsik.myongsikandroid.util.Constant.DINNER_H
import com.myongsik.myongsikandroid.util.Constant.DINNER_S
import com.myongsik.myongsikandroid.util.Constant.LUNCH_A_GOOD
import com.myongsik.myongsikandroid.util.Constant.LUNCH_A_GOOD_H
import com.myongsik.myongsikandroid.util.Constant.LUNCH_A_GOOD_S
import com.myongsik.myongsikandroid.util.Constant.LUNCH_B_GOOD
import com.myongsik.myongsikandroid.util.DialogUtils
import com.myongsik.myongsikandroid.util.FoodEvaluation
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

//홈화면 일간 식단 조회 프래그먼트
@AndroidEntryPoint
class HomeFragment : Fragment()  {

    private var _binding : FragmentHomeBinding?= null
    private val binding : FragmentHomeBinding
        get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()

    //back button
    private lateinit var callback: OnBackPressedCallback

    private lateinit var mainActivity: MainActivity

    private lateinit var localDate : LocalDate

    private var initDate : Int = 0


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
        if (MyongsikApplication.prefs.getUserCampus() == "S") {
            mainViewModel.weekGetFoodAreaFun("MCC식당")
            mainViewModel.weekGetFoodArea.observe(viewLifecycleOwner) {
                val list =  mutableListOf<List<String>>()

                for (i in 0 until 15){
                    list.add(it.data[i].meals)
                }
                val originalList: List<List<String>> = list

                val subLists = originalList.chunked(3)
                val finalList = subLists.chunked(5)

                // 오늘 날짜
                localDate = LocalDate.parse(it.localDateTime.substring(0,10))
                initDate = LocalDate.parse(it.localDateTime.substring(0,10)).dayOfWeek.value

                binding.viewPager2.adapter = MyPagerAdapter(finalList[0], it.data[0].mealId, mainViewModel)
                setCurrentPage(LocalDate.parse(it.localDateTime.substring(0,10)).dayOfWeek.value)

                binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                val indicator = binding.indicator
                indicator.setViewPager(binding.viewPager2)
            }
        }else if (MyongsikApplication.prefs.getUserCampus() == "Y") {
            Log.e("campus", MyongsikApplication.prefs.getUserArea())
            when (MyongsikApplication.prefs.getUserArea()) {
                "S" -> {
                    mainViewModel.weekGetFoodAreaFun("교직원식당")
                    mainViewModel.weekGetFoodArea.observe(viewLifecycleOwner) {
                        val list =  mutableListOf<List<String>>()
                        binding.homeTimeTv.text = String.format(
                            getString(R.string.home_time_staff_tv)
                        )

                        for (i in 0 until 10){
                            list.add(it.data[i].meals)
                        }
                        val originalList: List<List<String>> = list

                        val subLists = originalList.chunked(2)
                        val finalList = subLists.chunked(5)
                        // 오늘 날짜
                        localDate = LocalDate.parse(it.localDateTime.substring(0,10))
                        initDate = LocalDate.parse(it.localDateTime.substring(0,10)).dayOfWeek.value

                        binding.viewPager2.adapter = MyPagerAdapter(
                            finalList[0],
                            it.data[0].mealId,
                            mainViewModel
                        )
                        setCurrentPage(LocalDate.parse(it.localDateTime.substring(0,10)).dayOfWeek.value)
                        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        val indicator = binding.indicator
                        indicator.setViewPager(binding.viewPager2)
                    }

                }
                "L" -> {
                    mainViewModel.weekGetFoodAreaFun("생활관식당")
                    mainViewModel.weekGetFoodArea.observe(viewLifecycleOwner) {
                        val list =  mutableListOf<List<String>>()

                        binding.homeTimeTv.text = String.format(
                            getString(R.string.home_time_life_tv)
                        )

                        for (i in 0 until 10){
                            list.add(it.data[i].meals)
                        }
                        val originalList: List<List<String>> = list

                        val subLists = originalList.chunked(2)
                        val finalList = subLists.chunked(5)
                        // 오늘 날짜
                        localDate = LocalDate.parse(it.localDateTime.substring(0,10))
                        initDate = LocalDate.parse(it.localDateTime.substring(0,10)).dayOfWeek.value

                        binding.viewPager2.adapter = MyPagerAdapter(
                            finalList[0],
                            it.data[0].mealId,
                            mainViewModel
                        )
                        setCurrentPage(LocalDate.parse(it.localDateTime.substring(0,10)).dayOfWeek.value)
                        binding.homeTodayDateTv.text = "${it.localDateTime.substring(5, 7)}월 ${it.localDateTime.substring(8,10)}일"
                        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        val indicator = binding.indicator
                        indicator.setViewPager(binding.viewPager2)
                    }

                }
                "H" -> {
                    mainViewModel.weekGetFoodAreaFun("학관식당")
                    mainViewModel.weekGetFoodArea.observe(viewLifecycleOwner) {
                        val list =  mutableListOf<List<String>>()

                        binding.homeTimeTv.text = String.format(
                            getString(R.string.home_time_life_tv)
                        )

                        for (i in 0 until 10){
                            list.add(it.data[i].meals)
                        }
                        val originalList: List<List<String>> = list

                        val subLists = originalList.chunked(2)
                        val finalList = subLists.chunked(5)
                        // 오늘 날짜
                        localDate = LocalDate.parse(it.localDateTime.substring(0,10))
                        initDate = LocalDate.parse(it.localDateTime.substring(0,10)).dayOfWeek.value

                        binding.viewPager2.adapter = MyPagerAdapter(
                            finalList[0],
                            it.data[0].mealId,
                            mainViewModel
                        )
                        setCurrentPage(LocalDate.parse(it.localDateTime.substring(0,10)).dayOfWeek.value)
                        binding.homeTodayDateTv.text = "${it.localDateTime.substring(5, 7)}월 ${it.localDateTime.substring(8,10)}일"
                        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        val indicator = binding.indicator
                        indicator.setViewPager(binding.viewPager2)
                    }
                }
            }
        }





        // 색상
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 월요일


                val monday = localDate.minusDays(((initDate-1).toLong()))
                val tues = monday.plus(1, ChronoUnit.DAYS)
                val wed = tues.plus(1, ChronoUnit.DAYS)
                val thurs = wed.plus(1, ChronoUnit.DAYS)
                val fid = thurs.plus(1, ChronoUnit.DAYS)

                when (position) {
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
                    4 -> {
                        val drawable = context?.let { it1 ->
                            ContextCompat.getDrawable(
                                it1,
                                R.drawable.home_arrow_right
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
                        binding.homeTodayArrowRight.setImageDrawable(drawable)
                        binding.homeTodayDateTv.text = "${fid.toString().substring(5,7)}월 ${fid.toString().substring(8,10)}일"
                    }
                    else -> {
                        binding.homeTodayArrowLeft.setImageDrawable(context?.let {
                            ContextCompat.getDrawable(
                                it, R.drawable.home_arrow_left
                            )
                        })
                        binding.homeTodayArrowRight.setImageDrawable(context?.let {
                            ContextCompat.getDrawable(
                                it, R.drawable.home_arrow_right
                            )
                        })
                    }

                }
                when(position) {
                    0 -> {
                        binding.homeTodayDateTv.text = "${monday.toString().substring(5, 7)}월 ${
                            monday.toString().substring(8, 10)
                        }일"

                    }
                        1 -> binding.homeTodayDateTv.text = "${tues.toString().substring(5,7)}월 ${tues.toString().substring(8,10)}일"
                    2 -> binding.homeTodayDateTv.text = "${wed.toString().substring(5,7)}월 ${wed.toString().substring(8,10)}일"
                    3 -> binding.homeTodayDateTv.text = "${thurs.toString().substring(5,7)}월 ${thurs.toString().substring(8,10)}일"
                    4 -> binding.homeTodayDateTv.text = "${fid.toString().substring(5,7)}월 ${fid.toString().substring(8,10)}일"

                }
            }
        })

        // 화살표 이벤트
        binding.homeTodayArrowLeft.setOnClickListener {
            val currentIndex = binding.viewPager2.currentItem
            binding.viewPager2.currentItem = currentIndex - 1
        }
        binding.homeTodayArrowRight.setOnClickListener {
            val currentIndex = binding.viewPager2.currentItem
            binding.viewPager2.currentItem = currentIndex + 1
        }

        // 리뷰 이벤트
        binding.bt.setOnClickListener{
            val dialogUtils = DialogUtils(requireContext())
            dialogUtils.showWriteReviewDialog { editText ->
                if (editText.text.toString().trim() == "") {
                    dialogUtils.showConfirmDialog("의견 작성", "의견을 작성해주세요!",
                        yesClickListener = {
                        })
                } else {
                    // 리뷰 작성
                    val currentDate = LocalDate.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val formattedDate = currentDate.format(formatter)

                    val request = RequestReviewData(
                        editText.text.toString(),
                        formattedDate,
                        MyongsikApplication.prefs.getUserID())

                    mainViewModel.postReview(request)
                    mainViewModel.postReviewData.observe(viewLifecycleOwner) {
                        if(it.success){
                            val anotherDialogView = LayoutInflater.from(context).inflate(R.layout.item_review_dialog, null)
                            val anotherBuilder = AlertDialog.Builder(context)
                            anotherBuilder.setView(anotherDialogView)
                            val anotherDialog = anotherBuilder.create()
                            anotherDialog.setCancelable(false)
                            anotherDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                            anotherDialog.show()

                            Handler().postDelayed({
                                anotherDialog.dismiss()
                            }, 1000L)
                        }
                }
            }
                    }
        }



//        mainViewModel = (activity as MainActivity).mainViewModel
        //하루가 지났을 때 AlarmManager 가 실행되면서 DataStore 가 초기화됐을 때
        if (MyongsikApplication.prefs.getString("key", "null") == "gg") {
            //하루가 지나고 DataStore 초기화 후 prefs 값을 변경시켜줌으로써 다음에는 초기화 안되게 막아둠
            LUNCH_A_GOOD = ""
            LUNCH_B_GOOD = ""
            DINNER = ""
            LUNCH_A_GOOD_S = ""
            DINNER_S = ""
            LUNCH_A_GOOD_H = ""
            DINNER_H = ""
            defaultDataStore()
            MyongsikApplication.prefs.setString("key", "todayStart")
        } else {
            getLaunchEvaluation()
            getLaunchBEvaluation()
            getDinnerEvaluation()
            getDinnerSEvaluation()
            getLunchSEvaluation()
            getDinnerHEvaluation()
            getLunchHEvaluation()
        }


        //네트워크 연결 되어있는지 확인
        if (!getNetworkConnected(mainActivity.applicationContext)) {
            //실패했을 경우
            binding.todayNotFoodCl.visibility = View.VISIBLE
            binding.viewPager2.visibility = View.INVISIBLE
            binding.todayDayNotFoodTv.visibility = View.INVISIBLE
            binding.todayDayNotNoticeTv.text = "네트워크 상태를 확인해주세요."
        }
        else {
            //주말
            when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                Calendar.SATURDAY -> {
                    showNotFoodDay(6)
                }
                Calendar.SUNDAY -> {
                    showNotFoodDay(7)
                }
                else -> {
                    binding.viewPager2.visibility = View.VISIBLE
                    binding.todayNotFoodCl.visibility = View.INVISIBLE
                    binding.indicator.visibility = View.VISIBLE
                }
            }
            // 들어왔을 때 position 값 = today -2 ==2
            // localdate값 여기서 왼쪽으로가면 -1 오른쪽 +1
         }
    }

    private fun showNotFoodDay(value: Int) {
        val color = if (value == 6) "#274984" else "#E31F1F"
        val dayOfWeekString = when (value) {
            6 -> "토요일"
            7 -> "일요일"
            else -> return
        }
        val calendar = Calendar.getInstance()
        binding.todayDayNotFoodTv.setTextColor(Color.parseColor(color))
        binding.todayDayNotNoticeTv.text = "금일 학생식당은 운영하지 않습니다."
        binding.todayDayNotFoodTv.text = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1 }월 ${calendar.get(Calendar.DAY_OF_MONTH)}일 $dayOfWeekString"
        binding.todayNotFoodCl.visibility = View.VISIBLE
        binding.viewPager2.visibility = View.GONE
        binding.indicator.visibility = View.GONE
        binding.bt.visibility = View.GONE
        binding.homeTodayArrowRight.visibility = View.GONE
        binding.homeTodayArrowLeft.visibility = View.GONE
    }

    private fun setCurrentPage(value: Int) {
        binding.viewPager2.setCurrentItem(value-1, false)
    }


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
    private fun getDinnerSEvaluation() {
        lifecycleScope.launch{
            DINNER_S= when(mainViewModel.getDinnerSEvaluation()){
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
    private fun getLunchSEvaluation() {
        lifecycleScope.launch{
            LUNCH_A_GOOD_S= when(mainViewModel.getLunchSEvaluation()){
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

    private fun getDinnerHEvaluation() {
        lifecycleScope.launch{
            DINNER_H= when(mainViewModel.getDinnerHEvaluation()){
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
    private fun getLunchHEvaluation() {
        lifecycleScope.launch{
            LUNCH_A_GOOD_H= when(mainViewModel.getLunchHEvaluation()){
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}