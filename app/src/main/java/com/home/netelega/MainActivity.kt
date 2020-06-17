package com.home.netelega

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.home.netelega.adapters.DialogsAdapter
import com.home.netelega.network.Result
import com.home.netelega.network.UserService
import com.home.netelega.structures.Dialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), DialogsAdapter.ActionClick {

    private lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences(USER_SHARED_PREFERENCES, MODE_PRIVATE)

        GlobalScope.launch(Dispatchers.IO) {
            val dialogs = getDialogs(pref.getString(USER_TOKEN_PREFERENCES, "")?:"")
            withContext(Dispatchers.Main){
                if(dialogs is Result.Success){
                    recycler.layoutManager = LinearLayoutManager(baseContext)
                    recycler.adapter = DialogsAdapter(
                        dialogs.value,
                        this@MainActivity
                    )
                }
            }
        }





    }

    private fun getDialogs(user_token: String)=
        UserService.getDialogs(user_token)

    override fun onDialogClick(dialog: Dialog) {
        val mIntent = Intent(this, MessagingActivity::class.java)
        mIntent.putExtra(OPPONENT_NAME, dialog.name+" "+dialog.surname)
        mIntent.putExtra(OPPONENT_ID, dialog.userId)
        startActivity(mIntent)
    }

}
