package com.kotlin.learn.core.common.data

import android.content.Context
import android.os.CountDownTimer
import com.kotlin.learn.core.common.util.ConnectivityUtil
import com.kotlin.learn.core.utilities.Constants.DELAY_COROUTINE_SEQUENTIAL
import com.kotlin.learn.core.utilities.Constants.DELAY_MILLIS_ONE_SECOND
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class CoroutineSafeJob(
    private val context: Context?,
    onErrorAction: () -> Unit,
    private val isSessionIdle: () -> Boolean,
    private val onTimeoutAction: (() -> Unit)? = null,
    private val onTimeoutCompletion: (() -> Unit)? = null
) {

    lateinit var continueProcess: (() -> Boolean)

    private var delayAfterInMillis: Long = 0
    private val withDelay = AtomicBoolean(true)
    private val supervisorJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + supervisorJob)
    private var runningJob: Job? = null
    var timer: CountDownTimer? = null

    private lateinit var tryAction: suspend () -> Unit
    private var actions: MutableList<suspend () -> Unit> = mutableListOf()
    private val tryOnErrorAction = {
        try {
            onErrorAction()
        } catch (e: Throwable) {
            Timber.e(e)
        }
    }

    //do need to be improved
    private var tryOnTimeoutAction = suspend {
        try {
            runningJob?.apply {
                invokeOnCompletion { if (isCompleted && !isActive) onTimeoutCompletion?.let { it() } }
                onTimeoutAction?.let { it() }
            }
        } catch (e: Throwable) {
            Timber.e(e)
        }
    }

    private fun startTask() {
        runningJob = scope.launch(IO) {
            context?.let {
                tryAction()
                if (withDelay.get()) {
                    delay(delayAfterInMillis)
                    tryOnTimeoutAction()
                }
            }
        }
    }

    private fun startTasksSequential() {
        runningJob = scope.launch(IO) {
            context?.let {
                if (ConnectivityUtil.isConnected(it)) {
                    actions.forEach {
                        it()
                        if (withDelay.get()) {
                            delay(delayAfterInMillis)
                            tryOnTimeoutAction()
                        }
                    }
                } else tryOnErrorAction()
            }
        }
    }

    private fun activateDelayTask(value: Boolean) {
        withDelay.set(value)
    }

    fun addDelay(value: Long) {
        withDelay.set(true)
        delayAfterInMillis = value
    }

    fun run(withDelay: Boolean? = false, cancel: Boolean = true, newAction: suspend () -> Unit) {
        withDelay?.let { activateDelayTask(it) }
        if (cancel) cancelTask()
        tryAction = {
            try {
                newAction()
            } catch (e: Throwable) {
                Timber.e(e)
            }
        }
        if (continueProcess() && !isSessionIdle()) startTask()
    }

    fun run(withDelay: Boolean? = false, newActions: List<suspend () -> Unit>) {
        withDelay?.let { activateDelayTask(it) }
        cancelTask()
        newActions.forEach {
            val action = suspend {
                try {
                    it()
                } catch (e: Throwable) {
                    Timber.e(e)
                }
            }
            actions.add(action)
        }
        // delay to prevent the conflict between each action
        addDelay(DELAY_COROUTINE_SEQUENTIAL)
        startTasksSequential()
    }

    fun safeScope(block: CoroutineSafeJob.() -> Unit) {
        if (!continueProcess() || isSessionIdle()) return

        if (ConnectivityUtil.isConnected(context ?: return)) {
            try {
                block(this)
            } catch (e: Throwable) {
                Timber.e(e)
            }
        } else tryOnErrorAction()
    }

    fun <T> launch(block: () -> T) {
        try {
            block()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun cancelTask() {
        runningJob?.cancel()
    }

    fun retryTask() {
        if (::tryAction.isInitialized) {
            cancelTask()
            startTask()
        }
    }

    fun retrySequentialTasks() {
        if (actions.isNotEmpty()) {
            startTasksSequential()
        }
    }

    // Region new coroutine
    // TODO : AFTER STABLE NEED TO MERGE TO CURRENT COROUTINE
    fun runWithCounter(
        maxInterval: Long,
        cancel: Boolean = true,
        newAction: suspend () -> Unit,
        getMillisUntilFinished: ((Long) -> Unit)? = null
    ) {
        activateCounter(maxInterval, getMillisUntilFinished)
        if (cancel) cancelTask()
        tryAction = suspend {
            try {
                newAction()
            } catch (e: Throwable) {
                Timber.e(e)
            }
        }
        startSingleTask()
    }

    private fun activateCounter(interval: Long, getMillisUntilFinished: ((Long) -> Unit)? = null) {
        timer = object : CountDownTimer(interval, DELAY_MILLIS_ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                getMillisUntilFinished?.let { it(millisUntilFinished) }
            }

            override fun onFinish() {
                cancelTask()
                onTimeoutCompletion?.let { it() }
                onTimeoutAction?.let { it() }
            }
        }
        timer?.start()
    }

    private fun startSingleTask() {
        runningJob = scope.launch(IO) {
            context?.let {
                if (ConnectivityUtil.isConnected(it)) {
                    tryAction()
                } else tryOnErrorAction()
            }
        }
    }

    fun retryWithCounterTask() {
        cancelTask()
        startSingleTask()
    }

    fun stopTimer() {
        timer?.cancel()
    }
}