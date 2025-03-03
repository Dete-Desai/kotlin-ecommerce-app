package com.codewithfk.shopper

import android.app.Application
import android.content.Context
import com.codewithfk.domain.model.UserDomainModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShopperSession(private val context: Context) {

    fun storeUser(user: UserDomainModel) {
        val sharedPref = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("id", user.id!!)
            putString("username", user.username)
            putString("email", user.email)
            putString("firstName", user.firstName)
            putString("lastName", user.lastName)
            putString("gender", user.gender)
            putString("image", user.image)
            putString("accessToken", user.accessToken)
            putString("refreshToken", user.refreshToken)
            apply()
        }
    }

    fun getUser(): UserDomainModel? {
        val sharedPref = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val id = sharedPref.getInt("id", 0)
        val username = sharedPref.getString("username", null)
        val email = sharedPref.getString("email", null)
        val firstName = sharedPref.getString("firstName", null)
        val lastName = sharedPref.getString("lastName", null)
        val gender = sharedPref.getString("gender", null)
        val image = sharedPref.getString("image", null)
        val accessToken = sharedPref.getString("accessToken", null)
        val refreshToken = sharedPref.getString("refreshToken", null)
        return if (id != 0 && username != null && email != null && firstName != null && lastName != null && gender != null && image != null && accessToken != null && refreshToken != null) {
            UserDomainModel(id, username, email, firstName, lastName, gender, image, accessToken, refreshToken )
        } else {
            null
        }
    }
}