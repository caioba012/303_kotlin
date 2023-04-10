package com.example.permissoes1

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.permissoes1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val permissionActResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
        var msg: String = ""
        when (it){
            true -> {msg = "Permissao concedida"}
            false -> {msg = "Permissao não concedida"}
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textView.text="Hello my friend"

        binding.btnPermi.setOnClickListener {
            binding.textView.text = "Clicked"
            if(checkPermission()) {
                permissionActResult.launch(CAMERA)
            }
            else{
                println("You can call anything...")
            }
        }

    }
    /* Cria uma função para verificar se a permissao está ativa ou não */
    private fun checkPermission(): Boolean{
        val permission = ContextCompat.checkSelfPermission(
            this, CAMERA)

        return permission != PackageManager.PERMISSION_GRANTED
    }
}