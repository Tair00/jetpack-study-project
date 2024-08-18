package com.example.compositionapp.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.compositionapp.R
import com.example.compositionapp.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView,count : Int){
             textView.text = String.format(
                textView.context.getString(R.string.required_score),
                count
            )
}
@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, score : Int){
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )
}
@BindingAdapter("requirePercentage")
fun bindRequirePercentage(textView: TextView, percentage : Int){
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        percentage
    )
}
@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult){
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        getPercentOfRightAnswers(gameResult)
    )
}
private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}