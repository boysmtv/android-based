package com.kotlin.learn.core.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kotlin.learn.core.common.R
import com.kotlin.learn.core.common.util.JsonUtil
import com.kotlin.learn.core.common.util.network.NetworkConnectionLiveData
import com.kotlin.learn.core.common.util.network.debounce
import com.kotlin.learn.core.common.data.preferences.DataStorePreferences
import com.kotlin.learn.core.common.util.listener.CommonListener
import com.kotlin.learn.core.ui.dialog.base.BaseDataDialog
import com.kotlin.learn.core.ui.dialog.base.BaseDataDialogGeneral
import com.kotlin.learn.core.ui.dialog.common.DialogGeneralError
import com.kotlin.learn.core.ui.dialog.common.DialogNoInternet
import com.kotlin.learn.core.ui.dialog.common.DialogWithAction
import com.kotlin.learn.core.ui.dialog.common.ProgressBarDialog
import com.kotlin.learn.core.ui.util.showDialog
import com.kotlin.learn.core.utilities.Constant.EMPTY_STRING
import com.kotlin.learn.core.utilities.Constant.ZERO
import com.kotlin.learn.core.utilities.ResponseConstants.HttpCode.CODE_400
import com.kotlin.learn.core.utilities.ResponseConstants.HttpCode.CODE_401
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> T) :
    Fragment(), CommonListener {

    var binding: T by viewBinding()

    protected abstract fun setupView()

    private var onReconnect: (() -> Unit)? = null

    private var dialog: ProgressBarDialog? = null
    internal var dialogGeneralError: DialogGeneralError? = null
    private lateinit var dialogNoInternet: DialogNoInternet
    private var needToShowErrorConnection = false
    private var isConnectionAvailable = true
    var initCustomOnLoadState: (() -> Unit)? = null
    var initCustomOnSuccessState: (() -> Unit)? = null
    var initCustomOnErrorState: (() -> Unit)? = null
    var retryCount = ZERO
    var retryReinvoke = ZERO

    @Inject
    lateinit var dataStorePreferences: DataStorePreferences

    @Inject
    lateinit var jsonUtil: JsonUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupCheckConnection()
    }

    private fun setupCheckConnection() {
        val connectionLiveData = NetworkConnectionLiveData(requireContext())
        connectionLiveData.debounce().observe(viewLifecycleOwner) { isConnected ->
            isConnected?.let {
                needToShowErrorConnection = !it
                isConnectionAvailable = it
                if (::dialogNoInternet.isInitialized) dialogNoInternet.dismiss()
                if (it) onReconnect?.let { action -> action() } else showErrorNoInternetConnection()
            }
        }
    }

    override fun restartTask() {
        TODO("Not yet implemented")
    }

    override fun mainErrorHandler(code: String?, title: String?, message: String?, httpCode: String?) {
        TODO("Not yet implemented")
    }

    override fun showProgressDialog(isShow: Boolean) {
        TODO("Not yet implemented")
    }

    fun showDialogWithActionButton(
        dataToDialog: BaseDataDialog,
        actionClickPrimary: () -> Unit,
        actionClickSecondary: (() -> Unit)? = null,
        tag: String? = EMPTY_STRING,
    ) {
        val dialog = DialogWithAction(
            onClickButtonPrimary = { actionClickPrimary() },
            onClickButtonSecondary = { actionClickSecondary?.invoke() }
        ).apply { data = dataToDialog }
        if (tag?.isNotEmpty() == true) dialog.show(childFragmentManager, tag)
        else childFragmentManager.showDialog(dialog)
    }

    fun showHideProgress(isLoading: Boolean) =
        if (isLoading) {
            dialog = ProgressBarDialog()
            dialog?.show(childFragmentManager, dialog?.tag)
        } else {
            dialog?.dismiss()
            dialog = null
        }

    fun showGeneralError(
        data: BaseDataDialogGeneral,
        actionClick: () -> Unit,
        actionClickSecondary: () -> Unit,
    ) {
        dialogGeneralError = DialogGeneralError(
            data,
            actionClick,
            actionClickSecondary,
            onDismissDialogGeneralError()
        )
        dialogGeneralError?.show(childFragmentManager, tag)
    }

    fun showDialogGeneralError(title: String, message: String) {
        showGeneralError(
            BaseDataDialogGeneral(
                title = title,
                message = message,
                icon = R.drawable.ic_warning_rounded,
                textPrimaryButton = "OK, Close!",
                visibleBackToSplash = false,
                dismissOnAction = true
            ),
            actionClick = { dismissDialogGeneralError() }
        ) {}
    }

    fun onDismissDialogGeneralError(): () -> Unit = {
        dialogGeneralError?.dismiss()
        dialogGeneralError = null
    }

    fun dismissDialogGeneralError() {
        dialogGeneralError?.dismiss()
        dialogGeneralError = null
    }

    fun showErrorNoInternetConnection() {
        dialogNoInternet = DialogNoInternet()
        dialogNoInternet.show(childFragmentManager, dialogNoInternet.tag)
    }

    override fun onResume() {
        super.onResume()
        if (::dialogNoInternet.isInitialized && isConnectionAvailable) dialogNoInternet.dismiss()
    }

    override fun onDestroyView() {
        dialog = null
        dialogGeneralError = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        dialog = null
        dialogGeneralError = null
        super.onDestroy()
    }

    open fun errorHandler(
        code: String?,
        title: String? = null,
        message: String? = null,
        httpCode: String?,
    ) {
        when {
            httpCode == CODE_400 ->
                showGeneralError(
                    BaseDataDialogGeneral(
                        title = title,
                        message = message,
                        icon = R.drawable.ic_warning_rounded,
                        textPrimaryButton = "OK",
                        visibleBackToSplash = true,
                    ),
                    actionClick = { dismissDialogGeneralError() },
                    actionClickSecondary = { backToSplash() },
                )
            else -> {
                dialog?.dismiss()
                showGeneralError(
                    BaseDataDialogGeneral(
                        title = title,
                        message = message,
                        icon = R.drawable.ic_warning_rounded,
                        textPrimaryButton = "OK",
                        visibleBackToSplash = true,
                    ),
                    actionClick = { dismissDialogGeneralError() },
                    actionClickSecondary = { backToSplash() }
                )
            }
        }
    }

}