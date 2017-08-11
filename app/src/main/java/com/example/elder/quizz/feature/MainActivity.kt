package com.example.elder.quizz.feature

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.elder.quizz.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.os.CountDownTimer
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button


class MainActivity : AppCompatActivity() {

    val perguntas = arrayListOf<String>()
    val alternativas = HashMap<Int, List<String>>()
    val respostas = arrayOf(1,2,2)
    var numeroPergunta : Int = 0


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepararLista()
        atualizaPerguntas()

    }

    fun prepararLista(){

        perguntas.add("Qual valor de PI?")
        perguntas.add("Qual o nome do presidente que sofreu iptman em 2016?")
        perguntas.add("Quem mais roubou em  em 2017")


        // adicionando alternativas
        val primeira = ArrayList<String>()
        primeira.add("3,14")
        primeira.add("5")
        primeira.add("9")
        primeira.add("5,21")

        val segunda = ArrayList<String>()
        segunda.add("Michel Temer")
        segunda.add("Dilma Rulsef")
        segunda.add("Eneias ")
        segunda.add("Bolsolixo")

        val terceira = ArrayList<String>()
        terceira.add("Michel Temer")
        terceira.add("Michel Temer")
        terceira.add("Michel Temer")
        terceira.add("Michel Temer")

        // adicionando a respostas
        alternativas.put(0 ,primeira )
        alternativas.put(1 ,segunda)
        alternativas.put(2 ,terceira)

    }

     fun atualizaPerguntas() {

         if(perguntas.size == numeroPergunta){

             alertaResultado()

         }else{
             pergunta.text = perguntas[numeroPergunta]
             alternativa1.text = alternativas[numeroPergunta]?.get(0)
             alternativa2.text = alternativas[numeroPergunta]?.get(1)
             alternativa3.text = alternativas[numeroPergunta]?.get(2)
             alternativa4.text = alternativas[numeroPergunta]?.get(3)

             object : CountDownTimer(30000, 1000) {
                 override fun onTick(millisUntilFinished: Long) {
                     textView.text = (millisUntilFinished / 1000).toString()

                 }
                 override fun onFinish() {
                     atualizaPerguntas()
                     textView.setText("done!")

                     atualizaPerguntas()
                 }
             }.start()

             numeroPergunta ++
         }
     }

    fun alertaResultado() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Resultado!")
        alertDialog.setMessage("Voce acertou mizerave")
        alertDialog.show()
    }
    fun checaResposta(view : View){

//        var btn Button = this.getText()
    }
}


