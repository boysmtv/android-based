package com.kotlin.learn.api.splash.data.local

import com.kotlin.learn.core.entity.auth.Lov
import javax.inject.Inject

class SplashLovConfigLocalDataSource @Inject constructor(
    private val cache: SplashLovConfigCache
) {
    fun getLov() = cache.getLov()

    fun getLovByKey(key : String) = cache.getLovByKey(key)

    fun setLov(list: List<Lov>) = cache.setLov(list)
}