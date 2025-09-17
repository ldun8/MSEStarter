package com.example.msestarter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Button in activity_second.xml with id btnMain
        val btnMain: Button = findViewById(R.id.btnMain)
        btnMain.setOnClickListener {
            // go back to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
