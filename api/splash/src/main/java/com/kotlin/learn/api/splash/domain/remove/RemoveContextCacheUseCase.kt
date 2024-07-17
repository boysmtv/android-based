package com.kotlin.learn.api.splash.domain.remove

import com.kotlin.learn.api.splash.data.repository.SplashRepository
import javax.inject.Inject

class RemoveContextCacheUseCase @Inject constructor(
    private val repo: SplashRepository
) {
    operator fun invoke() = repo.appConfigLocalDataSource.resetConfigCache()
}