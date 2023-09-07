package util

import android.util.Log

actual object Logger {
    actual fun error(tag: String?, message: Any?) {
        Log.e(tag, message.toString())
    }

    actual fun debug(tag: String?, message: Any?) {
        Log.d(tag, message.toString())
    }

    actual fun info(tag: String?, message: Any?) {
        Log.i(tag, message.toString())
    }

    actual fun warn(tag: String?, message: Any?) {
        Log.w(tag, message.toString())
    }

    actual fun verbose(tag: String?, message: Any?) {
        Log.v(tag, message.toString())
    }
}