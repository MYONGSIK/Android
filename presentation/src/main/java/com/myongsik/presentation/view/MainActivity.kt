package com.myongsik.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.MyongsikApplication
import com.myongsik.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myongsik_home_fragment_view) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        binding.bottomNavigationView.setOnItemReselectedListener{
            false
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_home -> {
                    if (MyongsikApplication.prefs.getUserCampus() == "Y") {
                        navController.navigate(R.id.fragment_select_home)
                        return@setOnItemSelectedListener true
                    } else {
                        navController.navigate(R.id.fragment_home)
                    }
                }

                R.id.fragment_map -> {
                    navController.navigate(R.id.fragment_map)
                    return@setOnItemSelectedListener true
                }

                R.id.fragment_search -> {
                    navController.navigate(R.id.fragment_search)
                    return@setOnItemSelectedListener true
                }

                R.id.fragment_love -> {
                    navController.navigate(R.id.fragment_love)
                    return@setOnItemSelectedListener true
                }
                // 다른 항목에 대한 처리 추가 가능
                else -> {
                    // 처리할 로직이 없을 경우에도 true를 반환하여 이벤트를 소비하도록 구현
                    return@setOnItemSelectedListener true
                }
            } // 선택된 메뉴 항목에 대한 처리가 끝나고 true를 반환하여 이벤트를 소비하도록 구현
            return@setOnItemSelectedListener true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.fragment_home || destination.id == R.id.fragment_search || destination.id == R.id.fragment_love || destination.id == R.id.fragment_select_home || destination.id == R.id.fragment_map) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.myongsikHomeFragmentView.setPadding(0, 0, 0, 0)
            } else {
                binding.bottomNavigationView.visibility = View.GONE
                binding.myongsikHomeFragmentView.setPadding(0, 0, 0, 0)
            }
        }
    }

    override fun onDestroy() {
        Constant.isAdAvailable = true
        super.onDestroy()
    }
}