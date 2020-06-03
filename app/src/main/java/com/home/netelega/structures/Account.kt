package com.home.netelega.structures

data class Account(
    val name: String,
    val surname: String,
    val password: String,
    val phone: String
) {
    fun toQueryString() =
        "?register?name=$name&surname=$surname&password=$password&phone_num=$phone"
}