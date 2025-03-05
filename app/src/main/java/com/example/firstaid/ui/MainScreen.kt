package com.example.firstaid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firstaid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onClickQuestionaireButton: () -> Unit,
    onClickLegalInfoButton: () -> Unit,
    onClickHospitalsButton: () -> Unit,
    onClickGuidesButton: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier.padding(bottom = 100.dp, top = 20.dp),
            query = "",
            onQueryChange = {},
            onSearch = {},
            active = false,
            onActiveChange = {}
        ) { }

        MainPageButtonsGroup(
            onClickQuestionaireButton = onClickQuestionaireButton,
            onClickLegalInfoButton = onClickLegalInfoButton,
            onClickHospitalsButton = onClickHospitalsButton,
            onClickGuidesButton = onClickGuidesButton
        )

    }
}


@Composable
fun MainPageButtonsGroup(
    modifier: Modifier = Modifier,
    onClickQuestionaireButton: () -> Unit,
    onClickLegalInfoButton: () -> Unit,
    onClickHospitalsButton: () -> Unit,
    onClickGuidesButton: () -> Unit
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        FilledTonalButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = onClickQuestionaireButton,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(R.string.questionnaire_button_title))
        }
        FilledTonalButton(
            onClick = onClickGuidesButton, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.guides_list_button_title))
        }
        FilledTonalButton(
            onClick = onClickHospitalsButton, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.hospitals_button_title))
        }
        FilledTonalButton(
            onClick = onClickLegalInfoButton, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.legal_info_button_title))
        }
    }
}