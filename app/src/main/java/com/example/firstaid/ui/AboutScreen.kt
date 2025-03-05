package com.example.firstaid.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstaid.R

@Composable
@Preview(showBackground = true)
fun AboutScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {

        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = stringResource(R.string.about_screen_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            modifier = Modifier.padding(vertical = 40.dp, horizontal = 10.dp),
            text = stringResource(R.string.about_screen_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )


        AuthorsSection(
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}

@Composable
fun AuthorsSection(modifier: Modifier = Modifier) {
    Text(
        modifier = Modifier.wrapContentSize(unbounded = true),
        text = stringResource(R.string.about_screen_authors),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
    )

    stringArrayResource(R.array.authors).forEach { author ->
        Text(
            text = author,
            modifier = Modifier.padding(3.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

