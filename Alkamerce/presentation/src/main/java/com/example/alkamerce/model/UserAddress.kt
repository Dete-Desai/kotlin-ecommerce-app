package com.codewithfk.alkamerce.model

import android.os.Parcelable
import com.codewithfk.domain.model.AddressDomainModel
import kotlinx.parcelize.Parcelize

@Serializable
@Parcelize
data class UserAddress(
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
) : Parcelable {
    override fun toString(): String {
        return "$addressLine, $city, $state, $postalCode, $country"
    }

    fun toAddressDataModel() = AddressDomainModel(
        addressLine,
        city,
        state,
        postalCode,
        country
    )
}