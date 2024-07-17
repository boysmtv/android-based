import com.kotlin.learn.core.common.data.preferences.CorePrefManager
import com.kotlin.learn.core.entity.auth.LanguagePack
import com.kotlin.learn.core.utilities.PreferenceConstants
import com.squareup.moshi.JsonAdapter
import javax.inject.Inject

class SplashLanguagePackCache @Inject constructor(
    private val prefManager: CorePrefManager,
    private val adapter: JsonAdapter<LanguagePack>
) {

    @Synchronized
    fun get(): LanguagePack? =
        prefManager.getString(PreferenceConstants.Language.PREF_KEY_LANGUAGE_PACK)?.let {
            adapter.fromJson(it)
        }

    fun set(languagePack: LanguagePack) {
        val data = adapter.toJson(languagePack)
        prefManager.setString(PreferenceConstants.Language.PREF_KEY_LANGUAGE_PACK, data)
    }

    fun clear() {
        prefManager.remove(PreferenceConstants.Language.PREF_KEY_LANGUAGE_PACK)
    }
}