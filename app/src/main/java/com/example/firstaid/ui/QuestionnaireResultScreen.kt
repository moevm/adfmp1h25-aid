package com.example.firstaid.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstaid.model.Guide

@Composable
fun QuestionnaireResultScreen(
    matchingGuides: List<Guide>,
    onBackClick: () -> Unit,
    onGuideClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Подходящие руководства",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        matchingGuides.forEach { guide ->
            GuideCard(
                guide = guide,
                onClick = { onGuideClick(guide.id) },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Назад")
        }
    }
}