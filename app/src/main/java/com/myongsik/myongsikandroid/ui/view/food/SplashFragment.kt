package com.myongsik.myongsikandroid.ui.view.food

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.databinding.FragmentSplashBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.DialogUtils
import com.myongsik.myongsikandroid.util.MyongsikApplication
import com.myongsik.myongsikandroid.util.NetworkUtils
import java.net.ConnectException

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding!!

    private lateinit var mainActivity: MainActivity
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
        val dialogUtils = DialogUtils(requireContext())
        if (!NetworkUtils.getNetworkConnected(requireContext())) {
            dialogUtils.showConfirmDialog("네트워크 에러가 발생하였습니다.", "다시 접속해주세요.",
                yesClickListener = {
                    mainActivity.finish()
                })
        } else {
            if (MyongsikApplication.prefs.getString("newUser", "new") == "new") {
                try {
                    @SuppressLint("HardwareIds")
                    val androidId = Settings.Secure.getString(
                        requireContext().contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                    Log.d("ggplot", "$androidId 신규회원")
                    val requestUser = RequestUserData(androidId)
                    mainViewModel.postUser(requestUser)
                    MyongsikApplication.prefs.setUserId(androidId)
                } catch (e: ConnectException) {
                    dialogUtils.showConfirmDialog("네트워크 에러가 발생하였습니다.", "다시 접속해주세요.",
                        yesClickListener = {
                            mainActivity.finish()
                        })
                }
            }
            val handler = Handler(Looper.getMainLooper())

            if (MyongsikApplication.prefs.getUserCampus() == "") {
                handler.postDelayed({
                    findNavController().navigate(R.id.action_fragment_splash_to_fragment_select)
                }, 1500)
                return
            } else {
                handler.postDelayed({
                    findNavController().navigate(R.id.action_fragment_splash_to_fragment_search)
                }, 1500)
            }
        }
        initErrorObserve()
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

    private fun initErrorObserve() {
        mainViewModel.exceptionLiveData.observe(this) {
            context?.run {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.error))
                    .setMessage(getString(R.string.check_newtwork_state))
                    .create()
                    .show()
            }
        }
    }
}