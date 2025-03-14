package com.example.firstaid.model

data class Hospital(
    val id: Int,
    val name: String,
    val address: String,
    val imageResId: Int? = null  // Optional image resource
)
