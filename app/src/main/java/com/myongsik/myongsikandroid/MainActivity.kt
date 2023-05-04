package com.myongsik.myongsikandroid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.myongsik.myongsikandroid.alarm.AlarmBroadCastReceiver
import com.myongsik.myongsikandroid.databinding.ActivityMainBinding
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

//DataStore 싱글톤으로 생성
//private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initInterstitialAd()
        setInterstitialAd()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.myongsik_home_fragment_view) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

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
                } // 다른 항목에 대한 처리 추가 가능
                else -> { // 처리할 로직이 없을 경우에도 true를 반환하여 이벤트를 소비하도록 구현
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

    private fun setInterstitialAd() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() { // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() { // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() { // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() { // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }

    private fun initInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
                mInterstitialAd?.show(this@MainActivity)
            }
        })
    }
}