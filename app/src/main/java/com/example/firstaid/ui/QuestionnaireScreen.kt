package com.example.firstaid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.example.firstaid.model.Guide
import com.example.firstaid.model.Question

@Composable
fun QuestionnaireScreen(
    questions: List<Question>,
    guides: List<Guide>,
    onFinish: (List<Guide>) -> Unit // Колбэк для завершения опросника
) {
    val currentQuestionIndex = remember { mutableStateOf(0) }
    val selectedTags = remember { mutableStateListOf<String>() }

    val currentQuestion = questions[currentQuestionIndex.value]

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Текст вопроса
        Text(
            text = currentQuestion.text,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Кнопки "Да" и "Нет"
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Button(onClick = {
                // При ответе "Да" добавляем тег
                selectedTags.add(currentQuestion.tag)
                goToNextQuestion(currentQuestionIndex, questions, selectedTags, guides, onFinish)
            }) {
                Text(text = "Да")
            }

            Button(onClick = {
                // При ответе "Нет" просто переходим к следующему вопросу
                goToNextQuestion(currentQuestionIndex, questions, selectedTags, guides, onFinish)
            }) {
                Text(text = "Нет")
            }
        }
    }
}

// Функция для перехода к следующему вопросу или завершения опросника
private fun goToNextQuestion(
    currentQuestionIndex: MutableState<Int>,
    questions: List<Question>,
    selectedTags: List<String>,
    guides: List<Guide>,
    onFinish: (List<Guide>) -> Unit
) {
    if (currentQuestionIndex.value < questions.size - 1) {
        currentQuestionIndex.value++
    } else {
        val matchingGuides = guides.filter { guide ->
            guide.tags.any { it in selectedTags }
        }
        onFinish(matchingGuides)
    }
}