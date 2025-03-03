package com.codewithfk.shopper.navigation

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.codewithfk.shopper.model.UiProductModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.Base64


val productNavType = object : NavType<UiProductModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): UiProductModel? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, UiProductModel::class.java)
        } else {
            @Suppress("DEPRECATION") // Handle older SDK versions
            bundle.getParcelable(key) as? UiProductModel
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun parseValue(value: String): UiProductModel {
        // Decode the JSON string into a UiProductModel object
        val item = Json.decodeFromString<UiProductModel>(value)

        // Decode the Base64-encoded fields and URL-decode the image URL
        return item.copy(
            image = URLDecoder.decode(item.image, "UTF-8"),
            description = String(Base64.getDecoder().decode(item.description.replace("_", "/"))),
            title = String(Base64.getDecoder().decode(item.title.replace("_", "/")))
        )
    }val productNavType = object : NavType<UiProductModel>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): UiProductModel? {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                return bundle.getParcelable(key, UiProductModel::class.java)
            return bundle.getParcelable(key) as? UiProductModel
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun parseValue(value: String): UiProductModel {
            val item = Json.decodeFromString<UiProductModel>(value)

            return item.copy(
                image = URLDecoder.decode(item.image, "UTF-8"),
                description = String(Base64.getDecoder().decode(item.description.replace("_", "/"))),
                title = String(Base64.getDecoder().decode(item.title.replace("_", "/")))
            )
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun serializeAsValue(value: UiProductModel): String {
            // Encode the UiProductModel object into a JSON string
            return Json.encodeToString(
                value.copy(
                    image = URLEncoder.encode(value.image, "UTF-8"), // URL-encode the image URL
                    description = String(
                        Base64.getEncoder().encode(value.description.toByteArray())
                    ).replace("/", "_"), // Base64-encode and replace "/" with "_"
                    title = String(Base64.getEncoder().encode(value.title.toByteArray())).replace(
                        "/",
                        "_"
                    ) // Base64-encode and replace "/" with "_"
                )
            )
        }

        override fun put(bundle: Bundle, key: String, value: UiProductModel) {
            // Store the UiProductModel object in the Bundle
            bundle.putParcelable(key, value)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun serializeAsValue(value: UiProductModel): String {
        return Json.encodeToString(
            value.copy(
                image = URLEncoder.encode(value.image, "UTF-8"),
                description = String(
                    Base64.getEncoder().encode(value.description.toByteArray())
                ).replace("/", "_"),
                title = String(Base64.getEncoder().encode(value.title.toByteArray())).replace(
                    "/",
                    "_"
                )
            )
        )
    }

    override fun put(bundle: Bundle, key: String, value: UiProductModel) {
        bundle.putParcelable(key, value)
    }

}