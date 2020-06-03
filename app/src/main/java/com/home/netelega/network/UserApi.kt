package com.home.netelega.network

import com.home.netelega.structures.Account
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/")
    suspend fun register(@Body acc: Account): Response<String>
}