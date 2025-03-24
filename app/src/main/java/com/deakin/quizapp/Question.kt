package com.deakin.quizapp

data class Question(
    val questionTitle: String,
    val questionDetails: String,
    val choices: List<String>,
    val correctAnswerIndex: Int
)
