package com.litegba

import android.content.Context
import android.util.Log

object EmulatorEngine {
    private const val TAG = "liteGBA_Emulator"
    private const val LIB_NAME = "mgba_libretro"

    fun initLibrary(context: Context? = null) {
        try {
            System.loadLibrary(LIB_NAME)
            Log.i(TAG, "Native library loaded: $LIB_NAME")
        } catch (e: UnsatisfiedLinkError) {
            Log.e(TAG, "Failed to load native library: $LIB_NAME", e)
            context?.let {
                ErrorHandler.handle(
                    it,
                    ErrorHandler.ErrorCode.ERR_NATIVE_LIB_LOAD,
                    e.message ?: "UnsatisfiedLinkError"
                )
            }
        }
    }
}
