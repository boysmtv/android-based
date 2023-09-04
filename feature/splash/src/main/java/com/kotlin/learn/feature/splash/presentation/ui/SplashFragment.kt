package com.kotlin.learn.feature.splash.presentation.ui

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.viewModels
import com.kotlin.learn.core.common.base.BaseFragment
import com.kotlin.learn.core.common.google.GoogleSignInExt
import com.kotlin.learn.core.common.util.InternetUtil
import com.kotlin.learn.core.common.util.LocationUtil
import com.kotlin.learn.core.common.util.NotificationUtil
import com.kotlin.learn.core.common.util.event.invokeDataStoreEvent
import com.kotlin.learn.core.model.PermissionModel
import com.kotlin.learn.core.model.ProfileModel
import com.kotlin.learn.core.nav.navigator.AuthNavigator
import com.kotlin.learn.core.utilities.Constant
import com.kotlin.learn.core.common.util.TransactionUtil
import com.kotlin.learn.core.utilities.extension.launch
import com.kotlin.learn.feature.splash.databinding.FragmentSplashBinding
import com.kotlin.learn.feature.splash.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val tag = this::class.java.simpleName

    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var authNavigator: AuthNavigator

    private var googleSignInExt: GoogleSignInExt = GoogleSignInExt({}, {})

    private var profileModel = ProfileModel()

    override fun setupView() {
        init()
        setupListener()
    }

    private fun init() {
        googleSignInExt.initGoogle(requireContext())
    }

    private fun setupListener() {

        profileModel.apply {
            profileId = TransactionUtil.generateTransactionID()
            connection = InternetUtil(requireContext()).getStatusConnectionModel()
            permission = PermissionModel().apply {
                location = LocationUtil(requireContext()).checkPermissions()
                internet = InternetUtil(requireContext()).isNetworkAvailable()
                notification = NotificationUtil(requireContext()).isNotificationEnabled()
            }
            updatedAt = TransactionUtil.getTimestampWithFormat()
            createdAt = updatedAt
        }
        Log.e(tag, "ProfileModel : ${jsonUtil.toJson(profileModel)}")

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.fetchUserFromDatastore().launch(this) { event ->
                invokeDataStoreEvent(event,
                    isFetched = { data ->
                        data?.let {
                            if (it.displayName != Constant.EMPTY_STRING) launchToHome()
                            else navigateToGreetings()
                        }
                    },
                    isError = {
                        navigateToGreetings()
                    }, {}
                )
            }
        }, 100)
    }

    private fun launchToHome() {
        authNavigator.fromSplashToHome(this@SplashFragment)
    }

    private fun launchToGreetings() {
        //authNavigator.fromSplashToGreetings(this@SplashFragment)
        authNavigator.fromSplashToMenu(this@SplashFragment)
    }

    private fun googleSignOut() = googleSignInExt.signOut({}, {})

    private fun navigateToGreetings() {
        googleSignOut()
        launchToGreetings()
    }

}