package com.example.speechtotext

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private val text = "Output"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        supportActionBar?.hide()

        resultTextView = findViewById(R.id.tvResult)
        val startSpeechButton: Button = findViewById(R.id.btnSpeechToText)
        val reset:TextView = findViewById(R.id.reset)

        // Use ActivityResultLauncher to handle speech recognition result
        val speechRecognitionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (!matches.isNullOrEmpty()) {
                    val firstMatch = matches[0]
                    resultTextView.text = firstMatch
                }
            }
        }

        startSpeechButton.setOnClickListener {
            val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            speechRecognitionLauncher.launch(speechIntent)
        }

        reset.setOnClickListener {
            resultTextView.text = text
        }
    }
}
