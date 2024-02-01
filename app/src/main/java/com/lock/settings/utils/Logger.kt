package com.lock.settings.utils

import android.util.Log

private const val TAG = "LOG"
fun log(log: String) {
    log(null, log)
}

fun log(tag: String?, log: String) {
    Log.d(TAG + (tag ?: ""), log)
}
