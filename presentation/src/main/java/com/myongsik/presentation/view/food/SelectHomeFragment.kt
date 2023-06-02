package com.myongsik.presentation.view.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.base.BaseFragment
import com.myongsik.myongsikandroid.util.MyongsikApplication
import com.myongsik.presentation.databinding.FragmentSelectHomeBinding

class SelectHomeFragment : BaseFragment<FragmentSelectHomeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectHomeBinding {
        return FragmentSelectHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.selectHomeHakV.setOnClickListener {
            MyongsikApplication.prefs.setUserArea("H")
            findNavController().navigate(R.id.action_fragment_select_home_to_fragment_home)
        }
        binding.selectHomeLifeV.setOnClickListener {
            MyongsikApplication.prefs.setUserArea("L")
            findNavController().navigate(R.id.action_fragment_select_home_to_fragment_home)
        }
        binding.selectHomeStaffV.setOnClickListener {
            MyongsikApplication.prefs.setUserArea("S")
            findNavController().navigate(R.id.action_fragment_select_home_to_fragment_home)

        }
        binding.selectHomeMyongV.setOnClickListener {
            MyongsikApplication.prefs.setUserArea("M")
            findNavController().navigate(R.id.action_fragment_select_home_to_fragment_home)
        }
        binding.selectHomeSettingIv.setOnClickListener {
            val action = SelectHomeFragmentDirections.actionFragmentSelectHomeToWidgetSettingFragment()
            findNavController().navigate(action)
        }

        settingBackPressedCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = SelectHomeFragmentDirections.actionFragmentSelectHomeToFragmentSearch()
                findNavController().navigate(action)
            }
        })
    }
}