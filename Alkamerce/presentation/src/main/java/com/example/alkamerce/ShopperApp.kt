package com.example.alkamerce

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.alkamerce.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AlkamerceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AlkamerceApp)
            modules(listOf(
                presentationModule,
                dataModule,
                domainModule
            ))
        }
    }
}