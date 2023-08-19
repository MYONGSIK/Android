package com.myongsik.myongsikandroid.presentation.view.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.base.BaseFragment
import com.myongsik.myongsikandroid.databinding.FragmentSelectSeoulBinding
import com.myongsik.myongsikandroid.util.MyongsikApplication

class SelectSeoulFragment : BaseFragment<FragmentSelectSeoulBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectSeoulBinding {
        return FragmentSelectSeoulBinding.inflate(inflater, container, false)
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.selectHomeLifeV.setOnClickListener {
            MyongsikApplication.prefs.setUserArea("PAUL")
            findNavController().navigate(R.id.action_fragment_select_seoul_to_fragment_home)
        }
        binding.selectHomeStaffV.setOnClickListener {
            MyongsikApplication.prefs.setUserArea("MCC")
            findNavController().navigate(R.id.action_fragment_select_seoul_to_fragment_home)
        }

        settingBackPressedCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = SelectSeoulFragmentDirections.actionFragmentSelectSeoulToFragmentSearch()
                findNavController().navigate(action)
            }
        })
    }
}