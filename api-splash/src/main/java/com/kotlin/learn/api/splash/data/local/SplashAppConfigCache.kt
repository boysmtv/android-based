package com.kotlin.learn.api.splash.data.local

import CorePlainPrefManager
import com.kotlin.learn.core.common.util.orDefault
import com.kotlin.learn.core.entity.splash.AppConfig
import com.kotlin.learn.core.utilities.PreferenceConstants.AppConfig.PREF_KEY_HSM_PIN_EXPONENT
import com.kotlin.learn.core.utilities.PreferenceConstants.AppConfig.PREF_KEY_HSM_PIN_PUBLIC_KEY
import com.kotlin.learn.core.utilities.PreferenceConstants.AppConfig.PREF_KEY_RSA_KEY
import com.kotlin.learn.core.utilities.PreferenceConstants.Config.PREF_KEY_APP_VERSION
import com.kotlin.learn.core.utilities.PreferenceConstants.Splash.PREF_KEY_APP_CONFIG
import com.mandiri.bds.core.common.util.security.SecurePrefManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject

class SplashAppConfigCache @Inject constructor(
    private val plainPrefManager: CorePlainPrefManager,
    private val securePrefManager: SecurePrefManager,
    private val moshi: Moshi
) {
    private var appConfigsTemp = hashMapOf<String, AppConfig>()
    private var appSecureKeysTemp = arrayListOf<String>()

    private val appConfigAdapter: JsonAdapter<AppConfig> by lazy {
        moshi.adapter(AppConfig::class.java)
    }

    //region SAVE
    fun saveAppVersion(version: String){
        securePrefManager.setString(PREF_KEY_APP_VERSION, version)
    }

    //region GET
    fun get(key: String) =
        if (appConfigsTemp.containsKey(key)) appConfigsTemp[key]
        else onGetAppConfigs(key).find { it.parameterKey == key }?.also { appConfigsTemp[key] = it }

    fun getAll() =
        getAppConfigsPlain().toMutableList().also { it.addAll(getAppConfigsSecure().toList()) }

    private fun onGetAppConfigs(key: String) =
        if (appSecureKeysTemp.contains(key)) getAppConfigsSecure() else getAppConfigsPlain()

    private fun getAppConfigsPlain() =
        plainPrefManager.getStringSet(PREF_KEY_APP_CONFIG)
            .map { appConfigAdapter.fromJson(it).orDefault(AppConfig()) }

    private fun getAppConfigsSecure() =
        securePrefManager.getStringSet(PREF_KEY_APP_CONFIG)
            .map { appConfigAdapter.fromJson(it).orDefault(AppConfig()) }

    fun resetConfigCache() {
        securePrefManager.clear()
    }

    fun set(appConfigResponse: List<AppConfig>) {
        appSecureKeysTemp = arrayListOf()
        appConfigsTemp = hashMapOf()

        appConfigResponse.filter {
            it.parameterKey in setOf(
                PREF_KEY_RSA_KEY,
                PREF_KEY_HSM_PIN_PUBLIC_KEY,
                PREF_KEY_HSM_PIN_EXPONENT
            )
        }.map { securePrefManager.setString(it.parameterKey, it.parameterValue) }

        plainPrefManager.setStringSet(
            key = PREF_KEY_APP_CONFIG,
            value = appConfigResponse.filter { !it.isSecured }
                .map { appConfigAdapter.toJson(it) }.toSet()
        )

        securePrefManager.setStringSet(
            key = PREF_KEY_APP_CONFIG,
            value = appConfigResponse.filter { it.isSecured }.map {
                appSecureKeysTemp.add(it.parameterKey)
                appConfigAdapter.toJson(it)
            }.toSet()
        )
    }

    fun loadAppVersion() = securePrefManager.getString(PREF_KEY_APP_VERSION)

}


