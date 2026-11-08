package com.litegba

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Launcher for picking the BIOS file
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

        btnSelectBios.setOnClickListener {
            openFilePicker("*/*", biosPickerLauncher)
        }

        btnSelectRom.setOnClickListener {
            openFilePicker("*/*", romPickerLauncher)
        }
    }

    private fun openFilePicker(mimeType: String, launcher: androidx.activity.result.ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = mimeType
        }
        launcher.launch(intent)
    }

    private fun validateAndLoadBios(uri: Uri) {
        // Check file size using ContentResolver to ensure it's byte-perfect 16KB
        val fileSize = contentResolver.openAssetFileDescriptor(uri, "r")?.use { it.length }
        if (fileSize == 16384L) {
            Toast.makeText(this, "Valid 16KB BIOS Selected!", Toast.LENGTH_SHORT).show()
            // TODO: Pass file descriptor or path to native C++ layer
        } else {
            Toast.makeText(this, "Error: BIOS must be exactly 16 KB.", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadRom(uri: Uri) {
        Toast.makeText(this, "ROM Selected: $uri", Toast.LENGTH_SHORT).show()
        // TODO: Initialize emulation core with ROM stream
    }
}
