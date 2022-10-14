package com.myongsik.myongsikandroid.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.repository.FoodRepositoryImpl
import com.myongsik.myongsikandroid.databinding.ActivityMainBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupJetpackNavigation()

        val foodRepository = FoodRepositoryImpl()
        val factory = MainViewModelProviderFactory(foodRepository)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }


    private fun setupJetpackNavigation(){
        val host = supportFragmentManager
            .findFragmentById(R.id.myongsik_home_fragment_view) as NavHostFragment ?: return
        navController = host.navController
    }
}