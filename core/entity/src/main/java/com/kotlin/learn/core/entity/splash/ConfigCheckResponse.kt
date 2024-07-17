/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 */
package com.kotlin.learn.core.entity.splash

import androidx.annotation.Keep
import com.kotlin.learn.core.entity.auth.LanguagePack
import com.kotlin.learn.core.entity.auth.LovResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ConfigCheckResponse(
    @Json(name = "parameterUpdatedNeeded") var parameterUpdatedNeeded: Boolean = false,
    @Json(name = "lovUpdatedNeeded") var lovUpdatedNeeded: Boolean = false,
    @Json(name = "lovData") val lovData: LovResult? = null,
    @Json(name = "parameterData") val parameterData: AppConfigResponse? = null,
    @Json(name = "localization") val localization: LanguagePack? = null
)

