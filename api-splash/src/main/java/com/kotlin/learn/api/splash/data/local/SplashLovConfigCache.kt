package com.kotlin.learn.api.splash.data.local

import CorePlainPrefManager
import com.kotlin.learn.core.common.util.JsonUtil
import com.kotlin.learn.core.entity.auth.Lov
import com.kotlin.learn.core.entity.auth.LovResult
import com.kotlin.learn.core.utilities.PreferenceConstants
import com.mandiri.bds.core.common.util.security.SecurePrefManager
import javax.inject.Inject

class SplashLovConfigCache @Inject constructor(
    private val securePrefManager: SecurePrefManager,
    private val plainPrefManager: CorePlainPrefManager,
    private val jsonUtil: JsonUtil
) {
    fun getLovByKey (key : String): List<Lov>? = getLov()?.filter { it.type?.trim() == key }

    fun getLov(): List<Lov>? = securePrefManager.getString(
        PreferenceConstants.AppConfig.PREF_KEY_LOV)?.let {
        jsonUtil.fromJson<LovResult>(it)?.lov
    }

    fun setLov(list: List<Lov>){
        securePrefManager.setString(
            PreferenceConstants.AppConfig.PREF_KEY_LOV,
            jsonUtil.toJson(
                LovResult(
                    lov = list
                )
            )
        )
    }
}