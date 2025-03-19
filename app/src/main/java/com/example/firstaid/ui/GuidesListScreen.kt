package com.example.firstaid.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SortByAlpha
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

enum class SortOrder {
    BY_TITLE,
    BY_ID
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidesListScreen(
    modifier: Modifier = Modifier,
    guides: List<Guide>,
    onGuideClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onClickSearchBar: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var sortOrder by remember { mutableStateOf(SortOrder.BY_TITLE) } // Состояние для сортировки
    val scrollState = rememberScrollState()

    // Фильтрация и сортировка списка
    val filteredGuides = guides
        .filter { guide ->
            guide.title.contains(query, ignoreCase = true)
        }
        .sortedWith(
            when (sortOrder) {
                SortOrder.BY_TITLE -> compareBy { it.title }
                SortOrder.BY_ID -> compareBy { it.id }
            }
        )

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    SearchBar(
                        query = query,
                        onQueryChange = { query = it },
                        onSearch = { /* Дополнительные действия при поиске */ },
                        active = false,
                        onActiveChange = { isActive ->
                            if (isActive) onClickSearchBar()
                        },
                        placeholder = { Text(text = stringResource(R.string.search_hint)) },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = onClickSearchBar) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Bar"
                                )
                            }
                        }
                    ) { }
                },
                actions = {
                    // Кнопка сортировки
                    IconButton(onClick = {
                        sortOrder = when (sortOrder) {
                            SortOrder.BY_TITLE -> SortOrder.BY_ID
                            SortOrder.BY_ID -> SortOrder.BY_TITLE
                        }
                    }) {
                        Icon(
                            imageVector = when (sortOrder) {
                                SortOrder.BY_TITLE -> Icons.Default.SortByAlpha
                                SortOrder.BY_ID -> Icons.Default.Numbers
                            },
                            contentDescription = "Sort"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.guides_list_screen_title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            filteredGuides.forEach { guide ->
                GuideCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    guide = guide,
                    onClick = { onGuideClick(guide.id) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
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
            text = guide.title,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
