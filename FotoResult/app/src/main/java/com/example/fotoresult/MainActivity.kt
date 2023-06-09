package br.com.slmm.fotoresult

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.com.slmm.fotoresult.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val register = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()){
            image: Bitmap? ->
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.textView.text="teste"
        binding.button.text="Click Aqui"
        binding.button.setOnClickListener{
            register.launch(null)
        }
        setContentView(binding.root)
    }
}