package com.example.data.di

import com.example.data.repository.CategoryRepositoryImpl
import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.repository.CartRepository
import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.OrderRepository
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<CartRepository> { com.example.data.repository.CartRepositoryImpl(get()) }
    single<OrderRepository> { com.example.data.repository.OrderRepositoryImpl(get()) }
    single<UserRepository> { com.example.data.repository.UserRepositoryImpl(get()) }
}