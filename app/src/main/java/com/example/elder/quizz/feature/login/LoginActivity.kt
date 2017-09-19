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
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.AccessToken
import com.google.firebase.FirebaseApp
import java.util.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FacebookAuthProvider
import model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount




/**
 * Created by elder.santos on 15/08/2017.
 */

class LoginActivity : AppCompatActivity() {

    private var inputPassword: EditText? = null
    private var auth: FirebaseAuth? = null
    private var firebase: FirebaseApp? = null
    private var progressBar: ProgressBar? = null
    private var callbackManager: CallbackManager? = null
    private val user: User? = null
    private val mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set the view now
        setContentView(R.layout.activity_login)

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        // facebook login
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                Toast.makeText(applicationContext, "succes", Toast.LENGTH_SHORT).show()
                accessFacebookLoginData(loginResult.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "canceled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        })




        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

        btn_signup?.setOnClickListener { startActivity(Intent(this@LoginActivity, SignupActivity::class.java)) }

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


    }


    fun sendLoginFacebookData(v:View) {
        LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email")
            )
    }

    private fun accessFacebookLoginData(accessToken: AccessToken?) {
        accessLoginData("facebook", accessToken!!.token)
    }

    private fun accessLoginData(provider: String, vararg tokens: String) {
        if (tokens != null
                && tokens.size > 0
                && tokens[0] != null) {

            var credential = FacebookAuthProvider.getCredential(tokens[0])
            credential = if (provider.equals("google", ignoreCase = true)) GoogleAuthProvider.getCredential(tokens[0], null) else credential

            user?.saveProviderSP( this, provider )
            mAuth?.signInWithCredential(credential)?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                        override fun onComplete(task: Task<AuthResult>) {

                            if (!task.isSuccessful) {
                                 Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
        } else {
            mAuth?.signOut()
        }
    }


}


