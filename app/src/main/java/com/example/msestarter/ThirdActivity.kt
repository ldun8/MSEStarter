package com.example.msestarter

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

class ThirdActivity : AppCompatActivity() {

    private lateinit var captureBtn: Button
    private lateinit var imageView: ImageView

    private var photoUri: Uri? = null
    private lateinit var tempPhotoFile: File

    private val requestCameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startCapture()
        else Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
    }

    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoUri != null) {
            imageView.setImageURI(photoUri)
        } else {
            Toast.makeText(this, "Capture canceled or failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        captureBtn = findViewById(R.id.btnCaptureImage)
        imageView = findViewById(R.id.imageView)

        captureBtn.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED -> startCapture()

                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    Toast.makeText(this, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show()
                    requestCameraPermission.launch(Manifest.permission.CAMERA)
                }

                else -> requestCameraPermission.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun startCapture() {
        tempPhotoFile = createTempImageFile()
        val authority = "${applicationContext.packageName}.fileprovider"
        photoUri = FileProvider.getUriForFile(this, authority, tempPhotoFile)
        takePicture.launch(photoUri)
    }

    private fun createTempImageFile(): File {
        val imagesDir = File(cacheDir, "images").apply { mkdirs() }
        return File(imagesDir, "captured.jpg").apply {
            if (exists()) delete()
            createNewFile()
        }
    }
}
