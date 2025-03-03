package com.codewithfk.shopper.model

import android.os.Parcelable
import com.codewithfk.domain.model.Product
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UiProductModel(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val image: String, // Use the first image from the `images` list
    val category: String,
    val rating: Double,
    val stock: Int,
    val brand: String
) : Parcelable {
    companion object {
        fun fromProduct(product: Product) = UiProductModel(
            id = product.id,
            title = product.title,
            price = product.price,
            description = product.description,
            image = product.images.firstOrNull() ?: "", // Use the first image or an empty string
            category = product.category,
            rating = product.rating,
            stock = product.stock,
            brand = product.brand
        )
    }
}
