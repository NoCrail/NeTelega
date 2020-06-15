package com.home.netelega.structures

import com.google.gson.annotations.SerializedName

data class Dialog(
    @SerializedName("user_id") val userId: String,
    val name: String,
    val surname: String
)