package com.myongsik.myongsikandroid.ui.view.food

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.databinding.FragmentSplashBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.MyongsikApplication
import com.myongsik.myongsikandroid.util.NetworkUtils

class SplashFragment : Fragment() {

    private var _binding : FragmentSplashBinding?= null
    private val binding : FragmentSplashBinding
        get() = _binding!!

    private lateinit var mainActivity : MainActivity
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (MyongsikApplication.prefs.getString("newUser", "new") == "new") {
            @SuppressLint("HardwareIds")
            val androidId = Settings.Secure.getString(
                requireContext().contentResolver,
                Settings.Secure.ANDROID_ID
            )
            MyongsikApplication.prefs.setUserId(androidId)
            MyongsikApplication.prefs.setString("newUser", "existing")
            val requestUser = RequestUserData(androidId)
            mainViewModel.postUser(requestUser)
        }

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