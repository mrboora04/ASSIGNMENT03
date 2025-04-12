plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "ca.bhavik.assignment3"
    compileSdk = 34

    defaultConfig {
        applicationId = "ca.bhavik.assignment3"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.glide)
    implementation(libs.recyclerview.v132)
    implementation(libs.cardview)
    implementation(libs.lifecycle.viewmodel.v280)
    implementation(libs.lifecycle.livedata.v280)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.constraintlayout.v214)
    implementation(libs.material)
}