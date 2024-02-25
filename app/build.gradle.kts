import java.util.Properties
import java.io.FileInputStream

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.ANDROID_JETBRAINS)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.ANDROID_NAVIGATION_SAFEARGS_KOTLIN)
    id(Plugins.GOOGLE_MAPS_SECRETS)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.DAGGER_HILT_ANDROID_APP)
    id(Plugins.GOOGLE_SERVICES)
    id(Plugins.FIREBASE_CRASHLYTICS)
}

android {
    compileSdk = DefaultConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.myongsik.myongsikandroid"
        minSdk = DefaultConfig.MIN_SDK_VERSION
        targetSdk = DefaultConfig.TARGET_SDK_VERSION
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(FileInputStream(rootProject.file("local.properties")))

        val admobApplicationId = properties.getProperty("ADMOB_APPLICATION_ID") ?: ""
        val kakaoMapApiKey = properties.getProperty("kakaoMapApiKey") ?: ""

        manifestPlaceholders["admobApplicationId"] = admobApplicationId
        manifestPlaceholders["kakaoMapApiKey"] = kakaoMapApiKey
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
    implementation(Dependencies.CORE_TEX)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.LEGACY_SUPPORT)
    testImplementation(Testing.JUNIT)
    androidTestImplementation(Testing.JUNIT_EXT)
    androidTestImplementation(Testing.ESPRESSO_CORE)

    // Google play
    implementation(Dependencies.GOOGLE_PLAY)

    // Google Admob
    implementation(Dependencies.GOOGLE_ADMOB)

    //Moshi
    implementation(Dependencies.MOSHI)
    kapt(Dependencies.MOSHI_KOTLIN_CODEGEN)

    // Retrofit2
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_MOCK)
    implementation(Dependencies.GSON)
    implementation(Dependencies.RETROFIT_GSON)

    // http 로그 확인
    implementation(Dependencies.OKHTTP_LOGGING_INTERCEPTOR)

    // Navigation
    implementation(Dependencies.NAVIGATION_FRAGMENT_KTX)
    implementation(Dependencies.NAVIGATION_UI_KTX)

    // Recyclerview
    implementation(Dependencies.RECYCLERVIEW)

    // Room
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_KTX)
    kapt(Dependencies.ROOM_COMPILER)
    implementation(Dependencies.ROOM_PAGING)

    // Lifecycle
    implementation(Dependencies.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Dependencies.LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.LIFECYCLE_VIEWMODEL_SAVEDSTATE)

    // Coroutine
    implementation(Dependencies.COROUTINES_CORE)
    implementation(Dependencies.COROUTINES_ANDROID)

    // DataStore
    implementation(Dependencies.DATASTORE)

    // Background
    implementation(Dependencies.WORK)

    // ViewPager2
    implementation(Dependencies.VIEWPAGER2)

    // Indicator
    implementation(Dependencies.INDICATOR)

    // Hilt
    implementation(Dependencies.DAGGER_HILT)
    kapt(Dependencies.DAGGER_HILT_COMPILER)

    // ViewModel delegate
    implementation(Dependencies.VIEWMODEL_DELEAGTE)
    implementation(Dependencies.VIEWMODEL_DELEAGTE_FRAGMENT)

    // Hilt extension
    implementation(Dependencies.HILT_EXTENSIONS)
    kapt(Dependencies.HILT_EXTENSIONS_COMPILER)

    // Paging
    implementation(Dependencies.PAGING3)

    //disugaring
    coreLibraryDesugaring(Dependencies.DISUGARING)

    //firebase
    implementation(platform(Dependencies.FIREBASE_BOM))
    implementation(Dependencies.FIREBASE_ANALYTICS)
    implementation(Dependencies.FIREBASE_CRASHLYTICS)

    //refresh
    implementation(Dependencies.REFRESH_LAYOUT)

    //map
    implementation(files(Dependencies.MAP))

    //Glide
    implementation(Dependencies.GLIDE)

    //kakao map
    implementation(Dependencies.KAKAO_MAP_V2)
}