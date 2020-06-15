package com.home.netelega

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.KartonDCP.SDK.SSLClient
import com.KartonDCP.SDK.Status.RegStat
import com.home.netelega.network.Result
import com.home.netelega.network.UserService
import com.home.netelega.structures.Dialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.net.InetAddress
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory


class MainActivity : AppCompatActivity(), Adapter.ActionClick {

    private lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences(USER_SHARED_PREFERENCES, MODE_PRIVATE)

//        GlobalScope.launch(Dispatchers.IO) {
//            val dialogs = getDialogs(pref.getString(USER_TOKEN_PREFERENCES, "")?:"")
//            withContext(Dispatchers.Main){
//                if(dialogs is Result.Success){
//                    recycler.layoutManager = LinearLayoutManager(baseContext)
//                    recycler.adapter = Adapter(dialogs.value, this@MainActivity)
//                }
//            }
//        }

//        GlobalScope.launch(Dispatchers.IO) {
//            val sendStatus = UserService.sendMessageTo("keklolarbidol","000", pref.getString(USER_TOKEN_PREFERENCES, "")?:"")
//            withContext(Dispatchers.Main){
//                if(sendStatus is Result.Success){
//                    Toast.makeText(
//                        baseContext,
//                        sendStatus.value,
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//        }

        GlobalScope.launch(Dispatchers.IO) {
            val msgs = UserService.getDialogMessages(pref.getString(USER_TOKEN_PREFERENCES, "")?:"","00",2)
            val kek = msgs
        }

    }

    private fun getDialogs(user_token: String)=
        UserService.getDialogs(user_token)

    override fun onDialogClick(dialog: Dialog) {
        TODO("Not yet implemented")
    }

}
