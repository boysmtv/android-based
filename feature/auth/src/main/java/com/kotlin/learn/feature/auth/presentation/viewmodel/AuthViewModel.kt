package com.kotlin.learn.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.learn.core.common.Result
import com.kotlin.learn.core.domain.AuthUseCase
import com.kotlin.learn.core.model.AuthReqModel
import com.kotlin.learn.core.model.AuthRespModel
import com.kotlin.learn.core.model.BaseResponse
import com.kotlin.learn.core.model.LoginReqModel
import com.kotlin.learn.core.model.LoginRespModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel() {

    private val _login: MutableStateFlow<Result<BaseResponse<LoginRespModel>>> =
        MutableStateFlow(Result.Loading)
    val login = _login.asStateFlow()

    fun postLogin(model: LoginReqModel) {
        useCase.postLogin(model = model)
            .onEach { _login.value = it }
            .launchIn(viewModelScope)
    }

}