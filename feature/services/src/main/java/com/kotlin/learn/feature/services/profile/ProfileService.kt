package com.kotlin.learn.feature.services.profile

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.kotlin.learn.core.common.util.JsonUtil
import com.kotlin.learn.core.common.util.security.DataStorePreferences
import com.kotlin.learn.core.domain.UserUseCase
import com.kotlin.learn.core.network.KtorClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.annotation.Nullable
import javax.inject.Inject

@AndroidEntryPoint
class ProfileService : Service() {

    private val tag = this::class.java.simpleName

    private var threadProfile: ThreadProfile = ThreadProfile()

    @Inject
    lateinit var ktorClient: KtorClient

    @Inject
    lateinit var jsonUtil: JsonUtil

    @Inject
    lateinit var preferences: DataStorePreferences

    @Inject
    lateinit var userUseCase: UserUseCase

    private var isServiceRunning = true

    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private val threadSleepTimer = 2000L

    override fun onCreate() {
        super.onCreate()

        threadProfile.initComponent(
            context = this@ProfileService,
            jsonUtil = jsonUtil,
            ktorClient = ktorClient,
            preferences = preferences,
            useCase = userUseCase
        )

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.e(tag, "ProfileService is running...")
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                while (isServiceRunning) {
                    try {
                        threadProfile.getTokenFirebase()
                        Thread.sleep(threadSleepTimer)
                        setupCheckToken()
                    } catch (e: InterruptedException) {
                        Log.e(tag, "ProfileService is error : ${e.message}")
                        e.printStackTrace()
                    }
                }
            }
        }

        return START_STICKY
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun setupCheckToken() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                if (threadProfile.getUser().isNotEmpty() && threadProfile.getToken().isNotEmpty()) {
                    isServiceRunning = false
                    stopSelf()
                    Log.e(tag, "ProfileService is stopped...")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }

}