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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.firstaid.model.LegalInfo

@Composable
fun LegalInformationScreen(
    modifier: Modifier = Modifier,
    legalInfoList: List<LegalInfo>,
    onBackClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

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
                text = stringResource(R.string.legal_info_screen_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = modifier.weight(1f))
        }



        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .verticalScroll(scrollState)
        )
        {
            legalInfoList.forEach { legalInfo ->
                LegalInfoCard(legalInfo = legalInfo)
            }
        }
    }
}


@Composable
fun LegalInfoCard(modifier: Modifier = Modifier, legalInfo: LegalInfo) {
    OutlinedCard(
        modifier = Modifier.padding(6.dp)
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = legalInfo.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(legalInfo.text, modifier = Modifier.padding(6.dp))
    }
}

