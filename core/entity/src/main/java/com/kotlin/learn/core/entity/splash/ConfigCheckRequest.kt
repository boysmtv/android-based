package com.kotlin.learn.core.entity.splash

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ConfigCheckRequest(
    @Json(name = "channelId") var channelId: String?
)