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
    namespace = "com.kotlin.learn.feature.services"
    viewBinding.isEnabled = true
}

val customModulePath: groovy.lang.Closure<Any> by ext

dependencies {
    implementation(customModulePath(":core:common"))
    implementation(customModulePath(":core:model"))
    implementation(customModulePath(":core:utilities"))
    implementation(customModulePath(":core:data"))
    implementation(customModulePath(":core:domain"))
    implementation(customModulePath(":core:network"))

    implementation(libs.bundles.viewmodel)
    implementation(libs.bundles.play.services)
    implementation(libs.bundles.firebase)

    implementation(libs.androidx.activity.activity.ktx)
    implementation(libs.androidx.activity.fragment.ktx)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.android)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.datastore.datastore.preferences)
    implementation(libs.androidx.work.runtime.ktx)

    kapt(libs.hilt.compiler)
}