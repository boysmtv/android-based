@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

apply {
    from("$rootDir/buildConfig/common-config.gradle")
}

android {
    namespace = "com.kotlin.learn.core.common"
    viewBinding.isEnabled = true
}

val customModulePath: groovy.lang.Closure<Any> by ext

dependencies {
    implementation(customModulePath(":core:ui"))
    implementation(customModulePath(":core:model"))
    implementation(customModulePath(":core:utilities"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    implementation(libs.paging.runtime)

    implementation(libs.material)
    implementation(libs.androidx.security.security.crypto)
    implementation(libs.com.squareup.moshi.moshi.kotlin)
    implementation(libs.com.google.android.gms.play.services.auth)
    implementation(libs.androidx.datastore.datastore.preferences)
    implementation(libs.com.google.firebase.firebase.auth)
    implementation(libs.com.google.firebase.firebase.common.ktx)
    implementation(libs.com.google.firebase.firebase.database.ktx)
    implementation(libs.com.google.firebase.firebase.messaging.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)

    implementation(libs.androidx.datastore.datastore.preferences)
    implementation(libs.androidx.room.room.runtime)
    implementation(libs.androidx.room.room.ktx)

    kapt(libs.hilt.compiler)
    kapt(libs.androidx.room.room.compiler)
}
