package com.example.alkamerce.di

import com.example.alkamerce.ui.feature.account.login.LoginViewModel
import com.example.alkamerce.ui.feature.account.register.RegisterViewModel
import com.example.alkamerce.ui.feature.cart.CartViewModel
import com.example.alkamerce.ui.feature.home.HomeViewModel
import com.example.alkamerce.ui.feature.orders.OrdersViewModel
import com.example.alkamerce.ui.feature.product_details.ProductDetailsViewModel
import com.example.alkamerce.ui.feature.summary.CartSummaryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        ProductDetailsViewModel(get(),get())
    }
    viewModel {
        CartViewModel(get(), get(), get(),get())
    }
    viewModel {
        CartSummaryViewModel(get(), get(),get())
    }
    viewModel {
        OrdersViewModel(get(),get())
    }

    viewModel {
        LoginViewModel(get(),get())
    }

    viewModel {
        RegisterViewModel(get(),get())
    }
}