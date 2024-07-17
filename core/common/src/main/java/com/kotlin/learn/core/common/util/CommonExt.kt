package com.kotlin.learn.core.common.util

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kotlin.learn.core.common.base.BaseActivity
import com.kotlin.learn.core.common.data.Translator
import com.kotlin.learn.core.utilities.getString

inline fun <T> T?.orDefault(
    default: T,
    additionalCheck: (T) -> Boolean = { _ -> true }
): T = if (this != null && additionalCheck(this)) this else default

fun ViewBinding.translate(@StringRes stringId: Int) = Translator.get(root.resources.getString(stringId))

fun ViewBinding.translateWithValue(@StringRes stringId: Int, replacement: String) =
    TranslatorUtil.translateWithValue(root.getString(stringId), replacement)

fun BottomSheetDialogFragment.translate(@StringRes stringId: Int) = Translator.get(getString(stringId))

fun Resources.translate(@StringRes stringId: Int) = Translator.get(getString(stringId))

fun BaseActivity<*>.translate(@StringRes stringId: Int) = Translator.get(getString(stringId))

fun ViewBinding.translate(localization: String) = Translator.get(localization)