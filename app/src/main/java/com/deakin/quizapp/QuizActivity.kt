package com.deakin.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private val questionList = listOf(
    Question("Choose the correct answer:", "What is Android?", listOf("OS", "Browser", "Software"), 0),
    Question("Choose the correct answer:", "What language is used for Android?", listOf("Python", "Java", "C++"), 1),
    Question("Choose the correct answer:", "What is an Activity?", listOf("UI Component", "Database", "Server"), 0),
    Question("Choose the correct answer:", "What is the brain of a computer?", listOf("Keyboard", "Monitor", "CPU"), 2),
    Question("Choose the correct answer:", "What does RAM stand for?", listOf("Readable Application Module", "Random Access Memory", "Remote Access Machine"), 1)
)

class QuizActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var score = 0
    private var choice = -1
    private var userName = "Guest"

    private fun selectChoice(selectedIndex: Int) {
        val buttons = listOf(
            findViewById<Button>(R.id.choice1),
            findViewById<Button>(R.id.choice2),
            findViewById<Button>(R.id.choice3)
        )

        choice = selectedIndex

        buttons.forEach {
            it.setBackgroundColor(getColor(R.color.white))
            it.setTextColor(getColor(R.color.black))
        }

        buttons[choice].setBackgroundColor(getColor(R.color.selectGray))
        findViewById<Button>(R.id.submitButton).isEnabled = true
    }

    private fun loadQuestion() {
        val question = questionList[currentQuestionIndex]
        findViewById<TextView>(R.id.questionTitle).text = question.questionTitle
        findViewById<TextView>(R.id.questionDetails).text = question.questionDetails
        findViewById<Button>(R.id.choice1).text = question.choices[0]
        findViewById<Button>(R.id.choice2).text = question.choices[1]
        findViewById<Button>(R.id.choice3).text = question.choices[2]
        findViewById<TextView>(R.id.progressDisplay).text = "${currentQuestionIndex + 1}/${questionList.size}"
        findViewById<ProgressBar>(R.id.progressBar).progress = currentQuestionIndex + 1
    }

    private fun checkAnswer(selectedIndex: Int) {
        val correctIndex = questionList[currentQuestionIndex].correctAnswerIndex

        val buttons = listOf(
            findViewById<Button>(R.id.choice1),
            findViewById<Button>(R.id.choice2),
            findViewById<Button>(R.id.choice3)
        )

        // Turn correct answer green
        buttons[correctIndex].setBackgroundColor(getColor(R.color.correctGreen))
        buttons[correctIndex].setTextColor(getColor(R.color.white))

        // Disable all buttons to prevent multiple selections
        buttons.forEach { it.isEnabled = false }

        if (selectedIndex == correctIndex) {
            score++
        } else {
            // Turn selected answer red
            buttons[choice].setBackgroundColor(getColor(R.color.wrongRed))
            buttons[choice].setTextColor(getColor(R.color.white))
        }

        // hide welcome text
        findViewById<TextView>(R.id.tvGreeting).visibility = View.INVISIBLE

        findViewById<Button>(R.id.submitButton).text = "Next"
        findViewById<Button>(R.id.submitButton).setOnClickListener { nextQuestion() }
    }

    private fun resetButtons() {
        val buttons = listOf(
            findViewById<Button>(R.id.choice1),
            findViewById<Button>(R.id.choice2),
            findViewById<Button>(R.id.choice3)
        )

        buttons.forEach {
            it.isEnabled = true
            it.setBackgroundColor(getColor(R.color.white))
            it.setTextColor(getColor(R.color.black))
        }

        findViewById<Button>(R.id.submitButton).isEnabled = false
    }

    private fun nextQuestion() {
        findViewById<Button>(R.id.submitButton).text = "Submit"
        findViewById<Button>(R.id.submitButton).setOnClickListener { checkAnswer(choice) }
        if (currentQuestionIndex < questionList.size - 1) {
            resetButtons()
            currentQuestionIndex++
            loadQuestion()
        } else {
            showResult()
        }
    }

    private fun showResult() {
        val resultsIntent = Intent(this, ScoreActivity::class.java)
        resultsIntent.putExtra("username", userName)
        resultsIntent.putExtra("score", "$score/${questionList.size}")
        startActivity(resultsIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        userName = intent.getStringExtra("username") ?: "Guest" // Retrieve name
        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)

        tvGreeting.text = "Welcome $userName!"

        loadQuestion()
        resetButtons()

        tvGreeting.visibility = View.VISIBLE

        findViewById<Button>(R.id.choice1).setOnClickListener { selectChoice(0) }
        findViewById<Button>(R.id.choice2).setOnClickListener { selectChoice(1) }
        findViewById<Button>(R.id.choice3).setOnClickListener { selectChoice(2) }
        findViewById<Button>(R.id.submitButton).setOnClickListener { checkAnswer(choice) }
    }
}
