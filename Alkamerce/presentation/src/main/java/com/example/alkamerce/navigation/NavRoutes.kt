package com.codewithfk.alkamerce.navigation

import com.codewithfk.alkamerce.model.UiProductModel

@Serializable
object HomeScreen

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
object CartScreen

@Serializable
object OrdersScreen

@Serializable
object ProfileScreen

@Serializable
object CartSummaryScreen

@Serializable
data class ProductDetails(val product: UiProductModel)

@Serializable
data class UserAddressRoute(val userAddressWrapper: UserAddressRouteWrapper)