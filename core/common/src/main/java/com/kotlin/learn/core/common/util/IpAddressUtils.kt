/*
 * Copyright © 2022 PT Bank Mandiri (Persero) Tbk.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.kotlin.learn.core.common.util

import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import com.kotlin.learn.core.utilities.Constants.EIGHT
import com.kotlin.learn.core.utilities.Constants.IP_TRANSFORM_FORMAT
import com.kotlin.learn.core.utilities.Constants.SIX_TEEN
import com.kotlin.learn.core.utilities.Constants.TWENTY_FOUR
import com.kotlin.learn.core.utilities.Constants.WIFI_ADDRESS_FORMAT
import java.util.Locale

object IpAddressUtils {
    fun getWifiIpAddress(context: Context): String {
        val wm = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val wifiInfo = wm.connectionInfo.ipAddress

        return java.lang.String.format(
            Locale.US, WIFI_ADDRESS_FORMAT,
            wifiInfo and IP_TRANSFORM_FORMAT,
            wifiInfo shr EIGHT and IP_TRANSFORM_FORMAT,
            wifiInfo shr SIX_TEEN and IP_TRANSFORM_FORMAT,
            wifiInfo shr TWENTY_FOUR and IP_TRANSFORM_FORMAT
        )
    }
}


