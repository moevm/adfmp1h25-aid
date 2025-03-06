package com.example.firstaid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firstaid.R

@Composable
fun DisclaimerScreen(
    modifier: Modifier = Modifier,
    onAgreeClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp),
            colors = CardColors(
                containerColor = colorResource(R.color.disclaimer_bg_color),
                contentColor = Color.Black,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified,
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.disclaimer_title),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )

                val disclaimerTextBlocks = stringArrayResource(R.array.disclaimer_text)
                disclaimerTextBlocks.forEach { block ->
                    Text(text = block, style = MaterialTheme.typography.bodyLarge)
                }

                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(R.string.disclaimer_comment),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic

                )
                OutlinedButton(
                    onClick = onAgreeClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = Color.Green,
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified,
                    )
                ) {
                    Text(text = stringResource(R.string.disclaimer_agree_button_title))
                }
            }


        }
    }
}


