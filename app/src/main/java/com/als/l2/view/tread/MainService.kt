package com.als.l2.view.tread

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log

private const val TAG = "MainServiceTAG"
const val MAIN_SERVICE_INT_EXTRA = "MainServiceIntExtra"
const val MAIN_SERVICE_STRING_EXTRA = "MainServiceExtra"

class MainService : IntentService("MainIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        createLogMessage("onHandleIntent ${intent?.getStringExtra(MAIN_SERVICE_STRING_EXTRA)}")
        intent?.let {
            sendBack(it.getIntExtra(MAIN_SERVICE_INT_EXTRA, 0).toString())
        }

    }

    override fun onCreate() {
        createLogMessage("onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createLogMessage("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        createLogMessage("onDestroy")
        super.onDestroy()
    }

    //Выводим уведомление в строке состояния
    private fun createLogMessage(message: String) {
        Log.d(TAG, message)
    }

    //Отправка уведомления о завершении сервиса
    private fun sendBack(result: String) {
        val broadcastIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
        broadcastIntent.putExtra(THREADS_FRAGMENT_BROADCAST_EXTRA, result)
        sendBroadcast(broadcastIntent)
    }
}