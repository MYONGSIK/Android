package com.myongsik.myongsikandroid.presentation.view.food

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.base.BaseFragment
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentSelectBinding
import com.myongsik.myongsikandroid.util.DialogUtils
import com.myongsik.myongsikandroid.util.MyongsikApplication
import com.myongsik.myongsikandroid.util.NetworkUtils

class SelectFragment : BaseFragment<FragmentSelectBinding>(){

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectBinding {
        return FragmentSelectBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initListener() {
        val dialogUtils = DialogUtils(requireContext())

        with(binding) {
            splashFBt.setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        splashFBt.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
                        splashFBt.setTextColor(ContextCompat.getColor(context!!, R.color.sub_color))
                    }
                    MotionEvent.ACTION_UP -> {
                        splashFBt.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.sub_color))
                        splashFBt.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                    }
                }
                false
            }

            splashSBt.setOnTouchListener { _, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        splashSBt.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
                        splashSBt.setTextColor(ContextCompat.getColor(context!!, R.color.sub_color))
                    }
                    MotionEvent.ACTION_UP -> {
                        splashSBt.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.sub_color))
                        splashSBt.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                    }
                }
                false
            }

            // 서울
            splashFBt.setOnClickListener {
                context?.let {
                    splashFBt.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(it, R.color.white))
                    splashFBt.setTextColor(ContextCompat.getColor(it, R.color.sub_color))
                }
                dialogUtils.showAlertDialog("인문캠퍼스로\n캠퍼스 설정을 하시겠어요?", 4,
                    yesClickListener = {
                        MyongsikApplication.prefs.setUserCampus("S")
                        if (!NetworkUtils.getNetworkConnected(context)) {
                            findNavController().navigate(R.id.action_fragment_select_to_fragment_home)
                        } else {
                            findNavController().navigate(R.id.action_fragment_select_to_fragment_search)
                        }

                    },
                    noClickListener = {
                        context?.let {
                            splashFBt.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(it, R.color.sub_color))
                            splashFBt.setTextColor(ContextCompat.getColor(it, R.color.white))
                        }
                    })
            }

            // 용인
            splashSBt.setOnClickListener {
                context?.let {
                    splashSBt.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(it, R.color.white))
                    splashSBt.setTextColor(ContextCompat.getColor(it, R.color.sub_color))
                }

                dialogUtils.showAlertDialog("자연캠퍼스로\n캠퍼스 설정을 하시겠어요?", 4,
                    yesClickListener = {
                        MyongsikApplication.prefs.setUserCampus("Y")
                        if (!NetworkUtils.getNetworkConnected(context)) {
                            findNavController().navigate(R.id.action_fragment_select_to_fragment_home)
                        } else {
                            findNavController().navigate(R.id.action_fragment_select_to_fragment_search)
                        }
                    },
                    noClickListener = {
                        context?.let {
                            splashSBt.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(it, R.color.sub_color))
                            splashSBt.setTextColor(ContextCompat.getColor(it, R.color.white))
                        }
                    })
            }

            splashEt.setOnClickListener {
                dialogUtils.showConfirmDialog("캠퍼스 설정",
                    "캠퍼스 설정을 통해 접속 시 선택한 캠퍼스 학식 메뉴를 확인할 수 있습니다.", yesClickListener = {})
            }
        }

        // 첫 경고창
        dialogUtils.showConfirmDialog("", "",
            yesClickListener = {
            })
    }
}