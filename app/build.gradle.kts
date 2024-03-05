plugins {
    id("com.google.devtools.ksp")
    id("com.android.application")
    id("kotlin-android")

    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")

    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.devkick.itbookstore"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.devkick.itbookstore"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.compile.sdk.get().toInt()
        versionName = libs.versions.version.name.get()
        versionCode = libs.versions.version.code.get().toInt()

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.activity)

    implementation(libs.bundles.hilt)
    implementation(libs.material)
    kapt(libs.hilt.compiler)

    implementation(libs.timber)

    implementation(projects.feature.bookSearch)
    implementation(projects.feature.bookDetail)
    implementation(projects.feature.navigation)
    implementation(projects.core.ui)
    implementation(projects.resource)
}