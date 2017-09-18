package com.example.elder.quizz.feature.login

import com.example.elder.quizz.feature.main.MainActivity
import android.content.Intent
import android.text.TextUtils
import com.example.elder.quizz.feature.signup.SignupActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.elder.quizz.R
import com.example.elder.quizz.feature.resetpassword.ResetPasswordActivity

import kotlinx.android.synthetic.main.activity_login.*
import android.view.ViewGroup
import android.view.LayoutInflater
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton




/**
 * Created by elder.santos on 15/08/2017.
 */

class LoginActivity : AppCompatActivity() {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var auth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    private var btnSignup: Button? = null
    private var btnLogin: Button? = null
    private var btnReset: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

//        login_button.setReadPermissions("email")
        var callbackManager = CallbackManager.Factory.create()

        // Callback registration
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })

        // set the view now
        setContentView(R.layout.activity_login)

        inputEmail = findViewById<EditText>(R.id.email)
        inputPassword = findViewById<EditText>(R.id.password)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        btnSignup = findViewById<Button>(R.id.btn_signup)
        btnLogin = findViewById<Button>(R.id.btn_login)
        btnReset = findViewById<Button>(R.id.btn_reset_password)

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

        btnSignup!!.setOnClickListener { startActivity(Intent(this@LoginActivity, SignupActivity::class.java)) }

        btnReset!!.setOnClickListener { startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java)) }

        btnLogin!!.setOnClickListener(View.OnClickListener {
            val email = inputEmail!!.text.toString()
            val password = inputPassword!!.text.toString()

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

}
