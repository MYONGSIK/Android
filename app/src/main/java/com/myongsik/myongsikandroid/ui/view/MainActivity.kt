package com.myongsik.myongsikandroid.ui.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.alarm.AlarmBroadCastReceiver
import com.myongsik.myongsikandroid.databinding.ActivityMainBinding
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

//DataStore 싱글톤으로 생성
//private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController
    lateinit var mainViewModel : MainViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //네비게이션들을 담는 호스트
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myongsik_home_fragment_view) as NavHostFragment

        //네비게이션 컨트롤러
        val navController = navHostFragment.navController

        //바텀 네비게이션 뷰와 네비게이션을 묶어준다.
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        //바텀 네비게이션 출력하는 부분과 그렇지 않은 부분을 나눔
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.fragment_home || destination.id == R.id.fragment_love || destination.id == R.id.fragment_search
                || destination.id == R.id.fragment_week_foods) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.myongsikHomeFragmentView.setPadding(0,0,0,0)}
            else {
                binding.bottomNavigationView.visibility= View.GONE
                binding.myongsikHomeFragmentView.setPadding(0,0,0,0)
            }
        }

//        val foodRepository = FoodRepositoryImpl(dataStore)
//        val factory = MainViewModelProviderFactory(foodRepository)
//        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        // Background
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

}