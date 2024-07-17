@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt)
}

apply {
    from("$rootDir/buildConfig/common-config.gradle")
}

android {
    namespace = "com.kotlin.learn"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.kotlin.learn"
        minSdk = 24
        compileSdk = 33

        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:common"))
    implementation(project(":core:utilities"))

    implementation(libs.bundles.ktor)
    implementation(libs.bundles.play.services)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.datastore.datastore.preferences)
    implementation(libs.material)
    implementation(libs.paging.runtime)
    implementation(libs.constraintlayout)
    implementation(libs.hilt.android)
    implementation(libs.timber)
    implementation(libs.com.android.support.multidex)
    implementation(libs.com.jakewharton.threetenabp.threetenabp)

    implementation(libs.appcenter.analytics)
    implementation(libs.appcenter.crashes)

    kapt(libs.hilt.compiler)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)
}