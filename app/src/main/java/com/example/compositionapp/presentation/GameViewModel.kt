package com.example.compositionapp.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.compositionapp.R
import com.example.compositionapp.data.GameRepositoryImpl
import com.example.compositionapp.domain.entity.GameSettings
import com.example.compositionapp.domain.entity.Level
import com.example.compositionapp.domain.entity.Question
import com.example.compositionapp.domain.repository.GameRepository
import com.example.compositionapp.domain.usecases.GenerateQuestionUseCase
import com.example.compositionapp.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application): AndroidViewModel(application) {
    private val repository = GameRepositoryImpl
    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level
    private val context = application
    private val  generatedQuestionUseCase = GenerateQuestionUseCase(repository)
    private val  getGameSettingUseCase = GetGameSettingsUseCase(repository)
    private val _formattedTime = MutableLiveData <String>()
        val formattedTime : LiveData<String>
        get() = _formattedTime
    private var timer: CountDownTimer? = null
    private val _progressAnswers = MutableLiveData <String>()
    val progressAnswers : LiveData<String>
        get() = _progressAnswers
    private val  _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question
    private var countOfRightAnswer = 0
    private var countOfQuestions = 0

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers
    private val _enoughCountOfRightAnswers = MutableLiveData <Boolean>()

    val enoughCountOfRightAnswers  : LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData <Boolean>()
    val enoughPercentOfRightAnswers  : LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent : LiveData<Int>
        get() = _minPercent
    fun startGame(level: Level){
        getGameSettings(level)
        startTimer()
        generateQuestion()
    }
    fun chooseAnswer(number: Int){
        checkAnswer(number)
        generateQuestion()
    }
    private fun checkAnswer(number: Int){
        val rightAnswer = question.value?.rightAnswer
        if(number == rightAnswer){
            countOfRightAnswer++

        }
        countOfQuestions++
    }
    private fun getGameSettings(level: Level){
        this.level = level
        this.gameSettings = getGameSettingUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers

    }
    private fun generateQuestion(){
        _question.value = generatedQuestionUseCase(gameSettings.maxSumValue)

    }
    private fun updateProgress(){
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value =String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswer,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswers.value = countOfRightAnswer >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = percent >= gameSettings.minPercentOfRightAnswers
    }
    private fun calculatePercentOfRightAnswers(): Int{
        return ((countOfRightAnswer/countOfQuestions.toDouble())*100).toInt()
    }
    fun startTimer() {
         timer = object : CountDownTimer(
             gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS){
            override fun onTick(p0: Long) {
        _formattedTime.value = formatTime(p0)
            }

            override fun onFinish() {
               finishGame()
            }
        }
        timer?.start()
    }
    private fun formatTime(p0: Long): String{
        val seconds = p0/ MILLIS_IN_SECONDS
        val minutes = seconds/SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes* SECONDS_IN_MINUTES)
        return String.format("%02d:%02d",minutes,seconds)
    }
    private fun finishGame(){

    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
    companion object{
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

}