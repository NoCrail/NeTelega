package com.home.netelega

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.home.netelega.network.Result
import com.home.netelega.network.UserService
import com.home.netelega.structures.Account
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.login_edit_text
import kotlinx.android.synthetic.main.activity_login.password_edit_text
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        pref = getSharedPreferences(USER_SHARED_PREFERENCES, MODE_PRIVATE)
        if (pref.contains(USER_ID_PREFERENCES)) startActivity(
            Intent(
                this,
                MainActivity::class.java
            )
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        registration_activity_btn.setOnClickListener {
            startActivity(Intent(
                this,
                RegistrationActivity::class.java
            ))
        }

        login_btn.setOnClickListener {
            if(isDataCorrect()){
                GlobalScope.launch(Dispatchers.IO) {
                    val res = loginUser(collectUserData())
                    if (res is Result.Success) {
                        val token = res.value.split("&")[0].split("=")[1]
                        val id = res.value.split("&")[1].split("=")[1]
                        pref.edit().putString(USER_ID_PREFERENCES, id).putString(USER_TOKEN_PREFERENCES, token).apply()
                        withContext(Dispatchers.Main){
                            Toast.makeText(
                                baseContext,
                                getString(R.string.seccess_user_login_msg),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        startActivity(Intent(baseContext, MainActivity::class.java))
                        finish()
                    } else {
                        showError(getString(R.string.server_error_msg) + " " + res)
                    }
                }

            }
        }


    }

    private fun loginUser(acc: Account): Result<String> {
        return UserService.login(acc)
    }

    private fun collectUserData(): Account{
        val password = password_edit_text.text.toString()
        val phone = login_edit_text.text.toString()
        return Account(phone, password)
    }

    private fun showError(message: String) {
        val snackbar = Snackbar.make(
            rootLayout,
            message,
            Snackbar.LENGTH_LONG
        )
        snackbar.view.setBackgroundColor(getColor(android.R.color.holo_red_light))
        snackbar.show()
    }

    private fun isDataCorrect() = !login_edit_text.text.isNullOrBlank() && !password_edit_text.text.isNullOrBlank()
}
