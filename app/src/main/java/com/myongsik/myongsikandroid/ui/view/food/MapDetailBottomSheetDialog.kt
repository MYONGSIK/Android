package com.myongsik.myongsikandroid.ui.view.food

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.model.food.RequestScrap
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.databinding.DialogBottomRestaurantSheetBinding
import com.myongsik.myongsikandroid.ui.viewmodel.food.HomeViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.search.LoveViewModel
import com.myongsik.myongsikandroid.util.CommonUtil
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapDetailBottomSheetDialog(private val restaurantId: Int) : BottomSheetDialogFragment() {

    private var _binding: DialogBottomRestaurantSheetBinding? = null
    private val binding: DialogBottomRestaurantSheetBinding
        get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()
    private val loveViewModel by viewModels<LoveViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogBottomRestaurantSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getOneRestaurant(restaurantId)
        bottomSheetDialogAction()
        initView()
    }

    private fun initView() {
        with(binding) {
            var restaurantPhoneNum = ""
            var restaurantScrap: RequestScrap? = null
            var restaurant: Restaurant? = null

            homeViewModel.getDetailRestaurant.observe(viewLifecycleOwner) {
                dialogNameTv.text = it.data.name
                val distance = CommonUtil.distanceMapper(it.data.distance)
                dialogDistanceTv.text = distance
                dialogCategoryTv.text = it.data.category
                dialogAddressTv.text = it.data.address
                restaurantPhoneNum = it.data.contact
                //스크랩
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
                restaurant = Restaurant(
                    address_name = it.data.address,
                    category_group_code = " ",
                    category_group_name = it.data.category,
                    category_name = it.data.category,
                    distance = it.data.distance,
                    id = it.data.code,
                    phone = it.data.contact,
                    place_name = it.data.name,
                    place_url = it.data.urlAddress,
                    road_address_name = it.data.address,
                    x = it.data.latitude.toString(),
                    y = it.data.longitude.toString()
                )
                loveViewModel.loveIs(restaurant!!)
            }

            loveViewModel.loveIs.observe(viewLifecycleOwner) {
                if (it == null) {
                    dialogBottomLoveIv.visibility = View.VISIBLE
                    dialogBottomScrapIv.visibility = View.INVISIBLE
                } else {
                    dialogBottomLoveIv.visibility = View.INVISIBLE
                    dialogBottomScrapIv.visibility = View.VISIBLE
                }
            }

            dialogBottomCallIv.setOnClickListener {
                restaurantClick(restaurantPhoneNum)
            }

            dialogBottomLoveIv.setOnClickListener {
                loveViewModel.scarpRestaurant(restaurantScrap!!)
                loveViewModel.saveFoods(restaurant!!)
                Toast.makeText(requireContext(), "찜 완료!", Toast.LENGTH_SHORT).show()
                dialogBottomScrapIv.visibility = View.VISIBLE
                dialogBottomLoveIv.visibility = View.INVISIBLE
            }

            dialogBottomScrapIv.setOnClickListener {
                loveViewModel.deleteFoods(restaurant!!)
                Toast.makeText(requireContext(), "찜 목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                dialogBottomScrapIv.visibility = View.INVISIBLE
                dialogBottomLoveIv.visibility = View.VISIBLE
            }
        }
    }

    private fun restaurantClick(phoneNum: String) {
        if (context?.getString(R.string.is_null_phone_number) != phoneNum) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$phoneNum"))
            context?.startActivity(intent)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.is_null_phone_number),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun bottomSheetDialogAction() {
        val bottomSheetBehavior = BottomSheetBehavior.from(view?.parent as View)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}