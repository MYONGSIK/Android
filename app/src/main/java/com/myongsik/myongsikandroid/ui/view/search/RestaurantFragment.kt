package com.myongsik.myongsikandroid.ui.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.databinding.FragmentRestaurantBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

//장소 상세화면
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

        //찜 되어있는지, 안되어있는지 판단
        mainViewModel.loveIs(restaurant)

        //그 결과
        mainViewModel.loveIs.observe(viewLifecycleOwner){
            /*
            null 이 나왔다는 것은 현재 관심목록에 없다는 뜻
             */
            if(it == null){
                binding.fabFavorite.visibility = View.VISIBLE
                binding.fabFavoriteLove.visibility = View.INVISIBLE
            }
            else{
                binding.fabFavorite.visibility = View.INVISIBLE
                binding.fabFavoriteLove.visibility = View.VISIBLE
            }
        }

        //관심목록에 저장하기 버튼을 클릭했을 때 (빈 하트)
        binding.fabFavorite.setOnClickListener {
            binding.fabFavorite.visibility = View.INVISIBLE
            binding.fabFavoriteLove.visibility = View.VISIBLE
            mainViewModel.saveFoods(restaurant)
            Snackbar.make(view, "찜 완료!", Snackbar.LENGTH_SHORT).show()
        }

        //관심목록에서 없애기 버튼 클릭을 클릭했을 때 (채워져있는 하트)
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