package com.example.compositionapp.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.compositionapp.data.GameRepositoryImpl
import com.example.compositionapp.domain.entity.GameSettings
import com.example.compositionapp.domain.entity.Level
import com.example.compositionapp.domain.repository.GameRepository
import com.example.compositionapp.domain.usecases.GenerateQuestionUseCase
import com.example.compositionapp.domain.usecases.GetGameSettingsUseCase

class GameViewModel: ViewModel() {
    private val repository = GameRepositoryImpl
    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level
    private val  generatedQuestionUseCase = GenerateQuestionUseCase(repository)
    private val  getGameSettingUseCase = GetGameSettingsUseCase(repository)
    private val _formattedTime = MutableLiveData <String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime
    fun startGame(level: Level){
        this.level = level
        this.gameSettings = getGameSettingUseCase(level)
    }

    fun startTimer(){
        val timer = object : CountDownTimer(gameSettings.gameTimeInSecond,
            MILLIS_IN_SECONDS){
            override fun onTick(p0: Long) {
                TODO("Not yet implemented")
            }

            override fun onFinish() {
               finishGame()
            }
        }
    }
    private fun formatTime(p0: Long): String{
        val seconds = p0/ MILLIS_IN_SECONDS
        val minutes = seconds/SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes* SECONDS_IN_MINUTES)
        return String.format("%02d:%02d",minutes,seconds)
    }
    private fun finishGame(){

    }
    companion object{
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

}