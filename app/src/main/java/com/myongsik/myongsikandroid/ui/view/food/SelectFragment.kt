package com.myongsik.myongsikandroid.ui.view.food

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentSelectBinding
import com.myongsik.myongsikandroid.util.DialogUtils
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
        val dialogUtils = DialogUtils(requireContext())
        binding.splashFBt.setOnClickListener {
            dialogUtils.showCampusSettingDialog("인문캠퍼스로\n캠퍼스 설정을 하시겠어요?", 4,
                yesClickListener = {
                    MyongsikApplication.prefs.setUserCampus("S")
                    if (!getNetworkConnected(requireContext())) {
                        findNavController().navigate(R.id.action_fragment_select_to_fragment_home)
                    } else {
                        findNavController().navigate(R.id.action_fragment_select_to_fragment_search)
                    }
                },
                noClickListener = {
                })
        }


        binding.splashSBt.setOnClickListener {
            dialogUtils.showCampusSettingDialog("자연캠퍼스로\n캠퍼스 설정을 하시겠어요?", 4,
                yesClickListener = {
                    MyongsikApplication.prefs.setUserCampus("Y")
                    if (!getNetworkConnected(requireContext())) {
                        findNavController().navigate(R.id.action_fragment_select_to_fragment_home)
                    } else {
                        findNavController().navigate(R.id.action_fragment_select_to_fragment_search)
                    }
                },
                noClickListener = {
                })
        }


        binding.splashEt.setOnClickListener {
            if(binding.splashHelpIv.visibility == View.VISIBLE)
                binding.splashHelpIv.visibility = View.INVISIBLE
            else
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