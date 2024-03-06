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
import com.osvaldo.newcheckoutarchpoc.core.abstractions.injectCollector
import com.osvaldo.newcheckoutarchpoc.presentation.composes.generic.ErrorView
import com.osvaldo.newcheckoutarchpoc.presentation.composes.generic.LoadingView
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.FishViewModel

@Composable
fun FishComposable(viewModel: FishViewModel = injectCollector<FishViewModel>()) {
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
                    FishSuccess(
                        viewModel = viewModel,
                        fish = viewData.fish,
                        fishIsGrilled = viewData.isGrilled,
                        isFromSea = viewData.isFromSea,
                        isDone = viewData.isDone
                    )
                }
            }
        }
    }
}

@Composable
fun FishSuccess(
    fish: String,
    fishIsGrilled: Boolean,
    isFromSea: Boolean,
    isDone: Boolean,
    viewModel: FishViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Fish: $fish and isGrilled: $fishIsGrilled",
            color = Color.Black,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        )

        Text(text = "Is Done")
        Switch(checked = isDone, onCheckedChange = {
            viewModel.intent(FishViewModel.ViewIntent.UpdateFishStatus(it))
        })

        Text(text = "Update is from sea:")
        Switch(checked = isFromSea, onCheckedChange = {
            viewModel.intent(FishViewModel.ViewIntent.UpdateIsFromSea(it))
        })
    }
}