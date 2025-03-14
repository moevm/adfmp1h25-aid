package com.example.firstaid.model

data class Hospital(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String? = null,
    val workingHours: String? = null,
    val coordinates: String? = null,
    val imageResId: Int? = null
)
