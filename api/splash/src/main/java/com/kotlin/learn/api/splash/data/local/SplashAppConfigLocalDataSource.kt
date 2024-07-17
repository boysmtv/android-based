package com.kotlin.learn.api.splash.data.local

import com.kotlin.learn.core.entity.splash.AppConfig
import javax.inject.Inject

class SplashAppConfigLocalDataSource @Inject constructor(
    private val cache: SplashAppConfigCache
) {
    fun loadAppConfigCache(key: String) = cache.get(key)

    fun loadAllAppConfigCache() = cache.getAll()

    fun loadAppVersion() = cache.loadAppVersion()

    fun resetConfigCache() = cache.resetConfigCache()

    fun saveAppConfigCache(appConfig: List<AppConfig>) =
        cache.set(appConfig)

    fun saveAppVersion(version: String) = cache.saveAppVersion(version)
}