package com.kotlin.learn.core.nav.navigation

import android.content.Context
import androidx.navigation.NavOptions

interface DeeplinkNavigation {
    fun navigate(state: String, context: Context, vararg arguments: Pair<String, Any>, navOptions: NavOptions? = null)
    fun navigate(state: String, vararg arguments: Pair<String, Any>, navOptions: NavOptions? = null)
}