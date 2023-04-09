package com.myongsik.myongsikandroid.ui.view.food

import android.view.LayoutInflater
import android.view.ViewGroup
import com.myongsik.myongsikandroid.BaseFragment
import com.myongsik.myongsikandroid.databinding.FragmentMapBinding
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
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.5803504797164, 126.923460283882), true)
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)
    }
}