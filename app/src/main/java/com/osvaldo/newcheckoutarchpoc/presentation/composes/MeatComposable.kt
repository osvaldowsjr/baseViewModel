package com.osvaldo.newcheckoutarchpoc.presentation.composes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.osvaldo.newcheckoutarchpoc.core.abstractions.injectViewModelWithCollector
import com.osvaldo.newcheckoutarchpoc.presentation.composes.generic.ErrorView
import com.osvaldo.newcheckoutarchpoc.presentation.composes.generic.LoadingView
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.MeatViewModel

@Composable
fun MeatComposable(viewModel: MeatViewModel = injectViewModelWithCollector<MeatViewModel>()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        viewModel.state.collectAsState().value.run {
            when (this.meatViewData.componentState) {
                ComponentState.ERROR -> {
                    ErrorView()
                }

                ComponentState.LOADING -> {
                    LoadingView()
                }

                ComponentState.SUCCESS -> {
                    MeatSuccess(
                        viewModel = viewModel,
                        meat = meatViewData.meat,
                        meatIsGrilled = meatViewData.isGrilled,
                        isDone = meatViewData.isDone
                    )
                }
            }
        }
    }
}

@Composable
fun MeatSuccess(
    meat: String,
    meatIsGrilled: Boolean,
    isDone: Boolean,
    viewModel: MeatViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Meat: $meat and isGrilled: $meatIsGrilled",
            color = Color.Black,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        )

        Text(text = "Is Done")
        Switch(checked = isDone, onCheckedChange = {
            viewModel.intent(MeatViewModel.ViewIntent.UpdateMeatStatus(it))
        })
    }
}