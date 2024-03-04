package com.osvaldo.newcheckoutarchpoc.presentation.composes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.osvaldo.newcheckoutarchpoc.presentation.composes.generic.ErrorView
import com.osvaldo.newcheckoutarchpoc.presentation.composes.generic.LoadingView
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.BreadViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BreadComposable(viewModel: BreadViewModel = koinViewModel()) {
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
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
                    BreadSuccess(
                        viewData.bread,
                        viewData.isGlutenFree,
                        viewData.isVegan,
                        viewModel = viewModel
                    )
                }
            }
        }
    }

}


@Composable
fun BreadSuccess(
    bread: String,
    isGlutenFree: Boolean,
    isVegan: Boolean,
    viewModel: BreadViewModel
) {
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Success bread: $bread",
            color = Color.Black,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        )

        Text(
            text = "Gluten Free: $isGlutenFree",
            color = Color.Black,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        )

        Text(
            text = "Vegan: $isVegan",
            color = Color.Black,
            fontSize = TextUnit(20f, TextUnitType.Sp)
        )
    }
    Button(onClick = { viewModel.intent(BreadViewModel.ViewIntent.UpdateBread) }) {
        Text(text = "Update Bread")
    }
}
