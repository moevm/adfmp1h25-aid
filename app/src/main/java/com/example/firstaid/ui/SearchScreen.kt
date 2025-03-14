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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.firstaid.R
import com.example.firstaid.model.Guide
import com.example.firstaid.model.Hospital
import com.example.firstaid.model.HospitalType
import androidx.compose.material3.AssistChipDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onInstitutionClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    guides: List<Guide>,
    hospitals: List<Hospital>,
    onGuideClick: (Int) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<HospitalType?>(null) }
    val scrollState = rememberScrollState()

    // Get category filters from HospitalType enum
    val categoryFilters = HospitalType.values().associate {
        it.value to setOf(it)
    }

    // Filter hospitals based on query and category
    val filteredHospitals = hospitals.filter { hospital ->
        val matchesQuery = hospital.name.contains(query, ignoreCase = true) ||
                hospital.address.contains(query, ignoreCase = true)

        val matchesCategory = selectedCategory?.let { category ->
            hospital.type == category
        } ?: true

        matchesQuery && matchesCategory
    }

    // Filter guides based on query
    val filteredGuides = guides.filter { guide ->
        guide.title.contains(query, ignoreCase = true) ||
                guide.description.contains(query, ignoreCase = true)
    }

    Scaffold(
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
                            IconButton(onClick = { /* Optional search action */ }) {
                                Icon(Icons.Default.Search, "Search")
                            }
                        }
                    ) {}
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
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
            // Category filter chips
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HospitalType.values().forEach { hospitalType ->
                    val isSelected = selectedCategory == hospitalType
                    AssistChip(
                        onClick = {
                            selectedCategory = if (isSelected) null else hospitalType
                        },
                        label = { Text(hospitalType.value) },
                        colors = if (isSelected) {
                            AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        } else {
                            AssistChipDefaults.assistChipColors()
                        }
                    )
                }
            }

            // Hospitals section
            if (filteredHospitals.isNotEmpty()) {
                Text(
                    text = "Учреждения",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                Column {
                    filteredHospitals.forEach { hospital ->
                        SuggestionCard(
                            suggestion = hospital.name,
                            onClick = { onInstitutionClick(hospital.id) }
                        )
                    }
                }
            }

            // Guides section
            if (filteredGuides.isNotEmpty()) {
                Text(
                    text = "Руководство",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                Column {
                    filteredGuides.forEach { guide ->
                        SuggestionCard(
                            suggestion = guide.title,
                            onClick = { onGuideClick(guide.id) }
                        )
                    }
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
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        onClick = onClick
    ) {
        Text(
            text = suggestion,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}