package com.example.firstaid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firstaid.R
import com.example.firstaid.model.Guide

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidesListScreen(
    modifier: Modifier = Modifier,
    guides: List<Guide>,
    onGuideClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val searchQuery = remember { mutableStateOf("") }
    val filteredGuides = remember(searchQuery.value) {
        guides.filter { guide ->
            guide.title.contains(searchQuery.value, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = onBackClick
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, Modifier.size(48.dp))
            }
            Text(
                modifier = Modifier.weight(4f),
                text = stringResource(R.string.guides_list_screen_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = modifier.weight(1f))
        }

        SearchBar(
            query = searchQuery.value,
            onQueryChange = { searchQuery.value = it },
            onSearch = {},
            active = false,
            onActiveChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Поиск по названию") }
        ) {}

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .verticalScroll(scrollState)
        ) {
            if (filteredGuides.isEmpty()) {
                Text(
                    text = "Ничего не найдено",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                filteredGuides.forEach { guide ->
                    GuideCard(
                        modifier = Modifier.fillMaxWidth(),
                        guide = guide,
                        onClick = { onGuideClick(guide.id) }
                    )
                }
            }
        }
    }
}
@Composable
fun GuideCard(
    modifier: Modifier = Modifier,
    guide: Guide,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = guide.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}