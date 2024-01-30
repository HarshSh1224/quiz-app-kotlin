package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button  = findViewById<Button>(R.id.btn_start)
        val textInput = findViewById<TextInputEditText>(R.id.et_name)

        button.setOnClickListener{
            textInput.text.let {
                if (textInput.text!!.isNotEmpty()) {
                    val intent = Intent(this, QuestionsPage::class.java)
                    intent.putExtra(Constants.userName, textInput.text!!.toString())
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}