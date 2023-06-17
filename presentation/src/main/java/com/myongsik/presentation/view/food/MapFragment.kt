package com.myongsik.presentation.view.food

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myongsik.presentation.viewmodel.food.HomeViewModel
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.MyongsikApplication
import com.myongsik.presentation.R
import com.myongsik.presentation.base.BaseFragment
import com.myongsik.presentation.databinding.FragmentMapBinding
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
        rankResponseObserve()
    }

    private fun rankResponseObserve() {
        homeViewModel.rankRestaurantResponse.observe(viewLifecycleOwner) { response ->
            response.data.content.forEach { item ->
                val latitude = item.longitude
                val longitude = item.latitude
                if(latitude != null && longitude != null){
                    val marker = MapPOIItem().apply {
                        itemName = item.scrapCount.toString()
                        tag = item.storeId
                        mapPoint = MapPoint.mapPointWithGeoCoord(latitude.toDouble(), longitude.toDouble())
                        val customBitmap = createCustomMarkerBitmap(item.scrapCount.toString())
                        customImageBitmap = customBitmap
                        markerType = MapPOIItem.MarkerType.CustomImage
                        setCustomImageAnchor(0.5f, 1.0f)
                    }

                    mapView.apply {
                        addPOIItem(marker)
                        setPOIItemEventListener(this@MapFragment)
                    }
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

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem) {
        if (previouslySelectedMarker != null && previouslySelectedMarker?.tag == p1.tag) {
            // 동일한 마커가 다시 클릭되었을 때
            p0?.deselectPOIItem(p1)
            previouslySelectedMarker = null
            return
        } else {
            previouslySelectedMarker?.let { prevMarker ->
                p0?.removePOIItem(prevMarker)

                val originalBitmap = createCustomMarkerBitmap(prevMarker.itemName)

                val restoredMarker = MapPOIItem().apply {
                    itemName = prevMarker.itemName
                    tag = prevMarker.tag
                    mapPoint = prevMarker.mapPoint
                    customImageBitmap = originalBitmap
                    markerType = MapPOIItem.MarkerType.CustomImage
                    setCustomImageAnchor(0.5f, 1.0f)
                }

                p0?.addPOIItem(restoredMarker)
            }
            // 기존 마커를 지도에서 제거
            p0?.removePOIItem(p1)
            val selectedBitmap = createSelectedCustomMarkerBitmap(p1.itemName)

            val newMarker = MapPOIItem().apply {
                itemName = p1.itemName
                tag = p1.tag
                mapPoint = p1.mapPoint
                customImageBitmap = selectedBitmap
                markerType = MapPOIItem.MarkerType.CustomImage
                setCustomImageAnchor(0.5f, 1.0f)
            }

            p0?.addPOIItem(newMarker)
            previouslySelectedMarker = newMarker
            p0?.invalidate()

            showBottomSheetDialog(p1.tag)
        }
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