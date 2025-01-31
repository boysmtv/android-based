package com.kotlin.learn.core.common.util.extensions

import android.os.Handler
import android.os.Looper
import com.kotlin.learn.core.common.base.BaseFragment
import com.kotlin.learn.core.common.data.SupportedLanguage
import com.kotlin.learn.core.common.data.Translator
import com.kotlin.learn.core.common.util.orDefault
import com.kotlin.learn.core.utilities.Constants.DELAY_MILLIS_ONE_SECOND
import com.kotlin.learn.core.utilities.Constants.INDONESIA_LOCALE_TAG
import com.kotlin.learn.core.utilities.Constants.USA_LOCALE_TAG
import com.kotlin.learn.core.utilities.Constants.ZERO
import java.util.Locale

fun runWithDelay(delay: Long = DELAY_MILLIS_ONE_SECOND, nextAction: () -> Unit) =
    Handler(Looper.getMainLooper()).postDelayed({ nextAction() }, delay)

fun getLocale(): Locale = when (Translator.getDefaultLanguage()) {
    SupportedLanguage.INDONESIAN -> Locale.forLanguageTag(INDONESIA_LOCALE_TAG)
    SupportedLanguage.ENGLISH -> Locale.forLanguageTag(USA_LOCALE_TAG)
}

fun BaseFragment<*>.getString(key: String): String {
    return arguments?.getString(key).orEmpty()
}

fun BaseFragment<*>.getInt(key: String): Int {
    return arguments?.getInt(key).orDefault(ZERO)
}

fun BaseFragment<*>.getBoolean(key: String): Boolean {
    return arguments?.getBoolean(key).orDefault(false)
}

@Suppress("UNCHECKED_CAST")
fun <OutputType> BaseFragment<*>.getModel(key: String, classType: Class<out OutputType>): OutputType =
    if (arguments?.getString(key)?.isNotEmpty() == true) {
        jsonUtil.moshi.adapter(classType).fromJson(arguments?.getString(key).orEmpty())
            ?: arguments?.get(key) as OutputType
    } else {
        arguments?.get(key) as OutputType
    }

@Suppress("UNCHECKED_CAST")
fun <OutputType> BaseFragment<*>.get(key: String, classType: Class<out OutputType>): OutputType = when (classType) {
    String::class.java -> getString(key) as OutputType
    Int::class.java -> getInt(key) as OutputType
    Boolean::class.java -> getBoolean(key) as OutputType
    else -> getModel(key, classType)
}