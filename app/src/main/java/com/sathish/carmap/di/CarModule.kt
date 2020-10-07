package com.sathish.carmap.di

import com.sathish.carmap.utils.Resource
import com.sathish.carmap.view.car.CarAdapter
import com.sathish.carmap.view.car.CarListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    single { Resource(context = androidContext()) }
    viewModel{ CarListViewModel(get(),get()) }
    factory { CarAdapter() }

}

