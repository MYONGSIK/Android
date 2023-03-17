package com.myongsik.myongsikandroid.ui.view.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.data.model.food.RequestScrap
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.databinding.FragmentRestaurantBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.GetAdvertisingIdTask
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.AndroidEntryPoint

//장소 상세화면
@AndroidEntryPoint
class RestaurantFragment : Fragment() {

    private var _binding : FragmentRestaurantBinding?= null
    private val binding : FragmentRestaurantBinding
        get() = _binding!!

    private val args : RestaurantFragmentArgs by navArgs<RestaurantFragmentArgs>()

    private val mainViewModel by activityViewModels<MainViewModel>()

    private lateinit var callback: OnBackPressedCallback

    private var campus : String?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webview.canGoBack()) {
                    binding.webview.goBack()
                }
                else{
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    class CookWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url = request?.url?.toString()

            return url!!.startsWith("tel:")
            return true
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @SuppressLint("HardwareIds")
        val androidId = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)

        if(MyongsikApplication.prefs.getUserCampus() == "S"){
            campus = "SEOUL"
        }else{
            campus = "YONGIN"
        }

        val restaurant = args.restaurant
        binding.webview.apply{
            webViewClient = CookWebViewClient()
            var webSet = binding.webview.settings
            webSet.javaScriptEnabled = true
            loadUrl(restaurant.place_url)
        }

        mainViewModel.loveIs(restaurant)

        mainViewModel.loveIs.observe(viewLifecycleOwner){
            if(it == null){
                binding.fabFavorite.visibility = View.VISIBLE
                binding.fabFavoriteLove.visibility = View.INVISIBLE
            }
            else{
                binding.fabFavorite.visibility = View.INVISIBLE
                binding.fabFavoriteLove.visibility = View.VISIBLE
            }
        }

        binding.fabFavorite.setOnClickListener {
            binding.fabFavorite.visibility = View.INVISIBLE
            binding.fabFavoriteLove.visibility = View.VISIBLE
            mainViewModel.saveFoods(restaurant)
            mainViewModel.scarpRestaurant(requestScrap = RequestScrap(
                address = restaurant.road_address_name,
                campus = campus!!,
                category = restaurant.category_group_name,
                code =restaurant.id,
                contact = restaurant.phone,
                distance = restaurant.distance,
                name = restaurant.place_name,
                phoneId = MyongsikApplication.prefs.getUserID(),
                urlAddress = restaurant.place_url,
            ))
            Snackbar.make(view, "찜 완료!", Snackbar.LENGTH_SHORT).show()
        }

        binding.fabFavoriteLove.setOnClickListener {
            binding.fabFavorite.visibility = View.VISIBLE
            binding.fabFavoriteLove.visibility = View.INVISIBLE
            mainViewModel.deleteFoods(restaurant)
            Snackbar.make(view, "찜 목록에서 삭제되었습니다.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}