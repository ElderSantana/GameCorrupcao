package com.example.elder.quizz.feature.game

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.elder.quizz.R
import java.util.*
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_game.*
import model.Alternatives
import model.Awnsers
import model.Questions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference


class GameActivity : AppCompatActivity() {

    var questions: ArrayList<Questions>? = null
    var alternatives: List<Alternatives>? = null
    var awnsers: List<Awnsers>? = ArrayList()
    var idalternative : String = ""

    var DatabaseReference: DatabaseReference? = null
    var QuestionReference: DatabaseReference? = null

    var numeroPergunta : Int = 0
    var numeroRespostas : Int = 0
    var numeroalternativa: Int = 0
    var numeroPontos : Int = 0
    var numeroErros : Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar

        toolbar.navigationContentDescription = "Cont"
        setSupportActionBar(toolbar)

        //clearing the previous artist list
//        (questions as ArrayList<Questions>).clear()
        //list to store artists
        questions = ArrayList()


        //get all questions
        QuestionReference = FirebaseDatabase.getInstance().getReference("questions")
        QuestionReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //iterating through all the nodes
                for (postSnapshot in dataSnapshot.children) {
                    //getting artist
                    val question = postSnapshot.getValue<Questions>(Questions::class.java)
                    //adding artist to the list
                    (questions as ArrayList<Questions>).add(question)

                }

                //attaching adapter to the recycleview
                var texto = findViewById<TextView>(R.id.pergunta)
                texto.text =   questions?.size.toString()

                atualizaPerguntas(questions!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }


    fun atualizaPerguntas(questions: ArrayList<Questions> ) {

         alternatives = ArrayList()
         if(questions!!.size == numeroPergunta){

             alertaResultado(questions)

         }else{

             // get the alternatives for the current question
             DatabaseReference = FirebaseDatabase.getInstance().getReference("alternatives").child(questions!![numeroPergunta].questionId)
             DatabaseReference!!.addValueEventListener(object : ValueEventListener {
                 override fun onDataChange(dataSnapshot: DataSnapshot) {
                     (alternatives as ArrayList<Alternatives>).clear()

                     for (postSnapshot in dataSnapshot.children) {
                         val alternative = postSnapshot.getValue<Alternatives>(Alternatives::class.java)
                         (alternatives as ArrayList<Alternatives>).add(alternative)
                     }

                     pergunta.text = questions!![numeroPergunta].questionTitle
                     alternativa1.text = alternatives!![0].alternativeTitle
                     alternativa2.text = alternatives!![1].alternativeTitle
                     alternativa3.text = alternatives!![2].alternativeTitle
                     alternativa4.text = alternatives!![3].alternativeTitle

                     object : CountDownTimer(30000, 1000) {
                         override fun onTick(millisUntilFinished: Long) {
                             var progressbar = findViewById<ProgressBar>(R.id.determinateBar)
                             progressbar.progress = (millisUntilFinished / 300).toInt()

                         }
                         override fun onFinish() {
                             if(numeroalternativa == 0){

                                 numeroErros++
                                 atualizaPerguntas(questions)
                             }else
                             {
                                 atualizaPerguntas(questions)
                             }
                             if (numeroErros == 3){

//                                 val alertDialog: AlertDialog = AlertDialog.Builder(this@GameActivity).create()
//                                 alertDialog.setTitle("Game Over")
//                                 alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Reiniciar", {
//                                     dialogInterface, i ->
//
//                                     numeroPontos = 0
//                                     numeroRespostas = 0
//                                     numeroPergunta =  0
//                                     numeroErros = 0
//                                     numeroalternativa = 0
//                                     atualizaPerguntas(questions)
//                                 })
//
//                                 alertDialog.show()
                                 alertaResultado(questions)

                             }

                         }
                     }.start()
                     numeroPergunta++
                     numeroalternativa = 0

                 }

                 override fun onCancelled(databaseError: DatabaseError) {

                 }
             })

         }
    }

