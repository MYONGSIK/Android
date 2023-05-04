package com.myongsik.myongsikandroid.presentation.view.food

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.BaseFragment
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.databinding.FragmentSplashBinding
import com.myongsik.myongsikandroid.presentation.viewmodel.food.SplashViewModel
import com.myongsik.myongsikandroid.util.DialogUtils
import com.myongsik.myongsikandroid.util.MyongsikApplication
import com.myongsik.myongsikandroid.util.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import java.net.ConnectException

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        val dialogUtils = DialogUtils(requireContext())
        if (!NetworkUtils.getNetworkConnected(requireContext())) {
            dialogUtils.showConfirmDialog("네트워크 에러가 발생하였습니다.", "다시 접속해주세요.",
                yesClickListener = {
                    activity?.finish()
                })
        } else {
            if (MyongsikApplication.prefs.getString("newUser", "new") == "new") {
                try {
                    @SuppressLint("HardwareIds")
                    val androidId = Settings.Secure.getString(
                        requireContext().contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                    val requestUser = RequestUserData(androidId)
                    splashViewModel.postUser(requestUser)
                    MyongsikApplication.prefs.setUserId(androidId)
                } catch (e: ConnectException) {
                    dialogUtils.showConfirmDialog("네트워크 에러가 발생하였습니다.", "다시 접속해주세요.",
                        yesClickListener = {
                            activity?.finish()
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
    }

    override fun initListener() {
        initErrorObserve()
    }

    private fun initErrorObserve() {
        splashViewModel.exceptionLiveData.observe(this) {
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