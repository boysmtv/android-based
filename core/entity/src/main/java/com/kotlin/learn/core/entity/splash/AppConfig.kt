package com.kotlin.learn.core.entity.splash

import com.kotlin.learn.core.utilities.Constant.EMPTY_STRING
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppConfig(
    @Json(name = "parameterKey") val parameterKey: String = EMPTY_STRING,
    @Json(name = "isSecured") val isSecured: Boolean = false,
    @Json(name = "parameterValue") val parameterValue: String = EMPTY_STRING
)
