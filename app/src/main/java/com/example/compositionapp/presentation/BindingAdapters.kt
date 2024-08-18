package com.example.compositionapp.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.compositionapp.R

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView,count : Int){
             textView.text = String.format(
                textView.context.getString(R.string.required_score),
                count
            )
}