plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
//firebase
// /Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "ro.gabrielbadicioiu.progressivemaintenance"
    compileSdk = 34

    defaultConfig {
        applicationId = "ro.gabrielbadicioiu.progressivemaintenance"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //room
    implementation (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)

    // Compose dependencies
    implementation (libs.androidx.lifecycle.viewmodel.compose.v287)
    implementation (libs.androidx.material.icons.extended)
// Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    // Kotlin Extensions and Coroutines support for Room
    implementation (libs.androidx.room.ktx)


    //navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    //koin
    implementation (libs.koin.android)
    implementation (libs.koin.androidx.navigation)
    implementation (libs.koin.androidx.compose)
    testImplementation (libs.koin.test.junit4)
    //viewmodel
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    //icons
    implementation (libs.androidx.material.icons.extended)

    //firebase
    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation(libs.firebase.analytics)
    //auth
    implementation(libs.firebase.auth.ktx)
    //firebase firestore
    implementation(libs.firebase.firestore)






}