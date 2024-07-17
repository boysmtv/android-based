package com.kotlin.learn.api.splash.domain.load

import com.kotlin.learn.api.splash.data.repository.SplashRepository
import javax.inject.Inject

class LoadAppVersionUseCase @Inject constructor(
    private val repo: SplashRepository
) {
    operator fun invoke() = repo.appConfigLocalDataSource.loadAppVersion()
}