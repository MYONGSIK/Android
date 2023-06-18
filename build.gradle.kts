// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version Versions.AGP apply false
    id(Plugins.ANDROID_LIBRARY) version Versions.AGP apply false
    id(Plugins.ANDROID_JETBRAINS) version Versions.KOTLIN apply false
    //secrets Plugin
    id(Plugins.GOOGLE_MAPS_SECRETS) version Versions.SECRETS_GRADLE_GRADLE apply false
    id(Plugins.ANDROID_NAVIGATION_SAFEARGS_KOTLIN) version Versions.NAVIGATION_SAFE_ARGS_KOTLIN apply false
    id(Plugins.DAGGER_HILT_ANDROID) version Versions.DAGGER_HILT_ANDROID apply false
    id(Plugins.GOOGLE_SERVICES) version Versions.GOOGLE_SERVICES apply false
    id(Plugins.FIREBASE_CRASHLYTICS) version Versions.FIREBASE_CRASHLYTICS apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}