package com.glindor.fotogeopointer

import android.app.Application
import com.glindor.fotogeopointer.di.appModules
import com.glindor.fotogeopointer.di.listModules
import com.glindor.fotogeopointer.di.pointModules
import com.glindor.fotogeopointer.di.splashModules
import com.glindor.fotogeopointer.utils.Logger
import org.koin.android.ext.android.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.d(this,"App  onCreate")
        startKoin(this, listOf(appModules,listModules,pointModules,splashModules))
        Logger.d(this,"App  startKoin - ок ")
    }
}