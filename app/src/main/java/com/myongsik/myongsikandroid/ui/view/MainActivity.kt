package com.myongsik.myongsikandroid.ui.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.repository.FoodRepositoryImpl
import com.myongsik.myongsikandroid.databinding.ActivityMainBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModelProviderFactory
import com.myongsik.myongsikandroid.util.Constant.DATASTORE_NAME

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    lateinit var mainViewModel : MainViewModel

    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupJetpackNavigation()

        val foodRepository = FoodRepositoryImpl(dataStore)
        val factory = MainViewModelProviderFactory(foodRepository)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }


    private fun setupJetpackNavigation(){
        val host = supportFragmentManager
            .findFragmentById(R.id.myongsik_home_fragment_view) as NavHostFragment ?: return
        navController = host.navController
    }
}