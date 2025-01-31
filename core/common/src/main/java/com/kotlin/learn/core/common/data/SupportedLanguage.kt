package com.kotlin.learn.core.common.data

enum class SupportedLanguage {

    ENGLISH {
        override var iso639Main = "en"
        override var iso639Subs = "eng"
    },

    INDONESIAN {
        override var iso639Main = "id"
        override var iso639Subs = "ind"
    };

    abstract var iso639Main: String
    abstract var iso639Subs: String

    override fun toString() = iso639Main

    companion object {
        fun fromIso639Main(lang: String): SupportedLanguage? {
            return values().find { it.iso639Main == lang }
        }

        fun fromIso639Subs(lang: String): SupportedLanguage? {
            return values().find { it.iso639Subs == lang }
        }
    }
}