package com.myongsik.myongsikandroid.ui.view.food

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.databinding.FragmentSelectBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.DialogUtils
import com.myongsik.myongsikandroid.util.GetAdvertisingIdTask
import com.myongsik.myongsikandroid.util.MyongsikApplication

class SelectFragment : Fragment() {

    private var _binding: FragmentSelectBinding? = null
    private val binding: FragmentSelectBinding
        get() = _binding!!

    private lateinit var mainActivity: MainActivity
    private val mainViewModel by activityViewModels<MainViewModel>()

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
        val dialogUtils = DialogUtils(requireContext())

        // 첫 경고창
        dialogUtils.showConfirmDialog("", "",
            yesClickListener = {
            }, noClickListener = {})

        // 서울
        binding.splashFBt.setOnClickListener {
            dialogUtils.showCampusSettingDialog("인문캠퍼스로\n캠퍼스 설정을 하시겠어요?", 4,
                yesClickListener = {
                    // 회원 등록

                    context?.let { it1 -> GetAdvertisingIdTask(it1).execute() }
                    val deviceId = it.id

                    val requestUser = RequestUserData(deviceId.toString())
                    mainViewModel.postUser(requestUser)
                    mainViewModel.postUserData.observe(viewLifecycleOwner) {

                        MyongsikApplication.prefs.setUserId(deviceId.toString())
                        MyongsikApplication.prefs.setUserCampus("S")
                        if (!getNetworkConnected(requireContext())) {
                            findNavController().navigate(R.id.action_fragment_select_to_fragment_home)
                        } else {
                            findNavController().navigate(R.id.action_fragment_select_to_fragment_search)
                        }

                    }
                },
                noClickListener = {
                })
        }

        // 용인
        binding.splashSBt.setOnClickListener {
            dialogUtils.showCampusSettingDialog("자연캠퍼스로\n캠퍼스 설정을 하시겠어요?", 4,
                yesClickListener = {
                    // 회원 등록

                    context?.let { it1 -> GetAdvertisingIdTask(it1).execute() }
                    val deviceId = it.id

                    val requestUser = RequestUserData(deviceId.toString())
                    mainViewModel.postUser(requestUser)
                    mainViewModel.postUserData.observe(viewLifecycleOwner) {

                        MyongsikApplication.prefs.setUserId(deviceId.toString())
                        MyongsikApplication.prefs.setUserCampus("Y")
                        if (!getNetworkConnected(requireContext())) {
                            findNavController().navigate(R.id.action_fragment_select_to_fragment_home)
                        } else {
                            findNavController().navigate(R.id.action_fragment_select_to_fragment_search)
                        }

                    }
                },
                noClickListener = {
                })
        }


        binding.splashEt.setOnClickListener {
            dialogUtils.showConfirmDialog("캠퍼스 설정",
                "캠퍼스 설정을 통해 접속 시 선택한 캠퍼스 학식 메뉴를 확인할 수 있습니다.", yesClickListener = {},
                noClickListener = {})
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
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}