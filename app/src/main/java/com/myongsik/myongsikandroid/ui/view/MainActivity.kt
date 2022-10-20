package com.myongsik.myongsikandroid.ui.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.alarm.AlarmBroadCastReceiver
import com.myongsik.myongsikandroid.data.repository.FoodRepositoryImpl
import com.myongsik.myongsikandroid.databinding.ActivityMainBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModelProviderFactory
import com.myongsik.myongsikandroid.util.Constant.DATASTORE_NAME
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
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

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val triggerTime = Calendar.getInstance()
        triggerTime.set(Calendar.HOUR_OF_DAY, 23)
        triggerTime.set(Calendar.MINUTE, 59)
        triggerTime.set(Calendar.SECOND, 0)
        triggerTime.set(Calendar.MILLISECOND, 0)

        val intent = Intent(this@MainActivity, AlarmBroadCastReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(this@MainActivity, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime.timeInMillis, AlarmManager.INTERVAL_DAY, pIntent)
    }


    private fun setupJetpackNavigation(){
        val host = supportFragmentManager
            .findFragmentById(R.id.myongsik_home_fragment_view) as NavHostFragment ?: return
        navController = host.navController
    }
}