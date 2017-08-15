package com.example.elder.quizz.feature.resetpassword

import android.widget.Toast
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import android.widget.ProgressBar
import android.widget.EditText
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.example.elder.quizz.R


/**
 * Created by elder.santos on 15/08/2017.
 */

class ResetPasswordActivity : AppCompatActivity() {

    private var inputEmail: EditText? = null
    private var btnReset: Button? = null
    private var btnBack: Button? = null
    private var auth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        inputEmail = findViewById<EditText>(R.id.email)
        btnReset = findViewById<Button>(R.id.btn_reset_password)
        btnBack = findViewById<Button>(R.id.btn_back)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        auth = FirebaseAuth.getInstance()

        btnBack!!.setOnClickListener { finish() }

        btnReset!!.setOnClickListener(View.OnClickListener {
            val email = inputEmail!!.text.toString().trim { it <= ' ' }

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(application, "Enter your registered email id", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            progressBar!!.visibility = View.VISIBLE
            auth!!.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@ResetPasswordActivity, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@ResetPasswordActivity, "Failed to send reset email!", Toast.LENGTH_SHORT).show()
                        }

                        progressBar!!.visibility = View.GONE
                    }
        })
    }

}