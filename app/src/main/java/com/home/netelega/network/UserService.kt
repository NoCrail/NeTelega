package com.home.netelega.network

import com.home.netelega.APP_TOKEN
import com.home.netelega.SERVER_PORT
import com.home.netelega.SERVER_ADDRESS
import com.home.netelega.structures.Account
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket
import java.nio.CharBuffer
import java.nio.charset.Charset

object UserService {

    fun register(acc: Account): Result<String> {
        return apiCall {
            val request = APP_TOKEN + acc.toQueryString()

            getSocket().run {
                val out = DataOutputStream(getOutputStream())
                out.write(request.toByteArray())
                out.flush()

                val inp = DataInputStream(getInputStream())
                val bb = ByteArray(8192)
                inp.read(bb)
                val response = String(bb, Charsets.UTF_8).trim()
                out.close()
                inp.close()
                close()

                if (response.contains("error"))
                    throw Exception(response)

                response
            }
        }
    }

    private fun getSocket() = Socket(SERVER_ADDRESS, SERVER_PORT)
}