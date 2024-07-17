package com.kotlin.learn.api.splash.data.local

import com.kotlin.learn.core.utilities.PreferenceConstants.Splash.PREF_KEY_IP_APP
import com.mandiri.bds.core.common.util.security.SecurePrefManager
import javax.inject.Inject

class SplashIpAppCache @Inject constructor(
    private val securePrefManager: SecurePrefManager
) {

    fun set(ip: String) {
        securePrefManager.setString(PREF_KEY_IP_APP, ip)
    }

    fun clear() = securePrefManager.remove(PREF_KEY_IP_APP)

}