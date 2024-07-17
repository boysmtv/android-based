package com.kotlin.learn.api.splash.data.repository

import SplashLanguagePackCache
import com.kotlin.learn.api.splash.data.local.SplashAppConfigLocalDataSource
import com.kotlin.learn.api.splash.data.local.SplashAppConfigResource
import com.kotlin.learn.api.splash.data.remote.SplashAppConfigRemoteDataSource
import com.kotlin.learn.core.common.data.resultFlow
import com.kotlin.learn.core.entity.splash.ConfigCheckRequest
import com.kotlin.learn.core.utilities.Constant.ZERO
import com.kotlin.learn.api.splash.data.local.SplashIpAppLocalDataSource
import com.kotlin.learn.api.splash.data.local.SplashLovConfigLocalDataSource
import javax.inject.Inject

class SplashRepository @Inject constructor(
    internal val appConfigLocalDataSource: SplashAppConfigLocalDataSource,
    internal val lovConfigLocalDataSource: SplashLovConfigLocalDataSource,
    private val appConfigRemoteDataSource: SplashAppConfigRemoteDataSource,
    private val splashLanguagePackCache: SplashLanguagePackCache,
    private val appConfigResource: SplashAppConfigResource,
    private val ipAppLocalDataSource: SplashIpAppLocalDataSource
) {
    fun postCheckConfig(param: ConfigCheckRequest) = resultFlow(
        networkCall = { appConfigRemoteDataSource.postConfigCheck(param) },
        saveCallResult = {
            it.lovData?.let { lovResult ->
                lovResult.lov?.let { lov -> lovConfigLocalDataSource.setLov(lov) }
            }
            it.parameterData?.let { config -> appConfigLocalDataSource.saveAppConfigCache(config.parameters) }
            it.localization?.let { locKey ->
                splashLanguagePackCache.set(locKey)
            }
        }
    )

    fun loadAppConfig(key: String) = appConfigLocalDataSource.loadAppConfigCache(key = key)

    fun loadAllAppConfig() = appConfigLocalDataSource.loadAllAppConfigCache().let {
        if (it.size == ZERO) appConfigResource.get() else it
    }?.also { appConfigLocalDataSource.saveAppConfigCache(it) }

    fun saveIp(ip: String) = ipAppLocalDataSource.saveIpCache(ip)
}