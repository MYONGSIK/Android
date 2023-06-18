import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.myongsik.myongsikandroid"
        minSdk = 23
        targetSdk = 33
        versionCode = 26
        versionName = "2.2.11"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(FileInputStream(rootProject.file("local.properties")))

        val admobApplicationId = properties.getProperty("ADMOB_APPLICATION_ID") ?: ""
        val kakaoMapApiKey = properties.getProperty("kakaoMapApiKey") ?: ""

        val manifestPlaceholders = mapOf("admobApplicationId" to admobApplicationId, "kakaoMapApiKey" to kakaoMapApiKey)
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
//        coreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    kapt {
        correctErrorTypes = true
    }
    defaultConfig {
        multiDexEnabled = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Google play
    implementation("com.google.android.gms:play-services-ads-identifier:17.0.0")

    // Google Admob
    implementation("com.google.android.gms:play-services-ads:22.0.0")


    //Moshi
    implementation("com.squareup.moshi:moshi:1.13.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

    // Retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:retrofit-mock:2.9.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // http 로그 확인
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")

    // Recyclerview
    implementation("androidx.recyclerview:recyclerview:1.3.0")

    // Room
    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.room:room-ktx:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")
    implementation("androidx.room:room-paging:2.4.2")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.4.1")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Background
    implementation("androidx.work:work-runtime-ktx:2.7.1")

    // ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // Indicator
    implementation("me.relex:circleindicator:2.1.6")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.41")
    kapt("com.google.dagger:hilt-compiler:2.41")

    // ViewModel delegate
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.fragment:fragment-ktx:1.4.1")

    // Hilt extension
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")

    // Naver map
    implementation("com.naver.maps:map-sdk:3.14.0")

    //disugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    //firebase
    implementation("com.google.firebase:firebase-bom:31.2.3")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    //refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //map
    implementation(files("libs/libDaumMapAndroid.jar"))

    //Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")
}