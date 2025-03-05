package com.example.firstaid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firstaid.R
import com.example.firstaid.model.Guide

@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    onBookmarkClick: () -> Unit,
    bookmarks: List<Guide>
) {

    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = modifier.padding(top = 40.dp),
            text = stringResource(R.string.bookmarks_screen_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )


        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .verticalScroll(scrollState)
        )
        {
            bookmarks.forEach { bookmark ->
                BookmarkCard(Modifier.fillMaxWidth(), onClick = onBookmarkClick, bookmark = bookmark)
            }
        }
    }
}


@Composable
fun BookmarkCard(modifier: Modifier = Modifier, onClick: () -> Unit, bookmark: Guide) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = bookmark.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

