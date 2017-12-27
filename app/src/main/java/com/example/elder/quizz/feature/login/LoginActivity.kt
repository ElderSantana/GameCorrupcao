package com.example.elder.quizz.feature.login

import com.example.elder.quizz.feature.main.MainActivity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.example.elder.quizz.R
import com.facebook.*
import kotlinx.android.synthetic.main.activity_login.*
import com.facebook.login.LoginResult
import android.widget.Toast
import com.example.elder.quizz.feature.resetpassword.ResetPasswordActivity
import com.example.elder.quizz.feature.signup.SignupActivity
import com.google.firebase.auth.*

/**
 * Created by elder.santos on 15/08/2017.
 */

class LoginActivity : AppCompatActivity() {

    private var inputPassword: EditText? = null
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
        login_button.setReadPermissions("email", "public_profile", "user_friends")
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


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

        btn_signup?.setOnClickListener { startActivity(Intent(this@LoginActivity, SignupActivity::class.java)) }
//
        btn_reset_password?.setOnClickListener { startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java)) }

        btn_login?.setOnClickListener(View.OnClickListener {
            val email = email?.text.toString()
            val password = password?.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            progressBar!!.visibility = View.VISIBLE


            //authenticate user
            auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar!!.visibility = View.GONE
                        if (!task.isSuccessful) {
                            // there was an error
                            if (password.length < 6) {
                                inputPassword!!.error = getString(R.string.minimum_password)
                            } else {
                                Toast.makeText(this@LoginActivity, getString(R.string.auth_failed), Toast.LENGTH_LONG).show()
                            }
                        } else {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager?.onActivityResult(requestCode, resultCode, data)

    }

}
