package com.myongsik.myongsikandroid.ui.view.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.BaseFragment
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentWidgetSettingBinding
import com.myongsik.myongsikandroid.ui.viewmodel.food.WidgetSettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WidgetSettingFragment : BaseFragment<FragmentWidgetSettingBinding>() {

    private val viewModel by viewModels<WidgetSettingViewModel>()

    private val checkColor by lazy {
        resources.getColor(R.color.main_color, null)
    }

    private val notCheckColor by lazy {
        resources.getColor(R.color.gray_color_d9d9d9, null)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWidgetSettingBinding {
        return FragmentWidgetSettingBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        if(true){
            /*
                데이터스토어값이 없을 경우, 즉 처음 위젯을 설정할 경우 디폴트 값으로 생활관 식당이 체크 되어 있다.
                if 문 안에 true값 대신 데이터스토어값에 빈 값이 들어있을 경우로 하시면 될 것 같습니다.
             */
            viewModel._dormitoryCheck.value = true
        }
    }

    override fun initListener() {
        with(binding) {
            // 생활관식당
            widgetDormitoryCl.setOnClickListener {
                viewModel._dormitoryCheck.value = true
                viewModel._myongjinCheck.value = false
                viewModel._studentCheck.value = false
                viewModel._teacherCheck.value = false
            }

            // 명진당
            widgetMyongjinCl.setOnClickListener {
                viewModel._myongjinCheck.value = true
                viewModel._dormitoryCheck.value = false
                viewModel._studentCheck.value = false
                viewModel._teacherCheck.value = false
            }

            // 학생회관
            widgetStudentCl.setOnClickListener {
                viewModel._studentCheck.value = true
                viewModel._dormitoryCheck.value = false
                viewModel._myongjinCheck.value = false
                viewModel._teacherCheck.value = false
            }

            // 교직원식당
            widgetTeacherCl.setOnClickListener {
                viewModel._teacherCheck.value = true
                viewModel._dormitoryCheck.value = false
                viewModel._myongjinCheck.value = false
                viewModel._studentCheck.value = false
            }

            viewModel.dormitoryCheck.observe(viewLifecycleOwner) {
                if(it) {
                    widgetDormitoryUncheckIv.setColorFilter(checkColor)
                } else {
                    widgetDormitoryUncheckIv.setColorFilter(notCheckColor)
                }
            }
            viewModel.myongjinCheck.observe(viewLifecycleOwner) {
                if(it) {
                    widgetMyongjinUncheckIv.setColorFilter(checkColor)
                } else {
                    widgetMyongjinUncheckIv.setColorFilter(notCheckColor)
                }
            }
            viewModel.studentCheck.observe(viewLifecycleOwner) {
                if(it) {
                    widgetStudentUncheckIv.setColorFilter(checkColor)
                } else {
                    widgetStudentUncheckIv.setColorFilter(notCheckColor)
                }
            }
            viewModel.teacherCheck.observe(viewLifecycleOwner) {
                if(it) {
                    widgetTeacherUncheckIv.setColorFilter(checkColor)
                } else {
                    widgetTeacherUncheckIv.setColorFilter(notCheckColor)
                }
            }

            widgetBackBtnIv.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}