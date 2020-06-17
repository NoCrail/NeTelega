package com.home.netelega

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.home.netelega.adapters.MessagingAdapter
import com.home.netelega.network.Result
import com.home.netelega.network.UserService
import com.home.netelega.structures.Message
import kotlinx.android.synthetic.main.activity_messaging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessagingActivity : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        pref = getSharedPreferences(USER_SHARED_PREFERENCES, MODE_PRIVATE)
        val opponentName = getIntent().getStringExtra(OPPONENT_NAME)?:"slomalos name"
        val opponentId = getIntent().getIntExtra(OPPONENT_ID, 0).toString()
        val userToken = pref.getString(USER_TOKEN_PREFERENCES, "0")
        val userId = pref.getString(USER_ID_PREFERENCES, "0")?:"0"

        getMessages(userToken, opponentId, userId, opponentName)

        send_btn.setOnClickListener {
            val message = message_edit_text.text.toString()
            GlobalScope.launch(Dispatchers.IO) {
                val sendStatus = UserService.sendMessageTo(message,opponentId, userToken?:"")
                withContext(Dispatchers.Main){
                    if(sendStatus is Result.Success){
                        Toast.makeText(
                            baseContext,
                            sendStatus.value,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                getMessages(userToken, opponentId, userId, opponentName)
            }
        }
    }

    private fun getMessages(userToken: String?, opponentId: String, userId: String, opponentName:String){
        GlobalScope.launch(Dispatchers.IO) {
            val msgs = UserService.getDialogMessages(userToken?:"",opponentId,10)
            withContext(Dispatchers.Main){
                if(msgs is Result.Success){
                    recycler_msgs.layoutManager = LinearLayoutManager(baseContext)
                    recycler_msgs.adapter = MessagingAdapter(msgs.value, userId, opponentName)
                    (recycler_msgs.adapter as MessagingAdapter).notifyDataSetChanged()
                }
            }
        }
    }

}