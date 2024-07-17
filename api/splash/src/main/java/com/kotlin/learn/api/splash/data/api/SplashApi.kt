package com.kotlin.learn.api.splash.data.api

import com.kotlin.learn.api.splash.util.SplashUrlConstant.URL_POST_CHECK_CONFIG
import com.kotlin.learn.core.entity.splash.ConfigCheckRequest
import com.kotlin.learn.core.entity.splash.ConfigCheckResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SplashApi {

    @POST(URL_POST_CHECK_CONFIG)
    suspend fun postCheckConfig(
        @Body request: ConfigCheckRequest
    ): Response<ConfigCheckResponse>

}