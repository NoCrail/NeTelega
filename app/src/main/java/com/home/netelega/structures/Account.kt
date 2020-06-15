package com.home.netelega.structures

data class Account(
    val phone: String,
    val password: String,
    val name: String = "",
    val surname: String = ""
) {
    fun toRegisterQueryString() =
        "?register?name=$name&surname=$surname&password=$password&phone_num=$phone"

    fun toLoginQueryString() =
        "?login?phone_num=$phone&password=$password"
}