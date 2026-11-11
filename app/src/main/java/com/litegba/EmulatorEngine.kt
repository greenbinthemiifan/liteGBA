package com.litegba

import android.content.Context
import android.util.Log

class EmulatorEngine {
    companion object {
        private const val TAG = "EmulatorEngine"

        /**
         * Loads the pre-built libmgba_libretro.so native library.
         * Returns true if successful, or triggers the error handler if it fails.
         */
        fun initLibrary(context: Context): Boolean {
            return try {
                System.loadLibrary("mgba_libretro")
                Log.d(TAG, "libmgba_libretro loaded successfully!")
                true
            } catch (e: UnsatisfiedLinkError) {
                Log.e(TAG, "Failed to load native library: ${e.message}")
                ErrorHandler.handle(
                    context,
                    ErrorHandler.ErrorCode.ERR_NATIVE_LIB_LOAD,
                    e.localizedMessage ?: "Missing or incompatible architecture library"
                )
                false
            }
        }
    }
}
