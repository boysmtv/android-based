package com.kotlin.learn.core.common.data

import com.i18next.android.Operation

object Translator {

    private lateinit var provider: LanguagePackProvider
    private lateinit var defaultLanguage: SupportedLanguage

    fun init(provider: LanguagePackProvider, defaultLanguage: SupportedLanguage) {
        Translator.provider = provider
        Translator.defaultLanguage = defaultLanguage
        reload(defaultLanguage)
    }

    fun setDefaultLanguage(lang: String) = provider.setLanguage(lang)

    fun getDefaultLanguage(): SupportedLanguage = defaultLanguage

    fun get(key: String, operation: Operation? = null): String = provider.get(key, operation) ?: key

    fun get(key: String, lang: SupportedLanguage, operation: Operation? = null): String =
        provider.get(key, lang, operation) ?: key

    fun reload(defaultLanguage: SupportedLanguage) {
        provider.load(defaultLanguage)
        Translator.defaultLanguage = defaultLanguage
    }
}