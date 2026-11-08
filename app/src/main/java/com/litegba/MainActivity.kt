package com.litegba

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Launcher for picking the 16 KB BIOS file
    private val biosPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                validateAndLoadBios(uri)
            }
        }
    }

    // Launcher for picking the GBA ROM file
    private val romPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                loadRom(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSelectBios = findViewById<Button>(R.id.btnSelectBios)
        val btnSelectRom = findViewById<Button>(R.id.btnSelectRom)

        // Set up button click listeners to trigger SAF file pickers
        btnSelectBios.setOnClickListener {
            openFilePicker(biosPickerLauncher)
        }

        btnSelectRom.setOnClickListener {
            openFilePicker(romPickerLauncher)
        }
    }

    private fun openFilePicker(launcher: androidx.activity.result.ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*" // Allows selecting binary and ROM files
        }
        launcher.launch(intent)
    }

    private fun validateAndLoadBios(uri: Uri) {
        // Ensure the selected file is a byte-perfect 16 KB (16384 bytes) GBA BIOS
        val fileSize = contentResolver.openAssetFileDescriptor(uri, "r")?.use { it.length } ?: 0L
        
        if (fileSize == 16384L) {
            Toast.makeText(this, "Valid 16 KB BIOS Selected!", Toast.LENGTH_SHORT).show()
            // TODO: Pass the URI string down to your C++ JNI layer for the mGBA core
        } else {
            Toast.makeText(this, "Invalid BIOS! Must be exactly 16 KB (found $fileSize bytes).", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadRom(uri: Uri) {
        // Extract the file name for display purposes
        var romName = "Unknown ROM"
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                romName = cursor.getString(nameIndex)
            }
        }

        Toast.makeText(this, "ROM Loaded: $romName", Toast.LENGTH_SHORT).show()
        // TODO: Pass URI or file descriptor to your C++ emulation core to boot the game
    }
}
