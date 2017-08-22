package com.example.elder.quizz.feature.game

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.elder.quizz.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var pontuacao = findViewById<TextView>(R.id.resultado)
        var pontos = intent.getIntExtra("pontuacao", 0)
        pontuacao.text = "Parabéns, você fez ${pontos} pontos"

    }

    fun Jogar(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}

