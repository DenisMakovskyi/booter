plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)

    id("kotlin-kapt")
}

android {
    namespace = "com.makovskyi.booter"
    compileSdk = 34

    defaultConfig {

        applicationId = "com.makovskyi.booter"

        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)

    //androidx
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.activity.ktx)

    //jetpack
    implementation(libs.jetpack.pref.ktx)
    implementation(libs.jetpack.room.ktx)
    implementation(libs.jetpack.room.runtime)
    ksp(libs.jetpack.room.compiler)
    implementation(libs.jetpack.lifeycle.process)
    implementation(libs.jetpack.lifeycle.runtime.ktx)
    implementation(libs.jetpack.lifeycle.viewmodel.ktx)
    implementation(libs.jetpack.lifeycle.viewmodel.state)
    ksp(libs.jetpack.lifeycle.compiler)

    //DI
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    ksp(libs.android.hilt.compiler)

    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}