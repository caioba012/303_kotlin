package com.example.duastelasretorno

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TelaValores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_valores)

        val btnRetorno = findViewById(R.id.btnVoltar) as Button
        btnRetorno.setOnClickListener{
            val txt = findViewById(R.id.txt1) as TextView
            val msg = txt.text

            //opção 1 - mais antiga
            /* val intent = Intent()
            intent.putExtra("ActivityResult", msg)
            setResult(RESULT_OK, intent)
            */

            Intent().apply {
                putExtra("param1", msg)
                putExtra("param2", msg)
                setResult(RESULT_OK,this)
            }

            finish()
        }
    }
}