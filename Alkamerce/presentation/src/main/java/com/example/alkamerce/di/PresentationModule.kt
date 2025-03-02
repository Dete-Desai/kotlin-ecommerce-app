package com.example.alkamerce.di

import com.example.alkamerce.AlkamerceSession
import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModule)
    single { AlkamerceSession(get()) }
}