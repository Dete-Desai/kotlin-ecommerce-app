package com.codewithfk.alkamerce.di

import com.codewithfk.alkamerce.ui.feature.account.login.LoginViewModel
import com.codewithfk.alkamerce.ui.feature.account.register.RegisterViewModel
import com.codewithfk.alkamerce.ui.feature.cart.CartViewModel
import com.codewithfk.alkamerce.ui.feature.home.HomeViewModel
import com.codewithfk.alkamerce.ui.feature.orders.OrdersViewModel
import com.codewithfk.alkamerce.ui.feature.product_details.ProductDetailsViewModel
import com.codewithfk.alkamerce.ui.feature.summary.CartSummaryViewModel
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