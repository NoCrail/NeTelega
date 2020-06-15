package com.home.netelega.network

import com.google.gson.Gson
import com.home.netelega.APP_TOKEN
import com.home.netelega.SERVER_PORT
import com.home.netelega.SERVER_ADDRESS
import com.home.netelega.structures.Account
import com.home.netelega.structures.Dialog
import com.home.netelega.structures.Message
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

object UserService {

    fun register(acc: Account): Result<String> {
        return apiCall {
            val request = APP_TOKEN + acc.toRegisterQueryString()
            serviceCall(request)
        }
    }

    fun login(acc: Account): Result<String> {
        return apiCall {
            val request = APP_TOKEN + acc.toLoginQueryString()
            serviceCall(request)
        }
    }

    fun getDialogs(userToken: String): Result<List<Dialog>>{
        val dialogString = apiCall {
            val request = "$APP_TOKEN?getDialogs?user_token=$userToken"
            serviceCall(request)
        }
        return if(dialogString is Result.Success){
            val dialogSplitString =  dialogString.value.split("dialogs=")[1]
            val dialogs = Gson().fromJson(dialogSplitString, Array<Dialog>::class.java)
            Result.Success(dialogs.toList())
        } else Result.Failure(java.lang.Exception())
    }

    fun sendMessageTo(msg:String, targetId: String, userToken: String): Result<String>{
        return apiCall {
            val request = "$APP_TOKEN?sendMsg?user_token=$userToken?target_user_id=$targetId?msg=$msg"
            serviceCall(request)
        }
    }

    fun getDialogMessages(userToken: String, targetId: String, count: Int): Result<List<Message>>{
        val messagesString = apiCall {
            val request = "$APP_TOKEN?getDialogMessages?user_token=$userToken?target_user_id=$targetId?count=$count"
            serviceCall(request)
        }
        return if(messagesString is Result.Success){
            val messagesSplitString =  messagesString.value.split("dialog=")[1]
            val messages = Gson().fromJson(messagesSplitString, Array<Message>::class.java)
            Result.Success(messages.toList())
        } else Result.Failure(java.lang.Exception())
    }

    private fun serviceCall(request: String) :String{
        return getSocket().run {
            val out = DataOutputStream(getOutputStream())
            out.writeUTF(request)
            out.flush()

            val inp = DataInputStream(getInputStream())
            val response = inp.readUTF()



            if (response.contains("error"))
                throw Exception(response) else {
                out.writeUTF("OK")
                out.flush()
            }

            out.close()
            inp.close()
            close()

            response
        }
    }

    private fun getSocket() = Socket(SERVER_ADDRESS, SERVER_PORT)


}