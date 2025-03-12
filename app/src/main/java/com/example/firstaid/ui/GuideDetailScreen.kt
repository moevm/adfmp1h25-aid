package com.example.firstaid.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstaid.model.Guide
import com.example.firstaid.model.PageItem

@Composable
fun GuideDetailScreen(
    guide: Guide,
    onBackClick: () -> Unit,
    onAddToBookmarks: () -> Unit,
    onShareClick: () -> Unit
) {
    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Text(text = guide.title, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = guide.description, modifier = Modifier.padding(bottom = 16.dp))

        guide.steps.forEach { step ->
            Text(text = step.title, modifier = Modifier.padding(bottom = 8.dp))
            step.items.forEach { item ->
                when (item) {
                    is PageItem.TextItem -> Text(text = item.text)
                    is PageItem.ImageItem -> {} // Здесь можно добавить Image
                }
            }
        }

        Button(onClick = onBackClick) {
            Text(text = "Назад")
        }
        Button(onClick = onAddToBookmarks) {
            Text(text = "В избранное")
        }
        Button(onClick = onShareClick) {
            Text(text = "Поделиться")
        }
    }
}