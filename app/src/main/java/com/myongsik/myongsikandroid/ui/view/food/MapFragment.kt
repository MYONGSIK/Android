package com.myongsik.myongsikandroid.ui.view.food

import android.view.LayoutInflater
import android.view.ViewGroup
import com.myongsik.myongsikandroid.BaseFragment
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.databinding.FragmentMapBinding
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapBinding {
        return FragmentMapBinding.inflate(inflater, container, false)
    }

    override fun initView() {
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
    }
}