package com.myongsik.presentation.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.myongsik.data.model.restaurant.RequestScrap
import com.myongsik.myongsikandroid.base.BaseFragment
import com.myongsik.presentation.databinding.FragmentRestaurantBinding
import com.myongsik.presentation.viewmodel.search.LoveViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//장소 상세화면
@AndroidEntryPoint
class RestaurantFragment : BaseFragment<FragmentRestaurantBinding>() {

    private val args : RestaurantFragmentArgs by navArgs<RestaurantFragmentArgs>()

    private val loveViewModel by viewModels<LoveViewModel>()

    private var campus : String?= null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRestaurantBinding {
        return FragmentRestaurantBinding.inflate(inflater, container, false)
    }

    override fun initView() {
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

        loveViewModel.loveIs(restaurant)

        val contact : String?
        if(restaurant.phone == ""){
            contact = "전화번호가 없습니다."
        } else {
            contact = restaurant.phone
        }

        binding.fabFavorite.setOnClickListener {
            binding.fabFavorite.visibility = View.INVISIBLE
            binding.fabFavoriteLove.visibility = View.VISIBLE
            loveViewModel.saveFoods(restaurant)
            loveViewModel.scarpRestaurant(requestScrap = RequestScrap(
                address = restaurant.road_address_name,
                campus = campus!!,
                category = restaurant.category_group_name,
                code =restaurant.id,
                contact = contact,
                distance = restaurant.distance,
                name = restaurant.place_name,
                phoneId = MyongsikApplication.prefs.getUserID(),
                urlAddress = restaurant.place_url,
                latitude = restaurant.x,
                longitude = restaurant.y
            )
            )
            Snackbar.make(binding.root, "찜 완료!", Snackbar.LENGTH_SHORT).show()
        }

        binding.fabFavoriteLove.setOnClickListener {
            binding.fabFavorite.visibility = View.VISIBLE
            binding.fabFavoriteLove.visibility = View.INVISIBLE
            loveViewModel.deleteFoods(restaurant)
            Snackbar.make(binding.root, "찜 목록에서 삭제되었습니다.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun initListener() {
        favoriteRestaurantGet()
        settingBackPressedCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webview.canGoBack()) {
                    binding.webview.goBack()
                }
                else{
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun favoriteRestaurantGet(){
        viewLifecycleOwner.lifecycleScope.launch{
            loveViewModel.loveIs.collectLatest {
                if(it == null){
                    binding.fabFavorite.visibility = View.VISIBLE
                    binding.fabFavoriteLove.visibility = View.INVISIBLE
                }
                else{
                    binding.fabFavorite.visibility = View.INVISIBLE
                    binding.fabFavoriteLove.visibility = View.VISIBLE
                }
            }
        }
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
}