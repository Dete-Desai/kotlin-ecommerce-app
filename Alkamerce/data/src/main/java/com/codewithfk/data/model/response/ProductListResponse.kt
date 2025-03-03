package com.codewithfk.data.model.response

import com.codewithfk.data.model.DataProductModel
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    val products: List<DataProductModel>,
    val total: Int,
    val skip: Int,
    val limit: Int
) {
    fun toProductList() = com.codewithfk.domain.model.ProductListModel(
        products = products.map { it.toProduct() },
        total = total,
        skip = skip,
        limit = limit
    )
}