package com.codewithfk.domain.model

data class ProductListModel(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)