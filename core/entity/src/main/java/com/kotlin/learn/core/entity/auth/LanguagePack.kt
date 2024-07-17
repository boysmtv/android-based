package com.kotlin.learn.core.entity.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@JsonClass(generateAdapter = true)
data class LanguagePack(
    @Json(name = "content") val content: Map<String, Map<String, String>>? = null,
    @Json(name = "languagePackId") val languagePackId: String? = null
) {

    private fun forLanguage(lang: String) = content?.let { it[lang] }

    fun forLanguageAsJson(lang: String, moshi: Moshi): String? {
        val data = this.forLanguage(lang) ?: return null

        val listType =
            Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
        val adapter: JsonAdapter<Map<String, String>> = moshi.adapter(listType)

        return adapter.toJson(data)
    }
}