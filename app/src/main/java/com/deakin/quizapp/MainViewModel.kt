package com.deakin.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _userName = MutableLiveData<String>()
    private val _totalQuestions = 5
    private val _questionIndex = MutableLiveData(1)

    val userName: LiveData<String> = _userName
    val totalQuestions: Int get() = _totalQuestions // No need for LiveData since it's constant
    val questionIndex: LiveData<Int> = _questionIndex

    fun setUserName(name: String) {
        _userName.value = name.takeIf { it.isNotBlank() } ?: "Guest"
    }

    fun incrementQuestionIndex() {
        _questionIndex.value = (_questionIndex.value ?: 1) + 1
    }
}
