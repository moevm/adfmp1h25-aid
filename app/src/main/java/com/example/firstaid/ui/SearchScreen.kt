package com.example.firstaid.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.firstaid.R
import com.example.firstaid.model.Guide
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onInstitutionClick: (String) -> Unit,
    onBackClick: () -> Unit,
    guides: List<Guide>,  // Add guides parameter
    onGuideClick: (Int) -> Unit  // Add guide click callback
) {
    var query by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val defaultOrganizationSuggestions = listOf("Больница №1", "Станция №7")
    val defaultGuideSuggestions = listOf("Потеря сознания", "Ножевое ранение", "Вывих руки")

    val categories = listOf("Учреждения", "Травмпункты", "Поликлиники")
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    SearchBar(
                        query = query,
                        onQueryChange = { query = it },
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = { Text(text = stringResource(R.string.search_hint)) },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { /* Handle search action */ }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon"
                                )
                            }
                        }
                    ) { }
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Row of category chips
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    AssistChip(
                        onClick = { query = category },
                        label = { Text(category) }
                    )
                }
            }

            // "Учреждения" Section
            Text(
                text = "Учреждения",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                defaultOrganizationSuggestions.forEach { suggestion ->
                    SuggestionCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        suggestion = suggestion,
                        onClick = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Coming Soon...")  // FIX: Use snackbarHostState properly
                            }
                        }
                    )
                }
            }

            // "Руководство" Section
            Text(
                text = "Руководство",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                guides.forEach { guide ->  // Use actual guides instead of default suggestions
                    SuggestionCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        suggestion = guide.title,
                        onClick = {
                            onGuideClick(guide.id)  // Use the guide ID for navigation
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun SuggestionCard(
    modifier: Modifier = Modifier,
    suggestion: String,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = suggestion,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

