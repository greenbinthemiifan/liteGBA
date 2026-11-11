package com.litegba

import android.content.Context
import android.util.Log
import android.widget.Toast

object ErrorHandler {
    private const val TAG = "liteGBA_Error"

    enum class ErrorCode(val code: Int, val defaultMessage: String) {
        ERR_BIOS_INVALID_SIZE(1001, "BIOS file must be exactly 16 KB (16384 bytes)."),
        ERR_NATIVE_LIB_LOAD(1002, "Failed to load emulation native library."),
        ERR_ROM_LOAD_FAILED(1003, "Could not read or parse the selected ROM file."),
        ERR_UNKNOWN(-1, "An unexpected emulation error occurred.")
    }

    fun handle(context: Context, error: ErrorCode, customDetails: String = "") {
        val fullMessage = if (customDetails.isNotEmpty()) {
            "${error.defaultMessage} ($customDetails)"
        } else {
            error.defaultMessage
        }

        // Log the error for debugging
        Log.e(TAG, "Error [${error.code}]: $fullMessage")

        // Show a friendly message to the user
        Toast.makeText(context, "Error: $fullMessage", Toast.LENGTH_LONG).show()
    }
}
