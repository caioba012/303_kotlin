package com.example.listabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayAdapter : ArrayAdapter<*> //<*> qualquer coisa
        val usuarios = arrayOf("São Paulo", "Minas Gerais" , "Rio de Janeiro" , "Paraná" ,
                                "Espirito Santo" , "Bahia" , "Mato Grosso" , "Mato Grosso do Sul",
                                "Santa Catarina" , "Tocantins", "Rio Grande do Sul", "Maranhão" ,
                                "Pernanbuco", "Amazonas", "Acre" , "Alagoas", "Roraima" , "Rondonia")

        // acessa a lista a partir de um arquivo xml
        var mListView = findViewById<ListView>(R.id.userlist)

        // cria o adapter
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, usuarios)
        mListView.adapter = arrayAdapter

    }
}