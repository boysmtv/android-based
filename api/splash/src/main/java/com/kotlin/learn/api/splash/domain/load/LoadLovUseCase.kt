package com.kotlin.learn.api.splash.domain.load

import com.kotlin.learn.api.splash.data.repository.SplashRepository
import javax.inject.Inject

class LoadLovUseCase @Inject constructor(
    private val repo: SplashRepository
) {
    operator fun invoke(key: String) =
        repo.lovConfigLocalDat aSource.getLovByKey(key)?.sortedBy { it.sequence }
}