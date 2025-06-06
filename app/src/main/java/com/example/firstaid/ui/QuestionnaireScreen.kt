package com.example.firstaid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firstaid.data.Datasource
import com.example.firstaid.model.Guide
import com.example.firstaid.model.Question

@Composable
fun QuestionnaireScreen(
    questions: List<Question>,
    guides: List<Guide>,
    onFinish: (List<Guide>) -> Unit
) {
    val currentQuestionIndex = remember { mutableStateOf(0) }
    val selectedTags = remember { mutableStateListOf<String>() }

    val currentQuestion = questions[currentQuestionIndex.value]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // Центрируем по вертикали
        horizontalAlignment = Alignment.CenterHorizontally // Центрируем по горизонтали
    ) {
        // Текст вопроса
        Text(
            text = currentQuestion.text,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp) // Отступ снизу
        )

        // Кнопка "Да"
        OutlinedButton(
            onClick = {
                selectedTags.add(currentQuestion.tag)
                goToNextQuestion(currentQuestionIndex, questions, selectedTags, guides, onFinish)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Отступ между кнопками
            shape = RoundedCornerShape(8.dp) // Скругленные углы
        ) {
            Text(text = "Да", style = MaterialTheme.typography.bodyLarge)
        }

        // Кнопка "Нет"
        OutlinedButton(
            onClick = {
                goToNextQuestion(currentQuestionIndex, questions, selectedTags, guides, onFinish)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Нет", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

private fun goToNextQuestion(
    currentQuestionIndex: MutableState<Int>,
    questions: List<Question>,
    selectedTags: MutableList<String>,
    guides: List<Guide>,
    onFinish: (List<Guide>) -> Unit
) {
    if (currentQuestionIndex.value < questions.size - 1) {
        currentQuestionIndex.value++
    } else {
        val matchingGuides = Datasource.getMatchingGuides(selectedTags)
        onFinish(matchingGuides)
    }
}