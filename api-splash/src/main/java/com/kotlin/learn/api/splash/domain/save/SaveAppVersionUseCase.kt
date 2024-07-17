package com.kotlin.learn.api.splash.domain.save

import com.kotlin.learn.api.splash.data.repository.SplashRepository
import javax.inject.Inject

class SaveAppVersionUseCase @Inject constructor(
    private val repo: SplashRepository
) {
    operator fun invoke(version : String) = repo.appConfigLocalDataSource.saveAppVersion(version)
}