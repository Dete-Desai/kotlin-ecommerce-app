package com.codewithfk.alkamerce.navigation

import android.os.Parcelable
import com.codewithfk.alkamerce.model.UserAddress
import kotlinx.parcelize.Parcelize

@Serializable
@Parcelize
data class UserAddressRouteWrapper(
    val userAddress: UserAddress?
) : Parcelable
