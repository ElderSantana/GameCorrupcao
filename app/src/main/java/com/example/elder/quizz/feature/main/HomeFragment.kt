package com.example.elder.quizz.feature.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.elder.quizz.R
import com.google.firebase.database.*
import model.Questions
import com.google.firebase.database.DataSnapshot




/**
 * Created by elder.santos on 15/08/2017.
 */
class HomeFragment : Fragment() {

    var listaquestions: ArrayList<String>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.home_fragment, container, false)



    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mDatabase :DatabaseReference = FirebaseDatabase.getInstance().getReference("questions")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for (postSnapshot in dataSnapshot.children) {

                    listaquestions?.add(postSnapshot.toString())
                }

                var questions = dataSnapshot.getValue<Questions>(Questions::class.java)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        mDatabase.addValueEventListener(postListener)


        var texto = view?.findViewById<TextView>(R.id.hometext)
        texto?.text = listaquestions?.get(0)


    }

     override fun onStart() {
        super.onStart()




    }




}


