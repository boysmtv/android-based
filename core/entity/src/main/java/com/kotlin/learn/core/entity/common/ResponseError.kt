package com.kotlin.learn.core.entity.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseError(
    @Json(name = "code") var code: String?,
    @Json(name = "message") var message: String?
)
