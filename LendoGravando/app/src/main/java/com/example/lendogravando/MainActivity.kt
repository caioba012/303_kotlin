package com.example.lendogravando

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.lendogravando.databinding.ActivityMainBinding
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var fileName = "DS304_${System.currentTimeMillis()}.txt"
    internal var extFile: File?=null
    private val filepath = "Minha_foto"

    private var imageUri: Uri? = null

    //chamada simples de requisição de permissão
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){  isGranted: Boolean ->
            if (isGranted){
                Log.i("Permissions","Aceita")
                binding.btnFoto.isEnabled = true
            }else {
                Log.i("Permissions", "Negada")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGrava.setOnClickListener {
            extFile = File(getExternalFilesDir(filepath), fileName)
            Log.e("DEBUG", getExternalFilesDir(filepath).toString())

            try{
                val fileOutputStream = FileOutputStream(extFile)
                fileOutputStream.write(binding.edtMsg.text.toString().toByteArray())
                fileOutputStream.close()
            }
            catch (e: IOException) {
                e.printStackTrace()
            }
            binding.txtMsg.text = "Dados gravados"
        }

        binding.btnLe.setOnClickListener {
            extFile = File(getExternalFilesDir(filepath), fileName)
            Log.e("DEBUG", getExternalFilesDir(filepath).toString())

            if(fileName!=null && fileName.trim() != ""){
                var fileInputStream = FileInputStream(extFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while( {text = bufferedReader.readLine(); text}() != null){
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                binding.txtMsg.text = stringBuilder.toString()
            }
        }

        if(!isExternalStorageAvailable || isExternalStorageReadOnly){
            binding.btnGrava.isEnabled = true
        }

        //Trataremos o botao para tirar a foto
        if(ContextCompat.checkSelfPermission(this, CAMERA)!=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(CAMERA)
        }else{
            binding.btnFoto.isEnabled = true
        }

        binding.btnFoto.setOnClickListener {
            imageUri = initFileUri()
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE,"Foto Tirada")
            values.put(MediaStore.Images.Media.DESCRIPTION, "Aula 303")

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            imageUri = this?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            launcher.launch(intent)
        }
    }

    val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            binding.imageView?.setImageURI(imageUri)
        }
    }

    private fun initFileUri():Uri{
        val filepath = "fotos"
        val fileName = "Foto_${System.currentTimeMillis()}.jpg"
        //cria o arquivo no diretorio acima
        val jpgImage = File(getExternalFilesDir(filepath),fileName)

        return FileProvider.getUriForFile(applicationContext,
            "com.example.fileprovider2",
            jpgImage)
    }


    private val isExternalStorageAvailable: Boolean get(){
        val extStorageState =  Environment.getExternalStorageState()

        //método didático, tanto que infoma estar redundante pois é uma constante booleana
        return if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)){
            true
        }else{
            false
        }
    }

    private val isExternalStorageReadOnly: Boolean get(){
        val extStorageState = Environment.getExternalStorageState()
        //mesmo método porém não redundante
        return (Environment.MEDIA_MOUNTED.equals(extStorageState))
    }

}