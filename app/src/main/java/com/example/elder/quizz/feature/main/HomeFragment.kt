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
import model.Questions
import java.util.*
import kotlin.collections.HashMap


/**
 * Created by elder.santos on 15/08/2017.
 */
class HomeFragment : Fragment() {
    internal lateinit var databaseQuestions: DatabaseReference
    internal lateinit var questions: List<Questions>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.home_fragment, container, false)



    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = layoutManager

        databaseQuestions = FirebaseDatabase.getInstance().getReference("questions")

        databaseQuestions.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (questions as ArrayList<Questions>).clear()

                for (postSnapshot in dataSnapshot.children) {
                    val question = postSnapshot.getValue<Questions>(Questions::class.java)
                    (questions as ArrayList<Questions>).add(question)
                }

                var texto = view?.findViewById<TextView>(R.id.hometext)
                texto?.text = questions[0].questionTitle
//                recyclerView.adapter = AlternativesAdapter(context, alternatives as ArrayList<Alternatives>, intent.getStringExtra("QUESTION_ID")   )
//                val trackListAdapter = TrackList(this@QuestionActivity , alternatives as ArrayList<Alternatives>)
//                listViewTracks.setAdapter(trackListAdapter)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })




//        questionsREF.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val alternative = snapshot.getValue<Alternatives>(Alternatives::class.java)
////                    alternative.pergunta
//
//                for (postSnapshot in snapshot.children) {
//                    //Getting the data from snapshot
////                    val person = postSnapshot.getValue<String>(String::class.java)
//
////                    questions.put(postSnapshot)
//                    postSnapshot
////                    val alternative = postSnapshot.getValue<Alternatives>(Alternatives::class.java)
//                    val alternative = snapshot.getValue<Alternatives>(Alternatives::class.java)
////                    alternative.pergunta
//
//                    questions?.put(alternative)
//
////                    questions.add(person)
//
//
//                    //Displaying it on textview
//                    var texto = view?.findViewById<TextView>(R.id.hometext)
//                    texto?.text = "teste"
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                System.out.println("The read failed: " + databaseError.getMessage())
//            }
//        })

//        var questionsREF :DatabaseReference = FirebaseDatabase.getInstance().getReference("questions")
//
//        questionsREF.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (postSnapshot in snapshot.children) {
//                    //Getting the data from snapshot
//                    val person = postSnapshot.getValue<String>(String::class.java)
//
//                    questions.add(person)
//
//                    //Displaying it on textview
//                    var texto = view?.findViewById<TextView>(R.id.hometext)
//                    texto?.text = questions[0]
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                System.out.println("The read failed: " + databaseError.getMessage())
//            }
//        })



    }

     override fun onStart() {
        super.onStart()
    }
}





