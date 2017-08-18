package com.example.elder.quizz.feature.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.elder.quizz.R
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import java.util.*


/**
 * Created by elder.santos on 15/08/2017.
 */
class HomeFragment : Fragment() {

    val questions : HashMap<String, Objects>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.home_fragment, container, false)



    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var questionsREF :DatabaseReference = FirebaseDatabase.getInstance().getReference("alternatives")


        questionsREF.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children) {

                  questions?.put(postSnapshot.key , postSnapshot.value as Objects)
                    print(questions)

//                    var texto = view?.findViewById<TextView>(R.id.hometext)
//                    texto?.text = postSnapshot.value.toString()


                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                System.out.println("The read failed: " + databaseError.getMessage())
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





