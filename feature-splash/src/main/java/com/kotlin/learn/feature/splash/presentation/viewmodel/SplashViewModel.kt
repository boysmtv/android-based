package com.kotlin.learn.feature.splash.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.learn.api.splash.domain.load.LoadAppVersionUseCase
import com.kotlin.learn.api.splash.domain.post.PostCheckConfigUseCase
import com.kotlin.learn.api.splash.domain.remove.RemoveContextCacheUseCase
import com.kotlin.learn.api.splash.domain.save.SaveAppVersionUseCase
import com.kotlin.learn.api.splash.domain.save.SaveIpAppUseCase
import com.kotlin.learn.core.common.util.BaseViewModel
import com.kotlin.learn.core.entity.splash.ConfigCheckRequest
import com.kotlin.learn.core.entity.splash.ConfigCheckResponse
import com.kotlin.learn.core.nav.navigation.DeeplinkNavigation
import com.kotlin.learn.core.utilities.AppVersion
import com.kotlin.learn.core.utilities.Constant.CHANNEL_ID
import com.kotlin.learn.core.utilities.ConstantDeeplink.NAVIGATE_TO_AUTHENTICATION
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kotlin.learn.core.common.entity.Result

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val postCheckConfigUseCase: PostCheckConfigUseCase,
    private val saveIPAppUseCase: SaveIpAppUseCase,
    private val removeContextCacheUseCase : RemoveContextCacheUseCase,
    private val saveAppVersionUseCase: SaveAppVersionUseCase,
    private val loadAppVersionUseCase: LoadAppVersionUseCase,
    private val deeplinkNavigation: DeeplinkNavigation
) : BaseViewModel() {

    private val _checkConfig = MutableLiveData<Result<ConfigCheckResponse>>()
    val checkConfig: LiveData<Result<ConfigCheckResponse>> = _checkConfig

    suspend fun postConfigCheck() =
        postCheckConfigUseCase(
            ConfigCheckRequest(CHANNEL_ID)
        ).collect { _checkConfig.postValue(it) }

    fun saveIp(ip: String) = saveIPAppUseCase(ip)

    fun checkAppVersion(){
        if (!loadAppVersionUseCase().equals(AppVersion.VERSION_NAME)){
            removeContextCacheUseCase()
            saveAppVersionUseCase(AppVersion.VERSION_NAME)
        }
    }

    fun navigateToAuthentication(){
        deeplinkNavigation.navigate(
            NAVIGATE_TO_AUTHENTICATION
        )
    }

}