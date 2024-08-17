package com.example.compositionapp.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
@Parcelize
class GameSettings (
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
):Parcelable{
    val minCountOfRightAnswersString: String
        get() = minCountOfRightAnswers.toString()
    val minPercentOfRightAnswersString: String
        get() = minPercentOfRightAnswers.toString()

}