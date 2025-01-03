plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "com.kitabisa.test.universitydomains"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kitabisa.test.universitydomains"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.kitabisa.test.universitydomains.core.testing.TestRunner"

        buildConfigField("String", "BASE_URL", "\"http://universities.hipolabs.com/\"")
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

    //network
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    // di
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // view model
    implementation(libs.androidx.lifecycle.viewModelCompose)
    // image
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    // navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    // icons extended
    implementation(libs.androidx.compose.material.iconsExtended)
    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // datastore
    implementation(libs.androidx.datastore.preferences)
    // browser
    implementation(libs.androidx.browser)

    // test
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.hilt.android.testing)
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.junit.ktx)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.turbine)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}