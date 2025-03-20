package com.example.firstaid.ui

import android.content.Context
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.firstaid.data.Datasource
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import android.content.pm.PackageManager
import android.location.LocationManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalsMapScreen(
    hospitalId: Int,
    onBackClick: () -> Unit,
    onClickSearchBar: () -> Unit
) {
    val context = LocalContext.current
    val userLocation = remember { mutableStateOf<org.osmdroid.util.GeoPoint?>(null) }
    val mapView = remember { mutableStateOf<MapView?>(null) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                userLocation.value = getLastKnownLocation(context)
                userLocation.value?.let { location ->
                    mapView.value?.controller?.setCenter(location)
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            userLocation.value = getLastKnownLocation(context)
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SearchBar(
                        modifier = Modifier.padding(10.dp),
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = { isActive ->
                            if (isActive) {
                                onClickSearchBar()
                            }
                        },
                        placeholder = { Text("Поиск") },
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        userLocation.value = getLastKnownLocation(context)
                        userLocation.value?.let { location ->
                            mapView.value?.controller?.setCenter(location)
                        }
                    } else {
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "Update Location",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            OpenStreetMapView(
                context = context,
                hospitalId = hospitalId,
                userLocation = userLocation.value,
                onMapViewCreated = { mapView.value = it }
            )
        }
    }
}
@Composable
fun OpenStreetMapView(
    context: Context,
    hospitalId: Int,
    userLocation: org.osmdroid.util.GeoPoint?,
    onMapViewCreated: (MapView) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            Configuration.getInstance()
                .load(ctx, ctx.getSharedPreferences(ctx.packageName, Context.MODE_PRIVATE))
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                val controller: IMapController = controller
                controller.setZoom(14.0) // Default zoom level

                val defaultLocation = org.osmdroid.util.GeoPoint(59.9343, 30.3351)
                controller.setCenter(defaultLocation)

                userLocation?.let { location ->
                    controller.setCenter(location)
                }

                val myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(ctx), this)
                myLocationOverlay.enableMyLocation()
                myLocationOverlay.enableFollowLocation()
                myLocationOverlay.isDrawAccuracyEnabled = true

                myLocationOverlay.runOnFirstFix {
                    post {
                        val location = myLocationOverlay.myLocation
                        if (location != null) {
                            controller.setCenter(location)
                            controller.animateTo(location)
                        }
                    }
                }

                overlays.add(myLocationOverlay)

                val hospitals = Datasource.hospitalsList

                hospitals.forEach { hospital ->
                    val marker = org.osmdroid.views.overlay.Marker(this)
                    marker.position = hospital.geoPoint
                    marker.setAnchor(
                        org.osmdroid.views.overlay.Marker.ANCHOR_CENTER,
                        org.osmdroid.views.overlay.Marker.ANCHOR_BOTTOM
                    )
                    marker.title = hospital.name
                    marker.snippet = "${hospital.type.value}; ${hospital.address}"
                    marker.image = getDrawable(context, hospital.imageResId!!)
                    marker.subDescription = "Телефон: ${hospital.phone} " +
                            " Часы работы: ${hospital.workingHours}"
                    if (hospital.id == hospitalId) {
                        marker.showInfoWindow()
                        controller.setCenter(hospital.geoPoint)
                        controller.setZoom(16.0)
                    }
                    overlays.add(marker)
                }

                // Передаем MapView в родительский компонент
                onMapViewCreated(this)
            }
        }
    )
}

private fun getLastKnownLocation(context: Context): org.osmdroid.util.GeoPoint? {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    // Проверяем наличие разрешения на доступ к геолокации
    return if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        try {
            // Получаем последнее известное местоположение
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            lastKnownLocation?.let {
                org.osmdroid.util.GeoPoint(it.latitude, it.longitude)
            }
        } catch (e: SecurityException) {
            // Обрабатываем исключение, если доступ к местоположению отклонен
            null
        }
    } else {
        // Если разрешение не предоставлено, возвращаем null
        null
    }
}