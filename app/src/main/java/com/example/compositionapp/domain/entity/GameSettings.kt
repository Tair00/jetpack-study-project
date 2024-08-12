package com.example.compositionapp.domain.entity

import java.io.Serializable

class GameSettings (
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
):Serializable