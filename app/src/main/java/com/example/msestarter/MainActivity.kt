package com.example.msestarter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity  // <-- requires appcompat dependency

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Put your real info
        findViewById<TextView>(R.id.tvName).setText(R.string.full_name_value)
        findViewById<TextView>(R.id.tvId).setText(R.string.student_id_value)


        findViewById<Button>(R.id.btnExplicit).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java)) // explicit
        }

        findViewById<Button>(R.id.btnImplicit).setOnClickListener {
            startActivity(Intent("com.example.msestarter.SHOW_CHALLENGES")) // implicit
        }
    }
}
