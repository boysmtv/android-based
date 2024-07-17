package com.kotlin.learn.core.entity.auth

import android.os.Parcelable
import androidx.annotation.Keep
import com.kotlin.learn.core.utilities.Constant.EMPTY_STRING
import com.kotlin.learn.core.utilities.Constant.ZERO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Keep
data class Lov(
    @Json(name = "lovId") val lovId: Int = ZERO,
    @Json(name = "code") val code: String? = null,
    @Json(name = "display") val display: String = EMPTY_STRING,
    @Json(name = "sequence") val sequence: Int = ZERO,
    @Json(name = "type") var type: String? = null
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
@Keep
data class LovResult(
    @Json(name = "lov") val lov: List<Lov>?,
    @Json(name = "lastUpdatedTime") val lastUpdatedTime: String? = null
) : Parcelable