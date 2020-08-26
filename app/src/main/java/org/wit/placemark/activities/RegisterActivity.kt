package org.wit.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.wit.placemark.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            Log.d("RegisterActivity", "Email is: " + email)
            Log.d("RegisterActivity", "Passwortd: $password")

            //Firebase Authentication to create a user name with email and password
        }

        already_have_account_text_view.setOnClickListener {
            Log.d("RegisterActivity", "Try to show login activity")

                //launch the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}