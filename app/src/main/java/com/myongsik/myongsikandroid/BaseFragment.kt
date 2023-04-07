package com.myongsik.myongsikandroid

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    private var backPressedCallback: OnBackPressedCallback? = null
    protected lateinit var binding: T

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressedCallback?.let {
            requireActivity().onBackPressedDispatcher.addCallback(this, it)
        }
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback?.remove()
    }

    //백버튼 콜백 (필요할 경우 사용)
    fun settingBackPressedCallback(callback: OnBackPressedCallback) {
        this.backPressedCallback = callback

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    //뷰 초기화
    protected abstract fun initView()

    // Observe, 클릭리스너 작업
    protected abstract fun initListener()
}