    // to check if the awnser is correct
    fun checaResposta(v: View) {

        // get the correct awnser
        DatabaseReference = FirebaseDatabase.getInstance().getReference("awnsers").child( questions!![numeroPergunta-1].questionId)
        DatabaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (awnsers as ArrayList<Awnsers>).clear()

                for (postSnapshot in dataSnapshot.children) {
                    val awnser = postSnapshot.getValue<Awnsers>(Awnsers::class.java)
                    (awnsers as ArrayList<Awnsers>).add(awnser)
                }

                // get the alternatives for the current question
                DatabaseReference = FirebaseDatabase.getInstance().getReference("alternatives").child(questions!![numeroPergunta-1].questionId)
                DatabaseReference!!.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        (alternatives as ArrayList<Alternatives>).clear()

                        for (postSnapshot in dataSnapshot.children) {
                            val alternative = postSnapshot.getValue<Alternatives>(Alternatives::class.java)
                            (alternatives as ArrayList<Alternatives>).add(alternative)
                        }

                        when (v.id) {
                            R.id.alternativa1 -> idalternative = alternatives!![0].alternativeId
                            R.id.alternativa2 -> idalternative = alternatives!![1].alternativeId
                            R.id.alternativa3 -> idalternative = alternatives!![2].alternativeId
                            R.id.alternativa4 -> idalternative = alternatives!![3].alternativeId
                        }

                        if(idalternative == awnsers!![0].AlternativeId){


                            val inflater = layoutInflater
                            val layout = inflater.inflate(R.layout.toast, findViewById<ViewGroup>(R.id.toast_layout_root)  )

                            val toast = Toast(applicationContext)
                            toast.setGravity(Gravity.TOP, 0, 0)
                            toast.duration = Toast.LENGTH_SHORT
                            toast.view = layout
                            toast.show()

//                            val context = applicationContext
//                            val text = "Resposta correta!"
//                            val duration = Toast.LENGTH_SHORT
//                            val toast = Toast.makeText(context, text, duration)
//                            toast.show()
                            numeroPontos++
                            atualizaPerguntas(questions!!)

                        }else{

                            if (numeroErros == 3){

                                alertaResultado(questions!!)
//                                val alertDialog: AlertDialog = AlertDialog.Builder(applicationContext).create()
//                                alertDialog.setTitle("Game Over")
//                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Reiniciar", {
//                                    dialogInterface, i ->
//
//                                    numeroPontos = 0
//                                    numeroRespostas = 0
//                                    numeroPergunta =  0
//                                    numeroErros = 1
//                                    numeroalternativa = 0
//                                    atualizaPerguntas(questions!!)
//                                })
//
//                                alertDialog.show()

                            }else{

                                val inflater = layoutInflater
                                val layout = inflater.inflate(R.layout.toast_wrong, findViewById<ViewGroup>(R.id.toast_layout_root)  )
                                val toast = Toast(applicationContext)
                                toast.setGravity(Gravity.TOP, 0, 0)
                                toast.duration = Toast.LENGTH_SHORT
                                toast.view = layout
                                toast.show()

//                                val context = applicationContext
//                                val text = "Que pena, resposta incorreta :/"
//                                val duration = Toast.LENGTH_SHORT
//                                val toast = Toast.makeText(context, text, duration)
//                                toast.show()
                                numeroErros++
                                atualizaPerguntas(questions!!)
                            }
                        }

                    }
                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        numeroRespostas++

    }

    fun alertaResultado(questions: ArrayList<Questions>) {



        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("pontuacao", numeroPontos )
        startActivity(intent)
        numeroPontos = 0
        numeroRespostas = 0
        numeroPergunta =  0
        numeroErros = 0
        numeroalternativa = 0


//        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
//        alertDialog.setTitle("Resultado final")
//        alertDialog.setMessage("Voce acertou $numeroPontos mizerave")
//        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Reiniciar", {
//            dialogInterface, i ->
//
//            numeroPontos = 0
//            numeroRespostas = 0
//            numeroPergunta =  0
//            numeroErros = 0
//            numeroalternativa = 0
//            atualizaPerguntas(questions)
//
//        })
//        alertDialog.show()
    }

}