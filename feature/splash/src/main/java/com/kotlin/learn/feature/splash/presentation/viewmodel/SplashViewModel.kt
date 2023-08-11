package com.kotlin.learn.feature.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.learn.core.common.util.DataStoreCacheEvent
import com.kotlin.learn.core.data.repository.DataStorePreferences
import com.kotlin.learn.core.utilities.PreferenceConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStorePreferences: DataStorePreferences
) : ViewModel() {

    fun fetchDataAuth() =
        flow {
            emit(
                DataStoreCacheEvent.FetchSuccess(
                    dataStorePreferences.getString(PreferenceConstants.Authorization.PREF_USER)
                        .getOrNull()
                        .orEmpty()
                )
            )
        }

    fun clearPreferences() {
        viewModelScope.launch {
            dataStorePreferences.clearPreferences()
        }
    }

}