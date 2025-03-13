package com.example.firstaid.ui

import android.content.Context
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firstaid.R
import com.example.firstaid.data.BookmarkManager
import com.example.firstaid.data.Datasource
import com.example.firstaid.model.Guide

@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    onBookmarkClick: (Int) -> Unit, // Теперь передаем ID руководства
    onBackClick: () -> Unit // Колбэк для возврата назад
) {
    val context = LocalContext.current
    val bookmarkedGuides = remember { mutableStateOf(emptyList<Guide>()) }

    // Загружаем избранные руководства при каждом открытии экрана
    LaunchedEffect(Unit) {
        val bookmarkedIds = BookmarkManager.getBookmarkedIds(context)
        bookmarkedGuides.value = Datasource.guidesList.filter { it.id in bookmarkedIds }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Верхняя строка с кнопкой "Назад" и заголовком
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
                text = stringResource(R.string.bookmarks_screen_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = modifier.weight(1f))
        }

        // Список избранных руководств
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            bookmarkedGuides.value.forEach { guide ->
                BookmarkCard(
                    modifier = Modifier.fillMaxWidth(),
                    guide = guide,
                    onClick = { onBookmarkClick(guide.id) } // Передаем ID руководства
                )
            }
        }
    }
}

@Composable
fun BookmarkCard(
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