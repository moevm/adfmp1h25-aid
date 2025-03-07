package com.example.firstaid.ui

import android.content.Context
import android.graphics.Rect
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.example.firstaid.R
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.api.IMapController
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
                    org.osmdroid.util.GeoPoint(59.9386, 30.3141),
                    org.osmdroid.util.GeoPoint(59.9275, 30.3508),
                    org.osmdroid.util.GeoPoint(59.9600, 30.2955),
                    org.osmdroid.util.GeoPoint(59.9450, 30.3200),
                    org.osmdroid.util.GeoPoint(59.9200, 30.3300)
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