package com.example.elder.quizz.feature.question

import android.text.TextUtils
import com.google.firebase.database.FirebaseDatabase
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.*
import com.example.elder.quizz.R
import model.Questions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


/**
 * Created by elder.santos on 18/08/2017.
 */

class NewsActivity : AppCompatActivity() {

    //view objects
    var editTextName: EditText? = null
    var editTextFont: EditText? = null
    var spinnerYear: Spinner? = null
    var listViewQuestions: ListView? = null

    //a list to store all the artist from firebase database
    var questions: List<Questions>? = null

    //our database reference object
    var databaseQuestions: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        //getting the reference of questions node
         databaseQuestions = FirebaseDatabase.getInstance().getReference("news")

        //getting views
        editTextName = findViewById<EditText>(R.id.editTextName)
        editTextFont = findViewById<EditText>(R.id.editTextFont)
        spinnerYear = findViewById<Spinner>(R.id.spinnerYear)
//        listViewQuestions = findViewById<ListView>(R.id.listViewQuestions)

        val buttonAddArtist = findViewById<Button>(R.id.buttonAddQuestion)

        //list to store artists
        questions = ArrayList()


        //adding an onclicklistener to button
        buttonAddArtist.setOnClickListener({
            addArtist()
        })

    }

    override fun onStart() {
        super.onStart()

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        databaseQuestions?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //clearing the previous artist list
                (questions as ArrayList<Questions>).clear()

                //iterating through all the nodes
                for (postSnapshot in dataSnapshot.children) {
                    //getting artist
                    val question = postSnapshot.getValue<Questions>(Questions::class.java)
                    //adding artist to the list
                    (questions as ArrayList<Questions>).add(question)
                }

                //attaching adapter to the recycleview
                recyclerView.adapter = QuestionsAdapter(this@NewsActivity, questions as ArrayList<Questions>  )

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }
    /*
    * This method is saving a new artist to the
    * Firebase Realtime Database
    * */
    private fun addArtist() {
        //getting the values to save
        val name = editTextName?.text.toString().trim { it <= ' ' }
        val font = editTextFont?.text.toString().trim { it <= ' ' }
        val year = spinnerYear?.selectedItem.toString()

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            val id = databaseQuestions!!.push().key

            //creating an Artist Object
            val artist = Questions(id, name, font, year )

            //Saving the Artist
            databaseQuestions!!.child(id).setValue(artist)

            //setting edittext to blank again
            editTextName?.setText("")

            //displaying a success toast
            Toast.makeText(this, "Question added", Toast.LENGTH_LONG).show()
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a questionTitle", Toast.LENGTH_LONG).show()
        }
    }

}