@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt)
}

apply {
    from("$rootDir/buildConfig/common-config.gradle")
}

android {
    namespace = "com.kotlin.learn.feature.movie"
    viewBinding.isEnabled = true
}

val customModulePath: groovy.lang.Closure<Any> by ext

dependencies {
    implementation(customModulePath(":core:common"))
    implementation(customModulePath(":core:model"))
    implementation(customModulePath(":core:utilities"))
    implementation(customModulePath(":core:data"))
    implementation(customModulePath(":core:domain"))
    implementation(customModulePath(":core:nav"))
    implementation(customModulePath(":core:ui"))
    implementation(customModulePath(":feature:common"))

    implementation(libs.androidx.activity.activity.ktx)
    implementation(libs.androidx.activity.fragment.ktx)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)

    implementation(libs.bundles.ktor)
    implementation(libs.bundles.viewmodel)
    implementation(libs.paging.runtime)
    implementation(libs.facebook.shimmer)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.com.github.zhpanvip.bannerviewpager)
    implementation(libs.com.github.bumptech.glide)
    implementation(libs.jp.wasabeef.glide.transformations)
    implementation(libs.jp.wasabeef.blurry)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.datastore.datastore.preferences)
    implementation(libs.com.google.firebase.firebase.auth)
    implementation(libs.com.google.firebase.firebase.common.ktx)
    implementation(libs.com.google.firebase.firebase.database.ktx)
    implementation(libs.com.google.firebase.firebase.messaging.ktx)
    implementation(libs.com.pierfrancescosoffritti.androidyoutubeplayer.core)

    kapt(libs.hilt.compiler)

    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)
}