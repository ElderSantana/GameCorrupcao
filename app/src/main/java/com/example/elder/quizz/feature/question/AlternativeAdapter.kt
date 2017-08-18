package com.example.elder.quizz.feature.question

import android.annotation.SuppressLint
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.elder.quizz.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import model.AlternativeCorrect
import model.Alternatives
import model.Questions
import android.widget.*


/**
 * Created by elder.santos on 18/08/2017.
 */

class AlternativesAdapter(var context: Activity, val alternatives: List<Alternatives>, val idquestion: String) : RecyclerView.Adapter<AlternativesAdapter.ViewHolder>() {
    internal lateinit var databaseQuestions: DatabaseReference


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val alternatives = alternatives[position]


        databaseQuestions = FirebaseDatabase.getInstance().getReference("awnsers").child(idquestion)


        holder?.textViewName?.text =  alternatives.alternativeTitle
        holder?.textViewYear?.text = alternatives.alternativeId


        // Set the listener for click
        holder?.view?.setOnClickListener {

            val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Adicionar ${alternatives.alternativeTitle} como resposta correta?")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", {
                dialogInterface, i ->

//                holder?.alternative?.setBackgroundResource(R.color.bg_login)
                val alternativeCorrect = AlternativeCorrect(alternatives.alternativeId)
                databaseQuestions!!.setValue(alternativeCorrect)
                Toast.makeText(context, "Questão adicionada como correta", Toast.LENGTH_LONG).show()

            })
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, " Não", {
                dialogInterface, i ->
            })
            alertDialog.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.question_list, parent, false))



    override fun getItemCount(): Int = alternatives.size

    class ViewHolder(view: View?) : RecyclerView.ViewHolder(view) {
        var view = view
        var textViewName = view?.findViewById<TextView>(R.id.textViewQuestion)
        var textViewYear = view?.findViewById<TextView>(R.id.textViewYear)
        var alternative = view?.findViewById<LinearLayout>(R.id.alternative)

    }

}