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
import com.osvaldo.newcheckoutarchpoc.presentation.viewModel.PriceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
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
        singleOf(::SandwichDatasource)
        singleOf(::SandwichRepository)
        factoryOf(::SandwichDtoMapper)
        singleOf(::SandwichUseCase)
        singleOf(::CompletionUseCase)
        factoryOf(::SandwichModelMapper)
        viewModelOf(::BreadViewModel)
        viewModelOf(::CondimentsViewModel)
        viewModelOf(::MeatViewModel)
        viewModelOf(::FishViewModel)
        viewModelOf(::CompletionViewModel)
        viewModelOf(::PriceViewModel)
    }

}