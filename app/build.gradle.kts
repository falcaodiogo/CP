plugins {
    id("kotlin-kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android) version "1.9.0"
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "ua.diogo.cp"
    compileSdk = 34

    defaultConfig {
        applicationId = "ua.diogo.cp"
        minSdk = 29
        targetSdk = 34
        minSdk = 29
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
    // firebase
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.runtime.livedata)

    //navigation
    val nav_version = "2.8.5"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.36.0")

    //material
    implementation("androidx.compose.material:material-icons-extended:1.7.6")
    implementation("androidx.compose.material:material:1.7.6")
    implementation("com.google.android.gms:play-services-auth:21.3.0")

    //android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Room Database
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$room_version")

    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$room_version")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$room_version")
    kapt("androidx.room:room-compiler:2.1.0-beta01")
    implementation("co.yml:ycharts:2.1.0")
    implementation("io.coil-kt:coil-compose:2.6.0")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.activity:activity:1.6.0-alpha05")

    //location
    implementation("com.google.android.gms:play-services-location:21.1.0")
    implementation("com.google.accompanist:accompanist-permissions:0.35.0-alpha")

    // https://mvnrepository.com/artifact/com.google.accompanist/accompanist-swiperefresh
    runtimeOnly("com.google.accompanist:accompanist-swiperefresh:0.36.0")

    //gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")

    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")

    // notifications
    implementation("androidx.core:core-ktx:1.13.1")
}