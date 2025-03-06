package com.example.firstaid.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firstaid.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.IconButton
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalsMapScreen(
    onBackClick: () -> Unit
) {
    val stPetersburg = LatLng(59.9311, 30.3609)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(stPetersburg, 11f)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.hospitals_map_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = LatLng(59.9500, 30.3167)),
                    title = "Больница №1",
                    snippet = "Описание или адрес"
                )
                Marker(
                    state = MarkerState(position = LatLng(59.9200, 30.3500)),
                    title = "Станция неотложной помощи №7",
                    snippet = "Описание или адрес"
                )
            }

            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Больница №1",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = painterResource(R.drawable.sample_hospital),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilledTonalButton(
                            onClick = { /* open route in Maps? */ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Маршрут")
                        }
                        FilledTonalButton(
                            onClick = { /* open website? */ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Сайт")
                        }
                        FilledTonalButton(
                            onClick = { /* add to favorites? */ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("В избранное")
                        }
                    }
                }
            }
        }
    }
}