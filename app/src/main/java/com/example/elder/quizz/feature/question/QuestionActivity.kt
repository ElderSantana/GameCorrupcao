package com.example.elder.quizz.feature.question

import android.text.TextUtils
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.*
import com.example.elder.quizz.R
import model.Alternatives


/**
 * Created by elder.santos on 18/08/2017.
 */

class QuestionActivity : AppCompatActivity() {

     lateinit var buttonAddTrack: Button
     lateinit var editTextTrackName: EditText
     lateinit var textViewArtist: TextView
     lateinit var listViewTracks: ListView

    internal lateinit var databaseAlternative: DatabaseReference

     var alternatives: List<Alternatives>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)


        /*
        * this line is important
        * this time we are not getting the reference of a direct node
        * but inside the node track we are creating a new child with the artist id
        * and inside that node we will store all the tracks with unique ids
        * */

        databaseAlternative = FirebaseDatabase.getInstance().getReference("alternatives").child(intent.getStringExtra("QUESTION_ID"))

        buttonAddTrack = findViewById<Button>(R.id.buttonAddTrack)
        editTextTrackName = findViewById<EditText>(R.id.editTextName)
        textViewArtist = findViewById<TextView>(R.id.textViewArtist)
        alternatives = ArrayList()

        textViewArtist.text = intent.getStringExtra("QUESTION_NAME")


        buttonAddTrack.setOnClickListener { saveTrack() }
    }

    override fun onStart() {
        super.onStart()

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        databaseAlternative.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (alternatives as ArrayList<Alternatives>).clear()

                for (postSnapshot in dataSnapshot.children) {
                    val alternative = postSnapshot.getValue<Alternatives>(Alternatives::class.java)
                    (alternatives as ArrayList<Alternatives>).add(alternative)
                }
                recyclerView.adapter = AlternativesAdapter(this@QuestionActivity, alternatives as ArrayList<Alternatives>, intent.getStringExtra("QUESTION_ID")   )
//                val trackListAdapter = TrackList(this@QuestionActivity , alternatives as ArrayList<Alternatives>)
//                listViewTracks.setAdapter(trackListAdapter)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun saveTrack() {
        val alternativesTlitle = editTextTrackName.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(alternativesTlitle)) {
            val id = databaseAlternative.push().key
            val track = Alternatives(id, alternativesTlitle)
            databaseAlternative.child(id).setValue(track)
            Toast.makeText(this, "Track saved", Toast.LENGTH_LONG).show()
            editTextTrackName.setText("")
        } else {
            Toast.makeText(this, "Please enter track name", Toast.LENGTH_LONG).show()
        }
    }
}
