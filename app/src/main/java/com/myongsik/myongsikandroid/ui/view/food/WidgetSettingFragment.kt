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

@AndroidEntryPoint class WidgetSettingFragment : BaseFragment<FragmentWidgetSettingBinding>() {

    private val viewModel by viewModels<WidgetSettingViewModel>()

    private val checkColor by lazy {
        resources.getColor(R.color.main_color, null)
    }

    private val notCheckColor by lazy {
        resources.getColor(R.color.gray_color_d9d9d9, null)
    }

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentWidgetSettingBinding {
        return FragmentWidgetSettingBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        initCheckData()
        initObserve()
    }

    private fun initCheckData() {
        viewModel.getCheckData()
    }

    override fun initListener() {
        with(binding) { // 생활관식당
            widgetDormitoryCl.setOnClickListener {
                viewModel.checkDormitory()
            }

            // 명진당
            widgetMyongjinCl.setOnClickListener {
                viewModel.checkMyongjin()
            }

            // 학생회관
            widgetStudentCl.setOnClickListener {
                viewModel.checkStudent()
            }

            // 교직원식당
            widgetTeacherCl.setOnClickListener {
                viewModel.checkTeacher()
            }

            widgetBackBtnIv.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initObserve() {
        initDormitoryObserve()
        initMyongjinObserve()
        initStudentObserve()
        initTeacherObserve()
    }

    private fun initDormitoryObserve() {
        viewModel.dormitoryCheck.observe(viewLifecycleOwner) {
            binding.widgetDormitoryUncheckIv.setColorFilter(getCurrentColor(it))
        }
    }

    private fun initMyongjinObserve() {
        viewModel.myongjinCheck.observe(viewLifecycleOwner) {
            binding.widgetMyongjinUncheckIv.setColorFilter(getCurrentColor(it))
        }
    }

    private fun initStudentObserve() {
        viewModel.studentCheck.observe(viewLifecycleOwner) {
            binding.widgetStudentUncheckIv.setColorFilter(getCurrentColor(it))
        }
    }

    private fun initTeacherObserve() {
        viewModel.teacherCheck.observe(viewLifecycleOwner) {
            binding.widgetTeacherUncheckIv.setColorFilter(getCurrentColor(it))
        }
    }

    private fun getCurrentColor(it: Boolean) = if (it) {
        checkColor
    } else {
        notCheckColor
    }
}