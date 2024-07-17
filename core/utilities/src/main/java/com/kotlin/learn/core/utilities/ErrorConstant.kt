package com.kotlin.learn.core.utilities

val httpError = listOf(
    ResponseConstants.HttpCode.CODE_500,
    ResponseConstants.HttpCode.CODE_503,
    ResponseConstants.HttpCode.CODE_504,
    ResponseConstants.HttpCode.CODE_401
)

var listAvoidGeneralError = listOf(
    ResponseConstants.HttpCode.CODE_500,
    ResponseConstants.HttpCode.CODE_503,
    ResponseConstants.HttpCode.CODE_409,
    ResponseConstants.HttpCode.CODE_504,
    ResponseConstants.HttpCode.CODE_404
)