package com.osvaldo.newcheckoutarchpoc.presentation.composes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.PriceViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PriceComposable(viewModel: PriceViewModel = koinViewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        viewModel.state.collectAsState().value.run {
            when (this.componentState) {
                ComponentState.ERROR -> {
                    ErrorView()
                }

                ComponentState.LOADING -> {
                    LoadingView()
                }

                ComponentState.SUCCESS -> {
                    PriceSuccess(
                        price
                    )
                }
            }
        }
    }
}

@Composable
fun PriceSuccess(
    price: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Price: $price",
            fontSize = TextUnit(20f, TextUnitType.Sp),
            color = Color.Black
        )

    }
}

