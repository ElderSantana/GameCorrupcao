package com.example.elder.quizz.feature.login

import com.example.elder.quizz.feature.main.MainActivity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.elder.quizz.R
import com.facebook.*
import kotlinx.android.synthetic.main.activity_login.*
import com.facebook.login.LoginResult
import android.widget.Toast
import com.google.firebase.auth.*

/**
 * Created by elder.santos on 15/08/2017.
 */

class LoginActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private var callbackManager: CallbackManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set the view now
        setContentView(R.layout.activity_login)


        //Get Firebase auth instance
        if (Profile.getCurrentProfile() != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        // facebook login
        callbackManager = CallbackManager.Factory.create()
        login_button.setReadPermissions("email", "public_profile", "user_friends", " publish_actions")
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(applicationContext, "Bem-vindo", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "onCancel", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext, "onError", Toast.LENGTH_SHORT).show()
            }
        })

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager?.onActivityResult(requestCode, resultCode, data)

    }

}
