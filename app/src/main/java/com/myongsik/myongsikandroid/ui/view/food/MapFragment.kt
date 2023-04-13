package com.myongsik.myongsikandroid.ui.view.food

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.myongsik.myongsikandroid.BaseFragment
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.food.RequestScrap
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
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
    private val loveViewModel by viewModels<LoveViewModel>()

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
                marker.tag = item.storeId
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude!!.toDouble(), longitude!!.toDouble())
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
        homeViewModel.getOneRestaurant(p1.tag)
        showBottomSheetDialog()
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_bottom_restaurant_sheet, null)
        val bottomSheetDialog = BottomSheetDialog(requireActivity())
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()

        val restaurantName = view.findViewById<TextView>(R.id.dialog_name_tv)
        val restaurantScrapCount = view.findViewById<TextView>(R.id.dialog_scrap_count_number_tv)
        val restaurantCategory = view.findViewById<TextView>(R.id.dialog_category_tv)
        val restaurantAddress = view.findViewById<TextView>(R.id.dialog_address_tv)
        val restaurantCallIv = view.findViewById<ImageView>(R.id.tvModifyChangeQuestion)
        val restaurantScrapIv = view.findViewById<ImageView>(R.id.tvDismissDialog)
        var restaurantPhoneNum = ""
        var restaurantId = 0

        var restaurantScrap : RequestScrap? = null
        var restaurant : Restaurant? = null
        homeViewModel.getDetailRestaurant.observe(viewLifecycleOwner){
            restaurantId = it.data.storeId
            restaurantName.text = it.data.name
            restaurantScrapCount.text = it.data.scrapCount.toString()
            restaurantCategory.text = it.data.category
            restaurantAddress.text = it.data.address
            restaurantPhoneNum = it.data.contact
            restaurantScrap = RequestScrap(
                address = it.data.address,
                campus = MyongsikApplication.prefs.getUserCampus(),
                category = it.data.category,
                code = it.data.code,
                contact = it.data.contact,
                distance = it.data.distance,
                name = it.data.name,
                phoneId = MyongsikApplication.prefs.getUserID(),
                urlAddress = it.data.urlAddress,
                latitude = it.data.latitude.toString(),
                longitude = it.data.longitude.toString()
            )
        }

        restaurantCallIv.setOnClickListener {
            restaurantClick(restaurantPhoneNum)
        }

        restaurantScrapIv.setOnClickListener {
            loveViewModel.scarpRestaurant(restaurantScrap!!)
            Snackbar.make(binding.root, "찜 완료!", Snackbar.LENGTH_SHORT).show()
            homeViewModel.getOneRestaurant(restaurantId)
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetDialog.dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    private fun restaurantClick(phoneNum : String) {
        if (context?.getString(R.string.is_null_phone_number) != phoneNum) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$phoneNum"))
            context?.startActivity(intent)
        } else {
            Toast.makeText(requireContext(), getString(R.string.is_null_phone_number), Toast.LENGTH_SHORT).show()
        }
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