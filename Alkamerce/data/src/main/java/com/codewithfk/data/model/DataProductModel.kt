package com.codewithfk.data.model

import kotlinx.serialization.Serializable
import com.codewithfk.domain.model.Product
import com.codewithfk.domain.model.Dimensions as DomainDimensions
import com.codewithfk.domain.model.Review as DomainReview
import com.codewithfk.domain.model.Meta as DomainMeta

@Serializable
data class DataProductModel(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val tags: List<String>,
    val brand: String,
    val sku: String,
    val weight: Int,
    val dimensions: Dimensions,
    val warrantyInformation: String,
    val shippingInformation: String,
    val availabilityStatus: String,
    val reviews: List<Review>,
    val returnPolicy: String,
    val minimumOrderQuantity: Int,
    val meta: Meta,
    val thumbnail: String,
    val images: List<String>
) {
    fun toProduct() = Product(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        tags = tags,
        brand = brand,
        sku = sku,
        weight = weight,
        dimensions = DomainDimensions(
            width = dimensions.width,
            height = dimensions.height,
            depth = dimensions.depth
        ),
        warrantyInformation = warrantyInformation,
        shippingInformation = shippingInformation,
        availabilityStatus = availabilityStatus,
        reviews = reviews.map { review ->
            DomainReview(
                rating = review.rating,
                comment = review.comment,
                date = review.date,
                reviewerName = review.reviewerName,
                reviewerEmail = review.reviewerEmail
            )
        },
        returnPolicy = returnPolicy,
        minimumOrderQuantity = minimumOrderQuantity,
        meta = DomainMeta(
            createdAt = meta.createdAt,
            updatedAt = meta.updatedAt,
            barcode = meta.barcode,
            qrCode = meta.qrCode
        ),
        thumbnail = thumbnail,
        images = images
    )
}

@Serializable
data class Dimensions(
    val width: Double,
    val height: Double,
    val depth: Double
)

@Serializable
data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
)

@Serializable
data class Meta(
    val createdAt: String,
    val updatedAt: String,
    val barcode: String,
    val qrCode: String
)