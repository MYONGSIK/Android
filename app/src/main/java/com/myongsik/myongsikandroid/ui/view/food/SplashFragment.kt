package com.myongsik.myongsikandroid.ui.view.food

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentSplashBinding
import com.myongsik.myongsikandroid.util.MyongsikApplication
import com.myongsik.myongsikandroid.util.NetworkUtils

class SplashFragment : Fragment() {

    private var _binding : FragmentSplashBinding?= null
    private val binding : FragmentSplashBinding
        get() = _binding!!

    private lateinit var mainActivity : MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val handler = Handler(Looper.getMainLooper())

        if(MyongsikApplication.prefs.getUserCampus() ==""){
            handler.postDelayed({
                findNavController().navigate(R.id.action_fragment_splash_to_fragment_select)
            },1500)
            return
        }
        if(!NetworkUtils.getNetworkConnected(context)){
            handler.postDelayed({
                findNavController().navigate(R.id.action_fragment_splash_to_fragment_home)
            },1500)
        }else{
            handler.postDelayed({
                findNavController().navigate(R.id.action_fragment_splash_to_fragment_search)
            },1500)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}