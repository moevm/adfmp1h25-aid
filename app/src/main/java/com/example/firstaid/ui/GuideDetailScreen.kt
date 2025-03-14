package com.example.firstaid.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.firstaid.manager.BookmarkManager
import com.example.firstaid.model.Guide
import com.example.firstaid.model.PageItem

@Composable
fun GuideDetailScreen(
    context: Context,
    guide: Guide,
    onBackClick: () -> Unit,
    onBookmarkUpdate: () -> Unit,
    onShareClick: () -> Unit
) {
    val isBookmarked = remember { mutableStateOf(BookmarkManager.isBookmarked(context, guide.id)) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад"
                )
            }

            Text(
                text = guide.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            IconButton(
                onClick = {
                    BookmarkManager.toggleBookmark(context, guide.id)
                    isBookmarked.value = !isBookmarked.value
                    onBookmarkUpdate()
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (isBookmarked.value) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                    contentDescription = "Избранное",
                    tint = if (isBookmarked.value) Color.Red else MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(
                onClick = {

                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Руководство по первой помощи: ${guide.title}\n\n" +
                                    "${guide.description}\n\n" +
                                    "Шаги:\n${
                                        guide.steps.joinToString("\n") { step ->
                                            "${step.title}: ${
                                                step.items.filterIsInstance<PageItem.TextItem>()
                                                    .joinToString(" ") { it.text }
                                            }"
                                        }
                                    }"
                        )
                        type = "text/plain"
                    }
                    context.startActivity(
                        Intent.createChooser(
                            shareIntent,
                            "Поделиться руководством"
                        )
                    )
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Поделиться"
                )
            }

        }

        Text(
            text = guide.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        guide.steps.forEach { step ->
            Text(
                text = step.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            step.items.forEach { item ->
                when (item) {
                    is PageItem.TextItem -> {
                        Text(
                            text = item.text,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    is PageItem.ImageItem -> {
                        Image(
                            painter = painterResource(id = item.imageResId),
                            contentDescription = "Изображение шага",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}