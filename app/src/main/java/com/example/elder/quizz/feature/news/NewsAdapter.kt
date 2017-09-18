package com.example.elder.quizz.feature.question

import android.widget.TextView
import android.view.ViewGroup
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.example.elder.quizz.R
import model.Questions
import android.support.v7.app.AppCompatActivity


/**
 * Created by elder.santos on 18/08/2017.
 */
class NewsAdapter(var context: Activity, val questions: List<Questions>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val questions = questions[position]

        holder?.textViewName?.text =  questions.questionTitle
        holder?.textViewYear?.text = questions.year


        holder?.view?.setOnClickListener {


            //creating an intent
            var intent = Intent(context, QuestionActivity::class.java)

            //putting artist name and id to intent
            intent.putExtra("QUESTION_NAME" , questions.questionTitle)
            intent.putExtra("QUESTION_ID" , questions.questionId)


            //starting the activity with intent
            (context as AppCompatActivity).startActivity(intent)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.question_list, parent, false))



    override fun getItemCount(): Int = questions.size

    class ViewHolder(view: View?) : RecyclerView.ViewHolder(view) {
        var view = view
        var textViewName = view?.findViewById<TextView>(R.id.textViewQuestion)
        var textViewYear = view?.findViewById<TextView>(R.id.textViewYear)

    }


    companion object {
        //we will use these constants later to pass the artist name and id to another activity
        val QUESTION_NAME = "com.example.elder.quizz.feature.model.Questions.questionTitle"
        val QUESTION_ID = "com.example.elder.quizz.feature.model.Questions.getQuestionId"
    }

}