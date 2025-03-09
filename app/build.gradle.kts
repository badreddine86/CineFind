plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.example.cinefind"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cinefind"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
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
    implementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.androidx.work.runtime.ktx)
    //images
    implementation(libs.coil.compose)
    implementation (libs.accompanist.drawablepainter)
    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    //Hilt
    implementation (libs.hilt.android.v120)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation (libs.androidx.hilt.work)
    kapt (libs.hilt.compiler)
    kapt(libs.hilt.android.compiler)
    //env file
    implementation(libs.dotenv.kotlin)
    //viewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.navigation.compose)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}