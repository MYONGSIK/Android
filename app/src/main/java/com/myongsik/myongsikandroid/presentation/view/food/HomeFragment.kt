package com.myongsik.myongsikandroid.presentation.view.food

import android.app.AlertDialog
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.myongsik.myongsikandroid.BuildConfig
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.base.BaseFragment
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.databinding.DialogBottomUpdateSheetBinding
import com.myongsik.myongsikandroid.databinding.FragmentHomeBinding
import com.myongsik.myongsikandroid.presentation.adapter.food.MyPagerAdapter
import com.myongsik.myongsikandroid.presentation.viewmodel.food.HomeViewModel
import com.myongsik.myongsikandroid.presentation.viewmodel.food.WeekFoodState
import com.myongsik.myongsikandroid.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

//홈화면 일간 식단 조회 프래그먼트
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var localDate: LocalDate

    private var initDate: Int = 0

    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "HomeFragment"

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        if(MyongsikApplication.prefs.getString(PreferenceKey.UPDATE_KEY, getString(R.string.preference_key_see)) == getString(R.string.preference_key_see)) {
            showBottomSheetDialog()
        }

        if (Constant.isAdAvailable) {
            Constant.isAdAvailable = false
            initInterstitialAd()
            setInterstitialAd()
        }
    }

    override fun initListener() {
        initObserve()

        settingBackPressedCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (MyongsikApplication.prefs.getUserCampus() == "S") {
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentSelectSeoul()
                    findNavController().navigate(action)
                } else { // 자캠은 식당 선택화면
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentSelectHome()
                    findNavController().navigate(action)
                }
            }
        })
    }

    private fun settingDate(localDateTime: LocalDate) {
        // 오늘 날짜
        localDate = localDateTime
        initDate = localDateTime.dayOfWeek.value
        if (initDate == 7) {
            localDate = localDate.plusDays(1)
            initDate = 1
        }
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.weekGetFoodArea.collect {
                    when(it) {
                        is WeekFoodState.UnInitialized -> {
                            initData()
                            initViewPager()
                            initViews()
                            chekNetwork()
                            checkWeekend()
                        }

                        is WeekFoodState.Loading -> {

                        }

                        is WeekFoodState.SuccessWeekFoodGetData -> {
                            val list = mutableListOf<List<String>>()

                            settingDate(LocalDate.parse(it.getWeekFoodData.localDateTime.substring(0, 10)))

                            it.getWeekFoodData.data.forEach { foodResult ->
                                list.add(foodResult.meals)
                            }

                            val chunkedList = when (list.size) {
                                15 -> {
                                    list.chunked(3).chunked(5).first()
                                }
                                5 -> {
                                    list.chunked(1).chunked(5).first()
                                }
                                else -> {
                                    list.chunked(2).chunked(5).first()
                                }
                            }

                            with(binding) {
                                viewPager2.adapter = MyPagerAdapter(chunkedList)
                                viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                                setCurrentPage(initDate)
                                indicator.setViewPager(viewPager2)
                            }
                        }

                        is WeekFoodState.Error -> {

                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.postReviewData.collectLatest {
                    it?.let{
                        if (it.success) {
                            val anotherDialogView = LayoutInflater.from(context).inflate(R.layout.item_review_dialog, null)
                            val anotherDialog = AlertDialog.Builder(context).setView(anotherDialogView).create().apply {
                                setCancelable(false)
                                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                show()
                            }

                            lifecycleScope.launch {
                                delay(1000L)
                                anotherDialog.dismiss()
                            }
                        } else {
                            Toast.makeText(context, "네트워크 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun initData() {
        if (MyongsikApplication.prefs.getUserCampus() == "S") {
            when (MyongsikApplication.prefs.getUserArea()) {
                "MCC" -> {
                    homeViewModel.weekGetFoodAreaFun("MCC식당")
                    binding.homeTimeTv.text = getString(R.string.home_time_tv)
                }
                "PAUL" -> {
                    homeViewModel.weekGetFoodAreaFun("폴바셋")
                }
            }
        } else if (MyongsikApplication.prefs.getUserCampus() == "Y") {
            when (MyongsikApplication.prefs.getUserArea()) {
                "S" -> {
                    homeViewModel.weekGetFoodAreaFun(CommonUtil.getAreaName(context))
                    binding.homeTimeTv.text = getString(R.string.home_time_staff_tv)
                }
                "L" -> {
                    homeViewModel.weekGetFoodAreaFun(CommonUtil.getAreaName(context))
                    binding.homeTimeTv.text = getString(R.string.home_time_life_tv)
                }
                "H" -> {
                    homeViewModel.weekGetFoodAreaFun(CommonUtil.getAreaName(context))
                    binding.homeTimeTv.text = getString(R.string.home_time_student_tv)
                }
                "M" -> {
                    homeViewModel.weekGetFoodAreaFun(CommonUtil.getAreaName(context))
                    binding.homeTimeTv.text = getString(R.string.home_time_myonog_tv)
                }
            }
        }
    }

    // 색상
    private fun initViewPager() {
        binding.viewPager2.offscreenPageLimit = 5
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position) // 월요일
                val monday = localDate.minusDays(((initDate - 1).toLong()))
                val tues = monday.plus(1, ChronoUnit.DAYS)
                val wed = tues.plus(1, ChronoUnit.DAYS)
                val thurs = wed.plus(1, ChronoUnit.DAYS)
                val fid = thurs.plus(1, ChronoUnit.DAYS)

                when (position) {
                    0 -> {
                        val drawable = context?.let { it1 ->
                            ContextCompat.getDrawable(
                                it1, R.drawable.home_arrow_left
                            )
                        }
                        val color = context?.let { it1 ->
                            ContextCompat.getColor(
                                it1, R.color.home_arrow_gray_color
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
                                it1, R.drawable.home_arrow_right
                            )
                        }
                        val color = context?.let { it1 ->
                            ContextCompat.getColor(
                                it1, R.color.home_arrow_gray_color
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

                binding.homeTodayDateTv.text = when (position) {
                    0 -> getString(R.string.date_format, monday.toString().substring(5, 7), monday.toString().substring(8, 10))
                    1 -> getString(R.string.date_format, tues.toString().substring(5, 7), tues.toString().substring(8, 10))
                    2 -> getString(R.string.date_format, wed.toString().substring(5, 7), wed.toString().substring(8, 10))
                    3 -> getString(R.string.date_format, thurs.toString().substring(5, 7), thurs.toString().substring(8, 10))
                    else -> getString(R.string.date_format, fid.toString().substring(5, 7), fid.toString().substring(8, 10))
                }
            }
        })
    }

    //네트워크 연결 되어있는지 확인
    private fun chekNetwork() {
        if (!NetworkUtils.getNetworkConnected(context)) { //실패했을 경우
            with(binding) {
                todayNotFoodCl.visibility = View.VISIBLE
                viewPager2.visibility = View.INVISIBLE
                todayDayNotFoodTv.visibility = View.INVISIBLE
                todayDayNotNoticeTv.text = getString(R.string.check_newtwork_state)
            }
        }
    }

    private fun initViews() {
        with(binding) {
            homeTodayArrowLeft.setOnClickListener {
                val currentIndex = binding.viewPager2.currentItem
                binding.viewPager2.currentItem = currentIndex - 1
            }

            homeTodayArrowRight.setOnClickListener {
                val currentIndex = binding.viewPager2.currentItem
                binding.viewPager2.currentItem = currentIndex + 1
            }

            bt.setOnClickListener {
                val dialogUtils = DialogUtils(requireContext())
                dialogUtils.showWriteReviewDialog { editText ->
                    val review = editText.text.toString()
                    if (review.isEmpty()) {
                        dialogUtils.showConfirmDialog(getString(R.string.opinion_write), getString(R.string.please_opinion_write)) {}
                    } else { // 리뷰 작성
                        writeMenu(review)
                    }
                }
            }
        }
    }

    private fun writeMenu(review: String) {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)
        val request = RequestReviewData(review, formattedDate, MyongsikApplication.prefs.getUserID(), CommonUtil.getAreaName(context))
        homeViewModel.postReview(request)
    }

    //주말
    private fun checkWeekend() {
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            val dialogUtils = context?.let { DialogUtils(it) }
            dialogUtils?.showConfirmDialog(getString(R.string.weekend_noti), getString(R.string.weekend_not_service), yesClickListener = {})
        }
        with(binding) {
            viewPager2.visibility = View.VISIBLE
            todayNotFoodCl.visibility = View.INVISIBLE
            indicator.visibility = View.VISIBLE
        }
    }

    private fun setCurrentPage(value: Int) {
        binding.viewPager2.setCurrentItem(value - 1, false)
    }

    private fun showBottomSheetDialog() {
        val binding = DialogBottomUpdateSheetBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()

        binding.bottomUpdateNotSeeCl.setOnClickListener {
            MyongsikApplication.prefs.setString(PreferenceKey.UPDATE_KEY, getString(R.string.update_not_see))
            bottomSheetDialog.dismiss()
        }

        binding.bottomBatteryExceptionCl.setOnClickListener {
            CommonUtil.batteryExceptionDialog(context)
            bottomSheetDialog.dismiss()
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.root.parent as View)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetDialog.dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    private fun setInterstitialAd() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() { // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() { // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() { // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() { // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }

    private fun initInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(), BuildConfig.ADMOB_MYONGSIK_INTERSTITIAL_HOME, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
                mInterstitialAd?.show(requireActivity())
            }
        })
    }
}