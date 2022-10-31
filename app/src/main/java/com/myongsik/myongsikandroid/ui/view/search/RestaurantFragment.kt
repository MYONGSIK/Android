package com.myongsik.myongsikandroid.ui.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.databinding.FragmentLoveBinding
import com.myongsik.myongsikandroid.databinding.FragmentRestaurantBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.SearchViewModelProviderFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantFragment : Fragment() {

    private var _binding : FragmentRestaurantBinding?= null
    private val binding : FragmentRestaurantBinding
        get() = _binding!!

    //safe Args 로 받은 식당정보
    private val args : RestaurantFragmentArgs by navArgs<RestaurantFragmentArgs>()

    private val mainViewModel by activityViewModels<MainViewModel>()

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

        binding.fabFavorite.setOnClickListener {
            mainViewModel.saveFoods(restaurant)
            Snackbar.make(view, "관심목록에 저장되었습니다.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}