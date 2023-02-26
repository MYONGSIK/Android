package com.myongsik.myongsikandroid.ui.view.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentSelectHomeBinding
import com.myongsik.myongsikandroid.util.MyongsikApplication

class SelectHomeFragment : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectHomeHakIv.setOnClickListener {
            MyongsikApplication.prefs.setUserArea("H")
            findNavController().navigate(R.id.action_fragment_select_home_to_fragment_home)
        }
        binding.selectHomeLifeIv.setOnClickListener {
            MyongsikApplication.prefs.setUserArea("L")
            findNavController().navigate(R.id.action_fragment_select_home_to_fragment_home)
        }
        binding.selectHomeStaffIv.setOnClickListener {
            MyongsikApplication.prefs.setUserArea("S")
            findNavController().navigate(R.id.action_fragment_select_home_to_fragment_home)

        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}