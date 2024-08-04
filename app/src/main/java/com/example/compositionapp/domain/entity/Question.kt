package com.example.compositionapp.domain.entity

data class Question (
    val sum: Int,
    val visibledNumber: Int,
    val options: List<Int>
)