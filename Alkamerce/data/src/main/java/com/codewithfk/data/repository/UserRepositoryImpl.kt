package com.codewithfk.data.repository

import com.codewithfk.domain.network.NetworkService
import com.codewithfk.domain.repository.UserRepository

class UserRepositoryImpl(private val networkService: NetworkService) : UserRepository {
    override suspend fun register(email: String, password: String, name: String) =
        networkService.register(email, password, name)

    override suspend fun login(username: String, password: String) =
        networkService.login(username, password)
}