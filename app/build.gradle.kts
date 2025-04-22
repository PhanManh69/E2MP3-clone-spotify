@file:Suppress("DEPRECATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt.android)
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization.compiler)
}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.emanh.rootapp"
    compileSdk = 35
    compileSdkVersion(35)

    defaultConfig {
        applicationId = "com.emanh.rootapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.test.junit4)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    kapt(libs.hilt.compiler)
    kapt(libs.hilt.androidCompiler)
    ksp(libs.androidx.room.compiler)

    annotationProcessor(libs.glide.compiler)
    annotationProcessor(libs.androidx.room.compiler)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.viewbinding)
    implementation(libs.androidx.compose.liveData)
    implementation(libs.androidx.compose.paging)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.support.v4)
    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.androidx.viewpager)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.splashScreen)
    implementation(libs.androidx.appCompatResource)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.lifecycle.liveData)
    implementation(libs.lifecycle.liveDataViewModel)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.commonJava)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.design.material)
    implementation(libs.hilt.android)
    implementation(libs.glide)
    implementation(libs.glide.customTransform)
    implementation(libs.glide.gpuImage)
    implementation(libs.glide.compose)
    implementation(libs.accompanist.webview)
    implementation(libs.accompanist.placeholder.material)
    implementation(libs.androidx.workManager)
    implementation(libs.androidx.recyclerView)
    implementation(libs.hilt.work)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.dynamicFeatureFragment)
    implementation(libs.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlin.plugin.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.svg)
    implementation(libs.coil.gif)
    implementation(libs.lottie.compose)
    implementation(libs.palette.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.okhttp.bom))

    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    implementation("io.coil-kt:coil-compose:2.7.0")
}