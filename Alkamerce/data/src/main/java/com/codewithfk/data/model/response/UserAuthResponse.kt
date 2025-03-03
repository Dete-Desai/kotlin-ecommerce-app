package com.codewithfk.data.model.response

import com.codewithfk.domain.model.UserDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class UserAuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String
){
    fun toDomainModel() = UserDomainModel(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        image = image,
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}