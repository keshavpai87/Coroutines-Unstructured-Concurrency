package com.example.coroutines_unstructured

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var count = 0
    private lateinit var btnDownloadUserData : Button
    private lateinit var btnCount : Button
    private lateinit var tvCount : TextView
    private lateinit var tvUserMessage : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnDownloadUserData = findViewById(R.id.btnDownloadUserData)
        btnCount = findViewById(R.id.btnCount)
        tvCount = findViewById(R.id.tvCount)
        tvUserMessage = findViewById(R.id.tvUserMessage)

        btnCount.setOnClickListener {
            tvCount.text = count++.toString()
            Log.i("MyTag", "Button Count ${Thread.currentThread().name}")
        }

        btnDownloadUserData.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch { // Adding a coroutine to execute the task
                downloadUserData()
            }
//            downloadUserData()
        }
    }

    private suspend fun downloadUserData() {
        for (i in 1..200000) {
            withContext(Dispatchers.Main) {
                tvUserMessage.text = "Downloading user $i in ${Thread.currentThread().name}"
            }
        }
    }
}