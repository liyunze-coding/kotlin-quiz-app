package com.deakin.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {
    var userName = ""
    private fun restartQuiz() {
        val quizIntent = Intent(this, QuizActivity::class.java)
        quizIntent.putExtra("username", userName)
        startActivity(quizIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // text info
        userName = intent.getStringExtra("username") ?: "Guest"
        val scoreDisplay = intent.getStringExtra("score")

        findViewById<TextView>(R.id.score).text = scoreDisplay
        findViewById<TextView>(R.id.congratsText).text = "Congratulations ${userName}!"

        // buttons
        findViewById<Button>(R.id.takeNewQuizButton).setOnClickListener { restartQuiz() }
        findViewById<Button>(R.id.finishButton).setOnClickListener {
            finishAffinity()
        }
    }
}