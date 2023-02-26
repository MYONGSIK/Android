package com.myongsik.myongsikandroid.ui.view.food

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentSelectBinding
import com.myongsik.myongsikandroid.util.MyongsikApplication

class SelectFragment : Fragment() {

    private var _binding : FragmentSelectBinding?= null
    private val binding : FragmentSelectBinding
        get() = _binding!!

    private lateinit var mainActivity : MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.splashFBt.setOnClickListener {
            MyongsikApplication.prefs.setUserCampus("S")
            if(!getNetworkConnected(mainActivity.applicationContext)){
                findNavController().navigate(R.id.action_fragment_select_to_fragment_home)
            }else{
                findNavController().navigate(R.id.action_fragment_select_to_fragment_search)
            }
        }
        binding.splashSBt.setOnClickListener {
            MyongsikApplication.prefs.setUserCampus("Y")
            if(!getNetworkConnected(mainActivity.applicationContext)){
                findNavController().navigate(R.id.action_fragment_select_to_fragment_home)
            }else{
                findNavController().navigate(R.id.action_fragment_select_to_fragment_search)
            }
        }


        binding.splashEt.setOnClickListener {
            binding.splashHelpIv.visibility = View.VISIBLE
        }
    }

    //Context 를 불러오기 위해
    override fun onAttach(context: Context) {

        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    //네트워크 상태 확인
    private fun getNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}