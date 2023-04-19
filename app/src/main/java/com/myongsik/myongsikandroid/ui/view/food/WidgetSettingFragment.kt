package com.myongsik.myongsikandroid.ui.view.food

import android.view.LayoutInflater
import android.view.ViewGroup
import com.myongsik.myongsikandroid.BaseFragment
import com.myongsik.myongsikandroid.databinding.FragmentWidgetSettingBinding

class WidgetSettingFragment : BaseFragment<FragmentWidgetSettingBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWidgetSettingBinding {
        return FragmentWidgetSettingBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }

    override fun initListener() {

    }
}