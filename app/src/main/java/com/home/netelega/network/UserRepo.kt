package com.home.netelega.network

import com.home.netelega.structures.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepo(private val api: UserApi) {
    suspend fun register(acc: Account): String? = withContext(Dispatchers.IO){
        val a = api.register(acc)
        return@withContext a.body()
    }


}