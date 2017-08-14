package com.example.elder.quizz.feature.game

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
import android.widget.Toast
import android.support.v7.widget.Toolbar
import android.content.Intent
import com.example.elder.quizz.feature.main.MainActivity


class GameActivity : AppCompatActivity() {

    val perguntas = arrayListOf<String>()
    val alternativas = HashMap<Int, List<String>>()
    val respostas = arrayListOf(2,2,2,2)
    var numeroPergunta : Int = 0
    var numeroRespostas : Int = 0
    var numeroalternativa: Int = 0
    var numeroPontos : Int = 0
    var numeroErros : Int = 1


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        toolbar.navigationContentDescription = "Cont"
        setSupportActionBar(toolbar)

        prepararLista()
        atualizaPerguntas()
    }


    fun prepararLista(){

        perguntas.add("Qual valor de PI?")
        perguntas.add("Qual o nome do presidente que sofreu iptman em 2016?")
        perguntas.add("Quem mais roubou em  em 2017")
        perguntas.add("Qual o numero da sorte?")


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
        terceira.add("Michel Temer2")
        terceira.add("Michel Temer3")
        terceira.add("Michel Temer4")

        val quarta = ArrayList<String>()
        quarta.add("Michel Temer")
        quarta.add("Vampirao")
        quarta.add("vamp")
        quarta.add("cuz")


        // adicionando a respostas
        alternativas.put(0 ,primeira )
        alternativas.put(1 ,segunda)
        alternativas.put(2 ,terceira)
        alternativas.put(3 ,quarta)

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
                     if(numeroalternativa == 0){

                         numeroErros++
                         atualizaPerguntas()
                     }else
                     {
                         atualizaPerguntas()
                     }
                     if (numeroErros > 3){

                         val alertDialog: AlertDialog = AlertDialog.Builder(this@GameActivity).create()
                         alertDialog.setTitle("Game Over")
                         alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Reiniciar", {
                             dialogInterface, i ->

                             numeroPontos = 0
                             numeroRespostas = 0
                             numeroPergunta =  0
                             numeroErros = 1
                             numeroalternativa = 0
                             atualizaPerguntas()
                         })

                         alertDialog.show()

                     }

                 }
             }.start()
             numeroPergunta++
             numeroalternativa = 0

         }
     }

    fun alertaResultado() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Resultado final")
        alertDialog.setMessage("Voce acertou $numeroPontos mizerave")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Reiniciar", {
            dialogInterface, i ->

            numeroPontos = 0
            numeroRespostas = 0
            numeroPergunta =  0
            numeroErros = 1
            numeroalternativa = 0
            atualizaPerguntas()

        })
        alertDialog.show()
    }


    // to check if the awnser is correct
    fun checaResposta(v: View) {

        when (v.id) {
            R.id.alternativa1 -> numeroalternativa = 1
            R.id.alternativa2 -> numeroalternativa = 2
            R.id.alternativa3 -> numeroalternativa = 3
            R.id.alternativa4 -> numeroalternativa = 4
        }

        if(numeroalternativa == respostas[numeroRespostas]){

            val context = applicationContext
            val text = "Resposta correta!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
            numeroPontos++
            atualizaPerguntas()

        }else{

            if (numeroErros == 3){
                val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
                alertDialog.setTitle("Game Over")
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Reiniciar", {
                    dialogInterface, i ->

                    numeroPontos = 0
                    numeroRespostas = 0
                    numeroPergunta =  0
                    numeroErros = 1
                    numeroalternativa = 0
                    atualizaPerguntas()
                })

                alertDialog.show()

            }else{

                val context = applicationContext
                val text = "Que pena, resposta incorreta :/"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
                numeroErros++
                atualizaPerguntas()
            }
        }

        numeroRespostas++

    }

    fun home(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}


