package com.kotlin.learn.core.common.base

import com.kotlin.learn.core.common.R
import com.kotlin.learn.core.ui.dialog.base.BaseDataDialog
import com.kotlin.learn.core.ui.dialog.base.BaseDataDialogGeneral
import com.kotlin.learn.core.ui.dialog.common.DialogGeneralError

fun BaseFragment<*>.onErrorIdleTime() {
    dialogGeneralError =
        DialogGeneralError(
            BaseDataDialogGeneral(
                title = "",
                message = "",
                icon = R.drawable.icon_download,
                textPrimaryButton = "OK",
                isCancelable = false,
                dismissOnAction = true
            ),
            onClickPrimaryButton = { backToSplash() }
        )
    dialogGeneralError?.show(childFragmentManager, tag)
}

fun BaseFragment<*>.onGeneralError(httpCode: String?) {
    showHideProgress(false)
    showGeneralError(
        BaseDataDialogGeneral(
            title = "",
            message = "",
            icon = R.drawable.icon_download,
            textPrimaryButton = "OK",
            isCancelable = false,
            dismissOnAction = true
        ),
        actionClick = { dismissDialogGeneralError() },
        actionClickSecondary = { backToSplash() },
    )
}

fun BaseFragment<*>.onErrorUnauthorized(title: String?, message: String?) {
    showGeneralError(
        BaseDataDialogGeneral(
            title = "",
            message = "",
            icon = R.drawable.icon_download,
            textPrimaryButton = "OK",
            isCancelable = false,
            dismissOnAction = true
        ),
        actionClick = { dismissDialogGeneralError() },
        actionClickSecondary = { backToSplash() },
    )
}

fun BaseFragment<*>.softInputMode(softInputMode: Int) =
    (activity as? BaseActivity<*>)?.window?.setSoftInputMode(softInputMode)

fun BaseFragment<*>.backToSplash() {
    dialogGeneralError?.dismiss()
    dialogGeneralError = null
    (activity as? BaseActivity<*>)?.backToSplash()
}


fun BaseFragment<*>.showCommonGeneralError(response: Result<Any>) {
    showProgressDialog(false)
    showDialogWithActionButton(
        BaseDataDialog(
            title = "",
            content = "",
            secondaryButtonShow = false,
            primaryButtonShow = true,
            primaryButtonText = "",
            secondaryButtonText = "OK",
            icon = R.drawable.icon_download
        ), {}, {}, "DIALOG"
    )
}