@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.parcelize)
}

apply {
    from("$rootDir/buildConfig/common-config.gradle")
}

android {
    namespace = "com.kotlin.learn.core.entity"
}

val customModulePath: groovy.lang.Closure<Any> by ext

dependencies {
    /* Project Implementation */
    implementation(customModulePath(":core:utilities"))

    /* Libraries */
    implementation(libs.bundles.common)
    implementation(libs.bundles.core)
    implementation(libs.bundles.network)
    implementation(libs.bundles.dagger)

    /* Kapt Implementation*/
    kapt(libs.bundles.dagger.kapt)
}