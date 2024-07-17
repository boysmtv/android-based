package com.kotlin.learn.feature.splash.presentation.ui

import androidx.fragment.app.viewModels
import com.kotlin.learn.core.common.base.BaseFragment
import com.kotlin.learn.core.common.base.observeDataFlow
import com.kotlin.learn.core.common.data.SupportedLanguage
import com.kotlin.learn.core.common.data.Translator
import com.kotlin.learn.core.common.util.IpAddressUtils
import com.kotlin.learn.feature.splash.databinding.FragmentSplashBinding
import com.kotlin.learn.feature.splash.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun setupView() {

    }

    private fun observeConfigCheck() = with(viewModel) {
        observeDataFlow(checkConfig,
            onLoad = {

            },
            onError = {

            }
        ) {
            Translator.reload(SupportedLanguage.INDONESIAN)
            onAppConfigSuccess()
        }
    }

    private fun onAppConfigSuccess() {
        context?.let { IpAddressUtils.getWifiIpAddress(it) }?.let { viewModel.saveIp(it) }
        viewModel.navigateToAuthentication()
    }
}