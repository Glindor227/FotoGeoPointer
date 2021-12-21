package com.glindor.fotogeopointer.di

import com.glindor.fotogeopointer.data.DataRepository
import com.glindor.fotogeopointer.data.provider.FireBaseProvider
import com.glindor.fotogeopointer.data.provider.IDataProvider
import com.glindor.fotogeopointer.ui.listpoints.ListViewModel
import com.glindor.fotogeopointer.ui.point.PointViewModel
import com.glindor.fotogeopointer.ui.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModules = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<IDataProvider> { FireBaseProvider(get(), get()) }
    single { DataRepository(get()) }
}
 val listModules = module {
     viewModel { ListViewModel(get()) }
 }

val pointModules = module {
    viewModel { PointViewModel(get()) }
}
val splashModules = module {
    viewModel { SplashViewModel(get()) }
}