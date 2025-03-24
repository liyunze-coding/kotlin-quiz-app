package com.deakin.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels() // initialize viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // enter name input
        val nameInput = findViewById<TextInputEditText>(R.id.nameInput)
        val btnStart = findViewById<Button>(R.id.startBtn)

        btnStart.setOnClickListener {
            val name = nameInput.text.toString().trim() // Get user input
            if (name.isNotEmpty()) {
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("username", name)
                startActivity(intent)
            } else {
                nameInput.error = "Please enter your name"
            }
        }
    }
}