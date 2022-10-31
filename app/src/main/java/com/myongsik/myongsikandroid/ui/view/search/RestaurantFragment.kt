package com.myongsik.myongsikandroid.ui.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.myongsik.myongsikandroid.databinding.FragmentLoveBinding
import com.myongsik.myongsikandroid.databinding.FragmentRestaurantBinding

class RestaurantFragment : Fragment() {

    private var _binding : FragmentRestaurantBinding?= null
    private val binding : FragmentRestaurantBinding
        get() = _binding!!

    private val args : RestaurantFragmentArgs by navArgs<RestaurantFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurant = args.restaurant
        binding.webview.apply{
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(restaurant.place_url)
        }

        binding.restaurantBackBt.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}