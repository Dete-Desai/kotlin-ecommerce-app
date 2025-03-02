package com.example.alkamerce.navigation

import com.example.alkamerce.model.UiProductModel
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
object HomeScreen

@kotlinx.serialization.Serializable
object LoginScreen

@kotlinx.serialization.Serializable
object RegisterScreen

@kotlinx.serialization.Serializable
object CartScreen

@kotlinx.serialization.Serializable
object OrdersScreen

@kotlinx.serialization.Serializable
object ProfileScreen

@kotlinx.serialization.Serializable
object CartSummaryScreen

@kotlinx.serialization.Serializable
data class ProductDetails(val product: UiProductModel)


data class UserAddressRoute(val userAddressWrapper: UserAddressRouteWrapper)