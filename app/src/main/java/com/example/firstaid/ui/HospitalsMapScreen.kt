package com.example.firstaid.ui

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalsMapScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = { Text("Search hospitals...") },
                        trailingIcon = {
                            IconButton(onClick = { /* Handle search */ }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            }
                        }
                    ) {}
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            OpenStreetMapView(context)
        }
    }
}

@Composable
fun OpenStreetMapView(context: Context) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            Configuration.getInstance().load(ctx, ctx.getSharedPreferences(ctx.packageName, Context.MODE_PRIVATE))
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                val controller: IMapController = controller
                controller.setZoom(14.0) // Default zoom level
                controller.setCenter(org.osmdroid.util.GeoPoint(59.9343, 30.3351)) // Saint Petersburg center

                val myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(ctx), this)
                myLocationOverlay.enableMyLocation()
                myLocationOverlay.enableFollowLocation()
                myLocationOverlay.isDrawAccuracyEnabled = true

                myLocationOverlay.runOnFirstFix {
                    post {
                        val userLocation = myLocationOverlay.myLocation
                        if (userLocation != null) {
                            controller.setCenter(userLocation) // Move map to user's location
                            controller.animateTo(userLocation)
                        }
                    }
                }

                overlays.add(myLocationOverlay)

                val hospitalLocations = listOf(
                    org.osmdroid.util.GeoPoint(60.0922, 30.2368), // Больница №1
                    org.osmdroid.util.GeoPoint(59.9447, 30.2756), // Поликлиника №5
                    org.osmdroid.util.GeoPoint(59.9434, 30.2759), // Травмпункт №3
                    org.osmdroid.util.GeoPoint(59.9709, 30.3099), // Станция №1
                    org.osmdroid.util.GeoPoint(59.8737, 30.3638) // Отделение экстренной медицинской помощи
                )

                hospitalLocations.forEach { geoPoint ->
                    val marker = org.osmdroid.views.overlay.Marker(this)
                    marker.position = geoPoint
                    marker.setAnchor(org.osmdroid.views.overlay.Marker.ANCHOR_CENTER, org.osmdroid.views.overlay.Marker.ANCHOR_BOTTOM)
                    marker.title = "Hospital"
                    marker.snippet = "Click for details"
                    overlays.add(marker)
                }
            }
        }
    )
}