package com.home.netelega

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.home.netelega.network.Result
import com.home.netelega.network.UserService
import com.home.netelega.structures.Account
import kotlinx.android.synthetic.main.activity_login.login_edit_text
import kotlinx.android.synthetic.main.activity_login.login_field_box
import kotlinx.android.synthetic.main.activity_login.password_edit_text
import kotlinx.android.synthetic.main.activity_login.password_field_box
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initEditFields()

        register_user_btn.setOnClickListener {
            if (!isDataCorrect()) {
                showError(getString(R.string.incorrect_data_error_msg))

                return@setOnClickListener
            }
            GlobalScope.launch(Dispatchers.IO) {
            val res = registerUser(collectUserData())
            if (res is Result.Success) {
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        baseContext,
                        getString(R.string.success_account_creation_msg),
                        Toast.LENGTH_LONG
                    ).show()
                }
                startActivity(Intent(baseContext, LoginActivity::class.java))
                finish()
            } else {
                showError(getString(R.string.server_error_msg) + " " + res)
            }
        }
        }
    }

    private fun showError(message: String) {
        val snackbar = Snackbar.make(
            linearlayout,
            message,
            Snackbar.LENGTH_LONG
        )
        snackbar.view.setBackgroundColor(getColor(android.R.color.holo_red_light))
        snackbar.show()
    }

    private fun initEditFields() {
        login_edit_text.doAfterTextChanged {
            login_field_box.error =
                if (it.isNullOrBlank()) getString(R.string.empty_field_msg) else null
        }

        name_edit_text.doAfterTextChanged {
            name_field_box.error =
                if (it.isNullOrBlank()) getString(R.string.empty_field_msg) else null
        }

        surname_edit_text.doAfterTextChanged {
            surname_field_box.error =
                if (it.isNullOrBlank()) getString(R.string.empty_field_msg) else null
        }

        password_edit_text.doAfterTextChanged {
            when {
                it.isNullOrBlank() -> password_field_box.error =
                    getString(R.string.empty_field_msg)
                it.length <= 3 -> {
                    password_field_box.error = getString(R.string.short_password_msg)
                }
                else -> password_field_box.error = null
            }
            if (password_edit_text.text.toString() != repeat_password_edit_text.text.toString()) {
                repeat_password_field_box.error = getString(R.string.passwords_do_not_match)
            } else repeat_password_field_box.error = null
        }

        repeat_password_edit_text.doAfterTextChanged {
            if (password_edit_text.text.toString() != repeat_password_edit_text.text.toString()) {
                repeat_password_field_box.error = getString(R.string.passwords_do_not_match)
            } else repeat_password_field_box.error = null
        }
    }

    private fun isDataCorrect() = !login_edit_text.text.isNullOrBlank() && !name_edit_text.text.isNullOrBlank()
            && !surname_edit_text.text.isNullOrBlank() && password_field_box.error == null
            && repeat_password_field_box.error == null && !password_edit_text.text.isNullOrBlank() && !repeat_password_edit_text.text.isNullOrBlank()

    private fun collectUserData(): Account {
        val name = name_edit_text.text.toString()
        val surname = surname_edit_text.text.toString()
        val password = password_edit_text.text.toString()
        val phone = login_edit_text.text.toString()
        return Account(name, surname, password, phone)
    }

    private fun registerUser(acc: Account): Result<String> {
        return UserService.register(acc)
    }
}
