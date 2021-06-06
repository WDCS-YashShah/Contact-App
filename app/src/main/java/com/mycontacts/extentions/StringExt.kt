package com.mycontacts.extentions

import android.util.Log
import com.mycontacts.BuildConfig

fun String.debug(tag: String = "-->>") {
    if (BuildConfig.DEBUG) {
        Log.d(tag, this)
    }
}

fun String.logLargeString(tag: String = "-->>") {
    if (BuildConfig.DEBUG) {
        if (length > 3000) {
            Log.e(tag, substring(0, 3000));
            this.substring(3000).logLargeString()
        } else {
            Log.e(tag, this); // continuation
        }
    }
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }
