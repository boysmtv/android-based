package com.kotlin.learn.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.learn.core.common.Result
import com.kotlin.learn.core.domain.AuthUseCase
import com.kotlin.learn.core.model.AuthReqModel
import com.kotlin.learn.core.model.AuthRespModel
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

    private val _postAuth: MutableStateFlow<Result<AuthRespModel>> = MutableStateFlow(Result.Loading)
    val postAuth = _postAuth.asStateFlow()

}