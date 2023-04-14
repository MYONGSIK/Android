package com.myongsik.myongsikandroid.ui.view.food

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myongsik.myongsikandroid.BaseFragment
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentMapBinding
import com.myongsik.myongsikandroid.ui.viewmodel.food.HomeViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.search.LoveViewModel
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(), MapView.POIItemEventListener {

    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var mapView : MapView
    private lateinit var marker : MapPOIItem

    private var previouslySelectedMarker: MapPOIItem? = null

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
        settingBackPressedCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = MapFragmentDirections.actionFragmentMapToFragmentSearch()
                findNavController().navigate(action)
            }
        })
    }

    private fun createMapView(){
        mapView = MapView(requireActivity())
        if (MyongsikApplication.prefs.getUserCampus() == "Y") {
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Constant.YONGIN_CAMPUS_Y, Constant.YONGIN_CAMPUS_X), true)
        } else{
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Constant.SEOUL_CAMPUS_Y, Constant.SEOUL_CAMPUS_X), true)
        }
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)
        initMapView()
    }

    private fun initMapView(){
        homeViewModel.rankRestaurantResponse.observe(viewLifecycleOwner) { response ->
            response.data.content.forEach { item ->
                //현재 이름이 바뀌여있음
                val latitude = item.longitude // 위도
                val longitude = item.latitude // 경도
                if(latitude != null && longitude != null){
                    marker = MapPOIItem()
                    // 클릭했을 때 해당 가게 이름
                    marker.itemName = item.scrapCount.toString()
                    marker.tag = item.storeId
                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude.toDouble(), longitude.toDouble())
                    // 현재 기본 마커 사용 추 후에 커스텀해서 바꿔야함
                    val customBitmap = createCustomMarkerBitmap(item.scrapCount.toString())
                    marker.customImageBitmap = customBitmap
                    marker.markerType = MapPOIItem.MarkerType.CustomImage
                    marker.setCustomImageAnchor(0.5f, 1.0f)

                    mapView.addPOIItem(marker) // 지도에 마커 추가
                    mapView.setPOIItemEventListener(this)
                }
            }
        }
    }

    private fun createSelectedCustomMarkerBitmap(name: String): Bitmap {
        val customMarkerView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_selected_balloon, null)

        val textView = customMarkerView.findViewById<TextView>(R.id.name)
        textView.text = name

        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        customMarkerView.layout(0, 0, customMarkerView.measuredWidth, customMarkerView.measuredHeight)

        val bitmap = Bitmap.createBitmap(customMarkerView.measuredWidth, customMarkerView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        customMarkerView.draw(canvas)

        return bitmap
    }

    private fun createCustomMarkerBitmap(name: String): Bitmap {
        val customMarkerView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_balloon, null)

        val textView = customMarkerView.findViewById<TextView>(R.id.name)
        textView.text = name

        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        customMarkerView.layout(0, 0, customMarkerView.measuredWidth, customMarkerView.measuredHeight)

        val bitmap = Bitmap.createBitmap(customMarkerView.measuredWidth, customMarkerView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        customMarkerView.draw(canvas)

        return bitmap
    }

    //선택된 마커
    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem) {
        previouslySelectedMarker?.let { prevMarker ->
            p0?.removePOIItem(prevMarker)

            val originalBitmap = createCustomMarkerBitmap(prevMarker.itemName)

            val restoredMarker = MapPOIItem()
            restoredMarker.itemName = prevMarker.itemName
            restoredMarker.tag = prevMarker.tag
            restoredMarker.mapPoint = prevMarker.mapPoint
            restoredMarker.customImageBitmap = originalBitmap
            restoredMarker.markerType = MapPOIItem.MarkerType.CustomImage
            restoredMarker.setCustomImageAnchor(0.5f, 1.0f)

            p0?.addPOIItem(restoredMarker)
        }
        // 기존 마커를 지도에서 제거
        p0?.removePOIItem(p1)
        val selectedBitmap = createSelectedCustomMarkerBitmap(p1.itemName)

        val newMarker = MapPOIItem()
        newMarker.itemName = p1.itemName
        newMarker.tag = p1.tag
        newMarker.mapPoint = p1.mapPoint
        newMarker.customImageBitmap = selectedBitmap
        newMarker.markerType = MapPOIItem.MarkerType.CustomImage
        newMarker.setCustomImageAnchor(0.5f, 1.0f)

        // 새로운 마커를 지도에 추가
        p0?.addPOIItem(newMarker)
        previouslySelectedMarker = newMarker
        p0?.invalidate()

        showBottomSheetDialog(p1.tag)
    }

    private fun showBottomSheetDialog(restaurantId: Int) {
        val mapDetailBottomSheetDialog = MapDetailBottomSheetDialog(restaurantId)
        mapDetailBottomSheetDialog.show(childFragmentManager, "custom_bottom_sheet")
    }

    @Deprecated("Deprecated in Java")
    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {

    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {

    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {

    }
}