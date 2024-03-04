package com.osvaldo.newcheckoutarchpoc.presentation.composes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.osvaldo.newcheckoutarchpoc.presentation.composes.generic.ErrorView
import com.osvaldo.newcheckoutarchpoc.presentation.composes.generic.LoadingView
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.CondimentsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CondimentsComposable(viewModel: CondimentsViewModel = koinViewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        viewModel.state.collectAsState().value.run {
            when (this.viewData.componentState) {
                ComponentState.ERROR -> {
                    ErrorView()
                }

                ComponentState.LOADING -> {
                    LoadingView()
                }

                ComponentState.SUCCESS -> {
                    CondimentSuccess(
                        viewModel = viewModel,
                        condiment = viewData.condiment,
                        isDone = viewData.isDone
                    )
                }
            }
        }
    }
}

@Composable
fun CondimentSuccess(
    condiment: String, isDone: Boolean, viewModel: CondimentsViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Condiment: $condiment",
            color = Color.Black,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        )

        Text(text = "Is Done")
        Switch(checked = isDone, onCheckedChange = {
            viewModel.intent(CondimentsViewModel.ViewIntent.UpdateCondimentStatus(it))
        })

        Button(onClick = { viewModel.intent(CondimentsViewModel.ViewIntent.UpdateCondiments) }) {
            Text(text = "Update Condiments")
        }
    }
}
