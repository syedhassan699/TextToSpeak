package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeech.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var binding: ActivityMainBinding?= null

    override fun onCreate(savedInstanceState: Bundle?)
    {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding?.root)

            tts = TextToSpeech(this,this)
        binding?.btn?.setOnClickListener(){
            if (binding?.etEntered?.text!!.isEmpty()){
                Toast.makeText(this, "Enter Text", Toast.LENGTH_SHORT).show()
            }else{
              speakOut(binding?.etEntered?.text.toString())
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.UK)

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","LANGUAGE SPECIFIED IS NOT SUPPORTED")
            }else{
                Log.i("TTS","INITIALIZATION FAILED!")
            }
        }
    }

    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        super.onDestroy()

        if (tts != null){
            tts?.stop()
            tts?.shutdown()
        }

        binding = null
    }
}