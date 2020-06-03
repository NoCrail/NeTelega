package com.home.netelega

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

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
    }
}
