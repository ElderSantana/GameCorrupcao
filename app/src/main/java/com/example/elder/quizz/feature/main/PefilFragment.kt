package com.example.elder.quizz.feature.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.elder.quizz.R
import com.example.elder.quizz.feature.login.LoginActivity
import com.facebook.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.perfil_fragment.*
import com.facebook.GraphRequest
import com.facebook.AccessToken




/**
 * Created by elder.santos on 15/08/2017.
 */
class PefilFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.perfil_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Profile.getCurrentProfile() == null){
            startActivity(Intent(context, LoginActivity::class.java))
        }

        try {

            btn_sair.setOnClickListener{
                signout()
            }
            nome.text = Profile.getCurrentProfile().name
            Glide.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(78,78)).into(photo)

            photo.setOnClickListener{

                val params = Bundle()
                params.putInt("score", 1 )
                /* Replace {achievement-id} with the ID of your achievement. */
                GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/${Profile.getCurrentProfile().id}/scores",
                        params,
                        HttpMethod.POST,
                        GraphRequest.Callback { Toast.makeText(context, "calback", Toast.LENGTH_SHORT).show() /* Verify the achievement was published. */ }
                ).executeAsync()
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        }
        
    }

    private fun signout(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(context, LoginActivity::class.java))
    }


    private  fun achievement(){
        val params = Bundle()
        params.putString("achievement", "{achievement-1}")
        /* Replace {achievement-id} with the ID of your achievement. */
        GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/achievements",
                params,
                HttpMethod.POST,
                GraphRequest.Callback { /* Verify the achievement was published. */ }
        ).executeAsync()
    }


}

