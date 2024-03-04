package com.osvaldo.newcheckoutarchpoc.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.osvaldo.newcheckoutarchpoc.core.ui.theme.NewCheckoutArchPocTheme
import com.osvaldo.newcheckoutarchpoc.presentation.composes.BreadComposable
import com.osvaldo.newcheckoutarchpoc.presentation.composes.CondimentsComposable
import com.osvaldo.newcheckoutarchpoc.presentation.composes.FishComposable
import com.osvaldo.newcheckoutarchpoc.presentation.composes.MeatComposable
import com.osvaldo.newcheckoutarchpoc.presentation.composes.PriceComposable
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.CompletionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val completionViewModel: CompletionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewCheckoutArchPocTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                BreadComposable()
                            }
                        }
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                CondimentsComposable()
                            }
                        }
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                MeatComposable()
                            }
                        }
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                FishComposable()
                            }
                        }
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                PriceComposable()
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.padding(15.dp))
                        }
                    }

                    completionViewModel.state.collectAsState().value.let { state ->
                        Button(
                            onClick = { completionViewModel.intent(CompletionViewModel.ViewIntent.GoToNextScreen) },
                            enabled = state.buttonEnabled,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "Next")
                        }
                    }

                    LaunchedEffect(key1 = Unit) {
                        completionViewModel.viewEffect.collect {
                            when (it) {
                                CompletionViewModel.ViewEffect.GoToNextScreen -> {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Go to next screen",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                CompletionViewModel.ViewEffect.ShowError -> {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Peixe não está pronto!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}