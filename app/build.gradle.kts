plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
   // id("androidx.room")
}

android {
    namespace = "com.eshan.filmapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.eshan.filmapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

//    room {
//        schemaDirectory("$projectDir/schemas")
//    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation ("androidx.navigation:navigation-compose:2.7.5")
    implementation(libs.androidx.room.common.jvm)
   // implementation(libs.androidx.room.runtime.android)
    val room_version = "2.7.1"

    implementation("androidx.room:room-runtime:$room_version")
    //ksp("androidx.room:room-compiler:$room_version")
  //  kapt("androidx.room:room-compiler:2.6.1")
    //kapt("androidx.room:room-compiler:2.5.0")
    kapt("androidx.room:room-compiler:$room_version")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}