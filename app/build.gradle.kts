plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myapplication01"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication01"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packaging {
        resources {
            excludes += "assets/location_map_gps_locked.png"
            excludes += "assets/location_map_gps_3d.png"
        }
    }

}


dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(files("lib\\AMap3DMap_10.0.700_AMapSearch_9.7.2_AMapLocation_6.4.5_20240508.jar"))
    implementation(files("lib\\Amap_2DMap_V6.0.0_20191106.jar"))
    implementation ("pub.devrel:easypermissions:3.0.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}