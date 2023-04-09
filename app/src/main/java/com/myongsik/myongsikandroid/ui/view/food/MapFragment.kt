package com.myongsik.myongsikandroid.ui.view.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.myongsik.myongsikandroid.BaseFragment
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentMapBinding
import com.myongsik.myongsikandroid.ui.viewmodel.food.HomeViewModel
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>() {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapBinding {
        return FragmentMapBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        homeViewModel.getMapRankRestaurant()
        createMapView()
    }

    override fun initListener() {

    }

    private fun createMapView(){
        val mapView = MapView(requireActivity())
        if (MyongsikApplication.prefs.getUserCampus() == "Y") {
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Constant.YONGIN_CAMPUS_Y, Constant.YONGIN_CAMPUS_X), true)
        } else{
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Constant.SEOUL_CAMPUS_Y, Constant.SEOUL_CAMPUS_X), true)
        }
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)

        homeViewModel.rankRestaurantResponse.observe(viewLifecycleOwner) { response ->
            response.data.content.forEach { item ->
                //현재 이름이 바뀌여있음
                val latitude = item.longitude // 위도
                val longitude = item.latitude // 경도

                val marker = MapPOIItem()
                // 클릭했을 때 해당 가게 이름
                marker.itemName = item.name
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude!!.toDouble(), longitude!!.toDouble())
                // 현재 기본 마커 사용 추 후에 커스텀해서 바꿔야함
                marker.markerType = MapPOIItem.MarkerType.YellowPin
//                marker.customCalloutBalloon = createCustomBalloon(item.scrapCount.toString()) // 맞춤형 말풍선 설정
                // 선택 했을 때 마크
                marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin

                mapView.addPOIItem(marker) // 지도에 마커 추가
            }
        }
    }

    private fun createCustomBalloon(name: String): View {
        val balloon = LayoutInflater.from(requireContext()).inflate(R.layout.custom_balloon, null) // 맞춤형 말풍선 레이아웃 불러오기
        val textView = balloon.findViewById<TextView>(R.id.name) // 레이아웃에서 텍스트뷰 찾기
        textView.text = name // 텍스트뷰에 이름 설정
        return balloon
    }
}