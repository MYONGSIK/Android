pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven {
            url = uri("https://naver.jfrog.io/artifactory/maven/")
            maven { url = uri("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/") }
        }
    }
}
rootProject.name = "myongsik_android"
include(":app")
