package com.kotlin.learn.api.splash.data.local

import android.content.Context
import com.kotlin.learn.api.splash.R
import com.kotlin.learn.core.entity.auth.LanguagePack
import com.squareup.moshi.JsonAdapter
import javax.inject.Inject

class SplashLanguagePackResource @Inject constructor(
    private val context: Context,
    private val adapter: JsonAdapter<LanguagePack>
) {

    fun get(): LanguagePack? {
        val inputStream = context.resources.openRawResource(R.raw.language_pack)
        val reader = inputStream.bufferedReader().use { it.readText() }

        return adapter.fromJson(reader)
    }

}