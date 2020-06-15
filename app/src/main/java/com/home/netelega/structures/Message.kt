package com.home.netelega.structures

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("sender_id") val senderId: String,
    val msg: String
)