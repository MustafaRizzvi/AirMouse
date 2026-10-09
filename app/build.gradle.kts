plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.airmouse.wear"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.airmouse.wear"
        minSdk = 30 // Wear OS 3+ (required for BluetoothHidDevice + modern sensor batching)
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

// Global compilation intercept to cleanly enforce JVM 17 execution target
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    // Core
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-service:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")

    // Compose (BOM keeps versions aligned)
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Wear Compose — the Wear-specific Material components (Chip, ToggleChip, ScalingLazyColumn, etc.)
    implementation("androidx.wear.compose:compose-material:1.4.0")
    implementation("androidx.wear.compose:compose-foundation:1.4.0")
    implementation("androidx.wear.compose:compose-navigation:1.4.0")

    // Wear tiles / core widgets
    implementation("androidx.wear:wear:1.3.0")

    // Play services for Wear (Bluetooth / capability helpers on some OEM watches)
    implementation("com.google.android.gms:play-services-wearable:18.2.0")

    // Coroutines for sensor stream + gesture recognizer pipelines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
}