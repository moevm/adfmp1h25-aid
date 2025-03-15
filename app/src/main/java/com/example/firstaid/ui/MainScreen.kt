package com.example.firstaid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firstaid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onClickQuestionaireButton: () -> Unit,
    onClickLegalInfoButton: () -> Unit,
    onClickHospitalsButton: () -> Unit,
    onClickGuidesButton: () -> Unit,
    onClickSearchBar: () -> Unit,
    onEmergencyCall: (String) -> Unit // Исправленный параметр
) {
    var showEmergencyDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showEmergencyDialog = true },
                containerColor = Color.Red,
                contentColor = Color.White,
                modifier = Modifier
                    .size(64.dp)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "Emergency Call",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            SearchBar(
                modifier = Modifier.padding(bottom = 100.dp, top = 20.dp),
                query = "",
                placeholder = { Text(text = stringResource(R.string.search_hint)) },
                onQueryChange = {},
                onSearch = {},
                active = false,
                onActiveChange = { isActive ->
                    if (isActive) {
                        onClickSearchBar()
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { onClickSearchBar() }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    }
                }
            ) { }

            MainPageButtonsGroup(
                onClickQuestionaireButton = onClickQuestionaireButton,
                onClickLegalInfoButton = onClickLegalInfoButton,
                onClickHospitalsButton = onClickHospitalsButton,
                onClickGuidesButton = onClickGuidesButton
            )
        }
    }

    if (showEmergencyDialog) {
        AlertDialog(
            onDismissRequest = { showEmergencyDialog = false },
            title = { Text(text = stringResource(R.string.emergency_dialog_title)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    EmergencyNumberButton(
                        number = "112",
                        service = "Общая служба экстренной помощи",
                        onClick = { onEmergencyCall("tel:112") }
                    )
                    EmergencyNumberButton(
                        number = "101",
                        service = "Пожарная служба",
                        onClick = { onEmergencyCall("tel:101") }
                    )
                    EmergencyNumberButton(
                        number = "102",
                        service = "Полиция",
                        onClick = { onEmergencyCall("tel:102") }
                    )
                    EmergencyNumberButton(
                        number = "103",
                        service = "Скорая помощь",
                        onClick = { onEmergencyCall("tel:103") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showEmergencyDialog = false }) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        )
    }
}

@Composable
fun MainPageButtonsGroup(
    modifier: Modifier = Modifier,
    onClickQuestionaireButton: () -> Unit,
    onClickLegalInfoButton: () -> Unit,
    onClickHospitalsButton: () -> Unit,
    onClickGuidesButton: () -> Unit
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        FilledTonalButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = onClickQuestionaireButton,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(R.string.questionnaire_button_title))
        }
        FilledTonalButton(
            onClick = onClickGuidesButton,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.guides_list_button_title))
        }
        FilledTonalButton(
            onClick = onClickHospitalsButton,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.hospitals_button_title))
        }
        FilledTonalButton(
            onClick = onClickLegalInfoButton,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.legal_info_button_title))
        }
    }
}

@Composable
fun EmergencyNumberButton(
    number: String,
    service: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Text(
            text = "$number — $service",
            modifier = Modifier.padding(4.dp)
        )
    }
}