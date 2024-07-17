package com.kotlin.learn.api.splash.domain.save

import com.kotlin.learn.api.splash.data.repository.SplashRepository
import javax.inject.Inject

class SaveIpAppUseCase @Inject constructor(
    private val repo: SplashRepository
) {
    operator fun invoke(ip : String) = repo.saveIp(ip)
}