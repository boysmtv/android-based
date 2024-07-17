package com.kotlin.learn.core.entity.splash

import com.kotlin.learn.core.utilities.Constant.EMPTY_STRING
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppConfigResponse(
    @Json(name = "parameters") val parameters: List<AppConfig> = listOf(),
    @Json(name = "lastUpdatedTime") val lastUpdatedTime: String = EMPTY_STRING
)