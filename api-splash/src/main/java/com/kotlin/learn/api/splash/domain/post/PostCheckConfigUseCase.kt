package com.kotlin.learn.api.splash.domain.post

import com.kotlin.learn.api.splash.data.repository.SplashRepository
import com.kotlin.learn.core.entity.splash.ConfigCheckRequest
import javax.inject.Inject

class PostCheckConfigUseCase @Inject constructor(
    private val repo: SplashRepository
) {
    operator fun invoke(param: ConfigCheckRequest) = repo.postCheckConfig(param)
}