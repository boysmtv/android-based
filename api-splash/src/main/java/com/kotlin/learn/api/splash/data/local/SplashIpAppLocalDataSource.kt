package com.kotlin.learn.api.splash.data.local

import javax.inject.Inject

class SplashIpAppLocalDataSource @Inject constructor(
    private val cache: SplashIpAppCache
) {

    fun saveIpCache(ip: String) = cache.set(ip)

}