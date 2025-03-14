package com.example.firstaid.model

import org.osmdroid.util.GeoPoint


data class Hospital(
    val name: String,
    val description: String,
    val imageResId: Int,
    val type: HospitalType,
    val geoPoint: GeoPoint
)
