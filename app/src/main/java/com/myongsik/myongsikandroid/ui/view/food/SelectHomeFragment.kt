package com.myongsik.myongsikandroid.ui.view.food

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentSelectHomeBinding
import com.myongsik.myongsikandroid.ui.view.search.LoveFragmentDirections
import com.myongsik.myongsikandroid.util.DialogUtils
import com.myongsik.myongsikandroid.util.MyongsikApplication

class SelectHomeFragment : Fragment() {
    private lateinit var callback: OnBackPressedCallback

    private var _binding : FragmentSelectHomeBinding?= null
    private val binding : FragmentSelectHomeBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //뒤로가기 버튼 클릭 시 검색화면으로
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = SelectHomeFragmentDirections.actionFragmentSelectHomeToFragmentSearch()
                findNavController().navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}