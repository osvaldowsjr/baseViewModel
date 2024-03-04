package com.osvaldo.newcheckoutarchpoc.core

import android.app.Application
import com.osvaldo.newcheckoutarchpoc.data.datasource.SandwichDatasource
import com.osvaldo.newcheckoutarchpoc.data.mapper.SandwichDtoMapper
import com.osvaldo.newcheckoutarchpoc.data.repository.SandwichRepository
import com.osvaldo.newcheckoutarchpoc.domain.mapper.SandwichModelMapper
import com.osvaldo.newcheckoutarchpoc.domain.useCase.CompletionUseCase
import com.osvaldo.newcheckoutarchpoc.domain.useCase.SandwichUseCase
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.BreadViewModel
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.CompletionViewModel
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.CondimentsViewModel
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.FishViewModel
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.MeatViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@MyApplication)
            // declare modules
            modules(appModule)
        }
    }

    private val appModule = module {
        single<CoroutineContextProvider> { CoroutineContextProvider.Default() }
        single { SandwichDatasource() }
        single { SandwichRepository(get(), get(), get()) }
        factory { SandwichDtoMapper() }

        single { SandwichUseCase(get(), get(), get()) }
        single { CompletionUseCase(get()) }
        factory { SandwichModelMapper() }


        viewModel { BreadViewModel(get()) }
        viewModel { CondimentsViewModel(get(), get()) }
        viewModel { MeatViewModel(get(), get()) }
        viewModel { FishViewModel(get(), get()) }
        viewModel { CompletionViewModel(get()) }
    }

}