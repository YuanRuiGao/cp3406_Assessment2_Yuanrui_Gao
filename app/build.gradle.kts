import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

// 添加 kotlin-kapt 和 kotlin-ksp 插件
plugins {
    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp) // 添加 ksp 插件
}

android {
    namespace = "com.example.assessment2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.assessment2"
        minSdk = 24
        targetSdk = 35
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10" // 确保版本与 Compose 兼容
    }
}

dependencies {
//    val room_version = "2.6.1" // 使用最新稳定版本

    // Room核心库
    implementation(libs.androidx.room.runtime)
    // Kotlin协程支持
    implementation(libs.androidx.room.ktx)
    // 使用 KSP 替代 kapt
    ksp(libs.androidx.room.compiler)

//    implementation(libs.androidx.datastore.preferences) // DataStore 依赖
    implementation ("androidx.datastore:datastore-preferences:1.1.4") // 或更高版本

    //KPI依赖
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
//    implementation(libs.retrofit2)
//    implementation(libs.gson.converter)
    // 其他依赖
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
