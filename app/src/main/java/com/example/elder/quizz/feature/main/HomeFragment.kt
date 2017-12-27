package com.example.elder.quizz.feature.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.elder.quizz.R
import com.example.elder.quizz.feature.question.AlternativesAdapter
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import model.Alternatives
import model.Awnsers
import model.Questions
import java.util.*
import kotlin.collections.HashMap
import android.widget.Toast
import android.view.Gravity
import android.widget.ImageView


/**
 * Created by elder.santos on 15/08/2017.
 */
class HomeFragment : Fragment() {
    internal lateinit var databaseAwnsers: DatabaseReference
     var questions: List<Awnsers> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.home_fragment, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        databaseAwnsers = FirebaseDatabase.getInstance().getReference("awnsers").child("-KrqSq0YFeouTeUQtVyb")
        databaseAwnsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (questions as ArrayList<Awnsers>).clear()

                for (postSnapshot in dataSnapshot.children) {
                    val question = postSnapshot.getValue<Awnsers>(Awnsers::class.java)
                    (questions as ArrayList<Awnsers>).add(question)
                }

                var texto = view?.findViewById<TextView>(R.id.hometext)
                texto?.text = questions[0].AlternativeId
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })


    }

     override fun onStart() {
        super.onStart()
    }
}





