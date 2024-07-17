package com.kotlin.learn.api.splash.data.remote

import com.kotlin.learn.api.splash.data.api.SplashApi
import com.kotlin.learn.core.common.base.BaseDataSource
import com.kotlin.learn.core.entity.splash.ConfigCheckRequest
import javax.inject.Inject

class SplashAppConfigRemoteDataSource @Inject constructor(var api: SplashApi) : BaseDataSource() {

    suspend fun postConfigCheck(param: ConfigCheckRequest) = getResultWithSingleObject {
        api.postCheckConfig(param)
    }

}