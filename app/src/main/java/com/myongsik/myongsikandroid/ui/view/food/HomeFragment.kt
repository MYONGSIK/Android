package com.myongsik.myongsikandroid.ui.view.food

import android.app.AlertDialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
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
import com.myongsik.myongsikandroid.util.MyongsikApplication
import com.myongsik.myongsikandroid.util.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

//홈화면 일간 식단 조회 프래그먼트
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()

    //back button
    private lateinit var callback: OnBackPressedCallback

    private lateinit var mainActivity: MainActivity

    private lateinit var localDate: LocalDate

    private var initDate: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)

        mainActivity = context as MainActivity

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //뒤로가기 버튼시 검색화면으로
                if (MyongsikApplication.prefs.getUserCampus() == "S") {
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentSearch()
                    findNavController().navigate(action)
                } else {
                    // 자캠은 식당 선택화면
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentSelectHome()
                    findNavController().navigate(action)
                }

            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (MyongsikApplication.prefs.getUserCampus() == "S") {
            mainViewModel.weekGetFoodAreaFun("MCC식당")
            mainViewModel.weekGetFoodArea.observe(viewLifecycleOwner) {
                val list = mutableListOf<List<String>>()
                // 오늘 날짜
                localDate = LocalDate.parse(it.localDateTime.substring(0, 10))
                initDate = LocalDate.parse(it.localDateTime.substring(0, 10)).dayOfWeek.value

                if (it.data.size < 2) {
                    return@observe
                }
                for (i in 0 until 15) {
                    list.add(it.data[i].meals)
                }
                val originalList: List<List<String>> = list

                val subLists = originalList.chunked(3)
                val finalList = subLists.chunked(5)

                binding.viewPager2.adapter = MyPagerAdapter(finalList[0], mainViewModel)
                setCurrentPage(LocalDate.parse(it.localDateTime.substring(0, 10)).dayOfWeek.value)

                binding.viewPager2.adapter =
                    MyPagerAdapter(finalList[0], mainViewModel)
                setCurrentPage(initDate)

                binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                val indicator = binding.indicator
                indicator.setViewPager(binding.viewPager2)
            }
        } else if (MyongsikApplication.prefs.getUserCampus() == "Y") {
            Log.e("campus", MyongsikApplication.prefs.getUserArea())
            when (MyongsikApplication.prefs.getUserArea()) {
                "S" -> {
                    mainViewModel.weekGetFoodAreaFun("교직원식당")
                    mainViewModel.weekGetFoodArea.observe(viewLifecycleOwner) {
                        val list = mutableListOf<List<String>>()
                        binding.homeTimeTv.text = String.format(
                            getString(R.string.home_time_staff_tv)
                        )
                        // 오늘 날짜
                        localDate = LocalDate.parse(it.localDateTime.substring(0, 10))
                        initDate = LocalDate.parse(it.localDateTime.substring(0, 10)).dayOfWeek.value

                        if (it.data.size < 2) {
                            return@observe
                        }
                        it.data.forEach { foodResult ->
                            list.add(foodResult.meals)
                        }
                        val finalList = list.chunked(2).chunked(5)

                        binding.viewPager2.adapter = MyPagerAdapter(
                            finalList[0],
                            mainViewModel
                        )
                        setCurrentPage(LocalDate.parse(it.localDateTime.substring(0, 10)).dayOfWeek.value)
                        binding.homeTodayDateTv.text = getString(R.string.date_format, it.localDateTime.substring(5, 7), it.localDateTime.substring(8, 10))
                        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        val indicator = binding.indicator
                        indicator.setViewPager(binding.viewPager2)
                    }

                }
                "L" -> {
                    mainViewModel.weekGetFoodAreaFun("생활관식당")
                    mainViewModel.weekGetFoodArea.observe(viewLifecycleOwner) {
                        val list = mutableListOf<List<String>>()

                        binding.homeTimeTv.text = String.format(
                            getString(R.string.home_time_life_tv)
                        )
                        // 오늘 날짜
                        localDate = LocalDate.parse(it.localDateTime.substring(0, 10))
                        initDate = LocalDate.parse(it.localDateTime.substring(0, 10)).dayOfWeek.value

                        if (it.data.size < 2) {
                            return@observe
                        }

                        for (i in 0 until 10) {
                            list.add(it.data[i].meals)
                        }
                        val originalList: List<List<String>> = list

                        val subLists = originalList.chunked(2)
                        val finalList = subLists.chunked(5)

                        binding.viewPager2.adapter = MyPagerAdapter(
                            finalList[0],
                            mainViewModel
                        )
                        setCurrentPage(LocalDate.parse(it.localDateTime.substring(0, 10)).dayOfWeek.value)
                        binding.homeTodayDateTv.text = getString(R.string.date_format, it.localDateTime.substring(5, 7), it.localDateTime.substring(8, 10))
                        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        val indicator = binding.indicator
                        indicator.setViewPager(binding.viewPager2)
                    }

                }
                "H" -> {
                    mainViewModel.weekGetFoodAreaFun("학생식당")
                    binding.homeTimeTv.text = String.format(
                        getString(R.string.home_time_student_tv)
                    )
                    mainViewModel.weekGetFoodArea.observe(viewLifecycleOwner) {
                        val list = mutableListOf<List<String>>()

                        // 오늘 날짜
                        localDate = LocalDate.parse(it.localDateTime.substring(0, 10))
                        initDate = LocalDate.parse(it.localDateTime.substring(0, 10)).dayOfWeek.value

                        if (it.data.size < 2) {
                            return@observe
                        }

                        for (i in 0 until 10) {
                            list.add(it.data[i].meals)
                        }
                        val originalList: List<List<String>> = list

                        val subLists = originalList.chunked(2)
                        val finalList = subLists.chunked(5)

                        binding.viewPager2.adapter = MyPagerAdapter(
                            finalList[0],
                            mainViewModel
                        )
                        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        val indicator = binding.indicator
                        indicator.setViewPager(binding.viewPager2)
                        setCurrentPage(LocalDate.parse(it.localDateTime.substring(0, 10)).dayOfWeek.value)
                        binding.homeTodayDateTv.text = getString(R.string.date_format, it.localDateTime.substring(5, 7), it.localDateTime.substring(8, 10))

                    }
                }
                "M" -> {
                    mainViewModel.weekGetFoodAreaFun("명진당식당")
                    binding.homeTimeTv.text = String.format(
                        getString(R.string.home_time_myonog_tv)
                    )
                    mainViewModel.weekGetFoodArea.observe(viewLifecycleOwner) {
                        val list = mutableListOf<List<String>>()
                        settingDate(LocalDate.parse(it.localDateTime.substring(0, 10)))
                        Log.e("s", it.data.toString())

                        for (i in 0 until it.data.size) {
                            list.add(it.data[i].meals)
                        }
                        val originalList: List<List<String>> = list

                        val subLists = originalList.chunked(3)
                        val finalList = subLists.chunked(5)

                        binding.viewPager2.adapter =
                            MyPagerAdapter(finalList[0], mainViewModel)
                        setCurrentPage(initDate)

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
                val monday = localDate.minusDays(((initDate - 1).toLong()))
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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                drawable?.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
                            } else {
                                drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                            }
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
                        binding.homeTodayDateTv.text = getString(R.string.date_format, fid.toString().substring(5, 7), fid.toString().substring(8, 10))
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
                when (position) {
                    0 -> binding.homeTodayDateTv.text = getString(R.string.date_format, monday.toString().substring(5, 7), monday.toString().substring(8, 10))
                    1 -> binding.homeTodayDateTv.text = getString(R.string.date_format, tues.toString().substring(5, 7), tues.toString().substring(8, 10))
                    2 -> binding.homeTodayDateTv.text = getString(R.string.date_format, wed.toString().substring(5, 7), wed.toString().substring(8, 10))
                    3 -> binding.homeTodayDateTv.text = getString(R.string.date_format, thurs.toString().substring(5, 7), thurs.toString().substring(8, 10))
                    4 -> binding.homeTodayDateTv.text = getString(R.string.date_format, fid.toString().substring(5, 7), fid.toString().substring(8, 10))
                }
            }
        })

        initViews()

        //하루가 지났을 때 AlarmManager 가 실행되면서 DataStore 가 초기화됐을 때
        setEvaluationData()

        //네트워크 연결 되어있는지 확인
        chekNetwork()

        //주말
        checkWeekend()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setEvaluationData() {
        if (MyongsikApplication.prefs.getString("key", "null") == "gg") {
            //하루가 지나고 DataStore 초기화 후 prefs 값을 변경시켜줌으로써 다음에는 초기화 안되게 막아둠
            initEvaluation()
        } else {
            getEvaluation()
        }
    }

    private fun initEvaluation() {
        LUNCH_A_GOOD = ""
        LUNCH_B_GOOD = ""
        DINNER = ""
        LUNCH_A_GOOD_S = ""
        DINNER_S = ""
        LUNCH_A_GOOD_H = ""
        DINNER_H = ""
        defaultDataStore()
        MyongsikApplication.prefs.setString("key", "todayStart")
    }

    private fun chekNetwork() {
        if (!NetworkUtils.getNetworkConnected(context)) {
            //실패했을 경우
            binding.todayNotFoodCl.visibility = View.VISIBLE
            binding.viewPager2.visibility = View.INVISIBLE
            binding.todayDayNotFoodTv.visibility = View.INVISIBLE
            binding.todayDayNotNoticeTv.text = "네트워크 상태를 확인해주세요."
        }
    }

    private fun initViews() {
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
        binding.bt.setOnClickListener {
            val dialogUtils = DialogUtils(requireContext())
            dialogUtils.showWriteReviewDialog { editText ->
                val review = editText.text.toString()
                if (review.isEmpty()) {
                    dialogUtils.showConfirmDialog("의견 작성", "의견을 작성해주세요!") {}
                } else {
                    // 리뷰 작성
                    writeMenu(review)

                    mainViewModel.postReviewData.observe(viewLifecycleOwner) {
                        if (it.success) {
                            val anotherDialogView = LayoutInflater.from(context).inflate(R.layout.item_review_dialog, null)
                            val anotherDialog = AlertDialog.Builder(context)
                                .setView(anotherDialogView)
                                .create().apply {
                                    setCancelable(false)
                                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                    show()
                                }

                            lifecycleScope.launch {
                                delay(1000L)
                                anotherDialog.dismiss()
                            }
                        }
                    }
                }
            }
        }
    }


    private fun writeMenu(review: String) {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)

        val request = RequestReviewData(
            review,
            formattedDate,
            MyongsikApplication.prefs.getUserID()
        )
        mainViewModel.postReview(request)
    }

    private fun checkWeekend() {
        if (Calendar.getInstance()
                .get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || Calendar.getInstance()
                .get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
        ) {
            val dialogUtils = context?.let { DialogUtils(it) }
            dialogUtils?.showConfirmDialog(
                "주말 운영 안내",
                "주말에는 식당을 운영하지 않습니다.",
                yesClickListener = {
                })
        }
        binding.viewPager2.visibility = View.VISIBLE
        binding.todayNotFoodCl.visibility = View.INVISIBLE
        binding.indicator.visibility = View.VISIBLE
    }

    private fun setCurrentPage(value: Int) {
        binding.viewPager2.setCurrentItem(value - 1, false)
    }

    //하루가 지났을 때 DataStore 를 초기화함
    private fun defaultDataStore() {
        lifecycleScope.launch {
            mainViewModel.defaultDataStore()
        }
    }
    private fun settingDate(localDateTime: LocalDate) {
        // 오늘 날짜
        localDate = localDateTime
        initDate = localDateTime.dayOfWeek.value
        if (initDate == 7){
            localDate = localDate.plusDays(1)
            initDate = 1
        }

    }

    //중식 평가 불러오기
    private fun getEvaluation() {
        lifecycleScope.launch {
            //현재 불러온 값에 따라 값을 저장
            LUNCH_A_GOOD = mainViewModel.getLunchEvaluation()
            LUNCH_B_GOOD = mainViewModel.getLunchBEvaluation()
            DINNER = mainViewModel.getDinnerEvaluation()
            DINNER_S = mainViewModel.getDinnerSEvaluation()
            LUNCH_A_GOOD_S = mainViewModel.getLunchSEvaluation()
            DINNER_H = mainViewModel.getDinnerHEvaluation()
            LUNCH_A_GOOD_H = mainViewModel.getLunchHEvaluation()
        }
    }
}