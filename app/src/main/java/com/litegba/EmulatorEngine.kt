package com.litegba

import android.util.Log

class EmulatorEngine {
    companion object {
        init {
            try {
                // Loads libmgba_libretro.so from your jniLibs folder
                System.loadLibrary("mgba_libretro")
                Log.d("EmulatorEngine", "libmgba_libretro loaded successfully!")
            } catch (e: UnsatisfiedLinkError) {
                Log.e("EmulatorEngine", "Failed to load native library: ${e.message}")
            }
        }

        fun initLibrary() {
            // Trigger companion object init
        }
    }
}
