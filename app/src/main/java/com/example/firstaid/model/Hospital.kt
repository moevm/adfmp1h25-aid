package com.example.firstaid.model
import org.osmdroid.util.GeoPoint

data class Hospital(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String? = null,
    val workingHours: String? = null,
    val coordinates: String? = null,
    val imageResId: Int? = null
    val imageResId: Int,
    val type: HospitalType,
    val geoPoint: GeoPoint
)