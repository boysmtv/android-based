package com.kotlin.learn.api.splash.data.local

import android.content.Context
import com.kotlin.learn.api.splash.R
import com.kotlin.learn.core.entity.splash.AppConfig
import com.kotlin.learn.core.entity.splash.AppConfigResponse
import com.squareup.moshi.JsonAdapter
import javax.inject.Inject

class SplashAppConfigResource @Inject constructor(
    private val context: Context,
    private val adapter: JsonAdapter<AppConfigResponse>
) {

    fun get(): List<AppConfig>? {
        val inputStream = context.resources.openRawResource(R.raw.app_config)
        val reader = inputStream.bufferedReader().use { it.readText() }

        return adapter.fromJson(reader)?.parameters
    }

}