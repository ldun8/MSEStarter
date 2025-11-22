package com.example.msestarter

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CUSTOM_PERMISSION = "com.example.msestarter.MSE412"
        private const val REQ_CODE_MSE412 = 412
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Request the custom permission at runtime
        requestCustomPermission()

        // 2. Set up button to start SecondActivity
        val explicitButton = findViewById<Button>(R.id.btnExplicit)
        explicitButton.setOnClickListener {
            // Permission must be granted before launching SecondActivity
            if (ContextCompat.checkSelfPermission(this, CUSTOM_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {

                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(
                    this,
                    "Permission required to open challenges screen.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun requestCustomPermission() {
        if (ContextCompat.checkSelfPermission(this, CUSTOM_PERMISSION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(CUSTOM_PERMISSION),
                REQ_CODE_MSE412
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQ_CODE_MSE412) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
