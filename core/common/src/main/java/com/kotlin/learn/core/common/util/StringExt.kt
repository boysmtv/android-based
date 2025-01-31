package com.kotlin.learn.core.common.util

import com.kotlin.learn.core.common.util.DateTimeUtils.convertDateToLocaleId
import com.kotlin.learn.core.common.util.DateTimeUtils.convertStringBirthdayToDate
import com.kotlin.learn.core.utilities.Constants.ADDITIONAL_SPACE
import com.kotlin.learn.core.utilities.Constants.ASTERISK_MASK
import com.kotlin.learn.core.utilities.Constants.AT_EMAIL
import com.kotlin.learn.core.utilities.Constants.Calender.DATE_FORMAT
import com.kotlin.learn.core.utilities.Constants.Calender.FULL_DATE_FORMAT
import com.kotlin.learn.core.utilities.Constants.ELEVEN
import com.kotlin.learn.core.utilities.Constants.EMPTY_STRING
import com.kotlin.learn.core.utilities.Constants.FOUR
import com.kotlin.learn.core.utilities.Constants.HYPHEN
import com.kotlin.learn.core.utilities.Constants.HYPHEN_WITH_SPACE
import com.kotlin.learn.core.utilities.Constants.ONE
import com.kotlin.learn.core.utilities.Constants.PLUS
import com.kotlin.learn.core.utilities.Constants.PhoneNumber.INDONESIA_PHONE_NUMBER
import com.kotlin.learn.core.utilities.Constants.PhoneNumber.INDONESIA_PHONE_NUMBER_CLEAR
import com.kotlin.learn.core.utilities.Constants.REGEX_MULTIPLE_SPACES
import com.kotlin.learn.core.utilities.Constants.SEVEN
import com.kotlin.learn.core.utilities.Constants.SIX
import com.kotlin.learn.core.utilities.Constants.SPACE_STRING
import com.kotlin.learn.core.utilities.Constants.THREE
import com.kotlin.learn.core.utilities.Constants.TWO
import com.kotlin.learn.core.utilities.Constants.ZERO
import com.kotlin.learn.core.utilities.Constants.ZERO_STRING
import org.threeten.bp.LocalDate
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String?.orReplaceWith(replacement: String) = this ?: replacement

fun String?.orReplaceEmpty(replacement: String) = if (this.isNullOrEmpty()) replacement else this

fun String?.orReplaceHyphen() = if (this.isNullOrEmpty()) HYPHEN else this

fun String?.orReplaceHyphenSpace() = if (this.isNullOrEmpty()) HYPHEN_WITH_SPACE else this

fun String?.joinWithHyphen(rightText: String) =
    this?.trimEnd().plus(HYPHEN_WITH_SPACE).plus(rightText.trimEnd()).removeSuffix(HYPHEN_WITH_SPACE)
        .removePrefix(HYPHEN_WITH_SPACE)

fun String?.removeExcessiveSpaces(replacement: String) = this.orReplaceEmpty(replacement)
    .replace(REGEX_MULTIPLE_SPACES.toRegex(), SPACE_STRING)

fun String.isValidEmail(regex: String?): Boolean {
    val pattern = Pattern.compile(regex)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isValidTextInput(regex: String?): Boolean {
    val pattern = Pattern.compile(regex)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isValidPhoneNumber(regex: String?): Boolean {
    val pattern = Pattern.compile(regex)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isValidAccountNumber(regex: String): Boolean {
    val pattern = Pattern.compile(regex)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun CharSequence.isValidRegex(regex: String): Boolean = if (isNotEmpty()) {
    val pattern = Pattern.compile(regex)
    val matcher: Matcher = pattern.matcher(this)
    matcher.matches()
} else false

fun CharSequence.checkRegex(regex: String): Boolean {
    val pattern = Pattern.compile(regex)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun CharSequence?.defaultEmpty(): CharSequence =
    if (this?.isNotEmpty() == true) this else EMPTY_STRING

fun String.maskingNumericWithSpace(): String {
    return this.maskingNumeric().replace(Regex("(.{$FOUR})"), ADDITIONAL_SPACE)
}

fun String.maskingNumericWithHyphen(): String {
    val maskNumber = StringBuilder()
    this.forEachIndexed { index, char ->
        if (index in setOf(THREE, SEVEN, ELEVEN)) {
            maskNumber.append(char + HYPHEN)
        } else {
            maskNumber.append(char)
        }
    }
    return maskNumber.toString()
}

fun String.maskingNumeric(): String {
    return this.replace(Regex(".(?=.{${FOUR}})"), ASTERISK_MASK)
}

fun String.maskingEmail(): String {
    val maxString = this.indexOf(AT_EMAIL)
    val maskEmail = StringBuilder()
    substringBefore(AT_EMAIL).forEachIndexed { index, char ->
        when {
            maxString > SIX ->
                if (index in THREE..maxString - FOUR) maskEmail.append(ASTERISK_MASK)
                else maskEmail.append(char)

            maxString in FOUR..SIX ->
                if (index in THREE..maxString) maskEmail.append(ASTERISK_MASK)
                else maskEmail.append(char)

            maxString in TWO..THREE -> if (index == ZERO) maskEmail.append(char)
            else maskEmail.append(ASTERISK_MASK)

            else -> maskEmail.append(ASTERISK_MASK)
        }
    }
    return maskEmail.append(substring(indexOf(AT_EMAIL), length)).toString()
}

fun String?.getFullDate(): String? {
    val parsedBirthDate: LocalDate?
    try {
        parsedBirthDate = convertStringBirthdayToDate(
            this,
            DATE_FORMAT
        )
    } catch (e: Exception) {
        return this
    }

    return parsedBirthDate?.let { convertDateToLocaleId(it, FULL_DATE_FORMAT) }
}

fun String?.validateValue(compareTo: String) = this.equals(compareTo, true)

fun String?.setIndonesiaPhoneNumberPrefix(): String? {
    return this?.let {
        when {
            it.take(ONE) == ZERO_STRING -> it.replaceFirst(ZERO_STRING, INDONESIA_PHONE_NUMBER)
            it.take(TWO) == INDONESIA_PHONE_NUMBER_CLEAR -> "${PLUS}$it"
            else -> it
        }
    } ?: run { null }
}