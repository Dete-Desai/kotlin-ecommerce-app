package com.codewithfk.alkamerce

import android.app.Application
import com.codewithfk.data.di.dataModule
import com.codewithfk.domain.di.domainModule
import com.codewithfk.alkamerce.di.presentationModule
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