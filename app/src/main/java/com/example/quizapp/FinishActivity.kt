package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class FinishActivity : AppCompatActivity() {
    private var userName : String? = null
    private var score : Int? = null
    private var total : Int? = null
    private var tvUserName: TextView? = null;
    private var tvYourScore: TextView? = null;
    private var btnFinish: Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)


        userName = intent.getStringExtra(Constants.userName)
        score = intent.getIntExtra(Constants.correctAnswers, 0)
        total = intent.getIntExtra(Constants.total, 20)

        tvUserName = findViewById(R.id.tv_user_name)
        tvYourScore = findViewById(R.id.tv_your_score)
        btnFinish = findViewById(R.id.btn_finish)

        tvUserName.let{
            it!!.text = userName
        }
        tvYourScore.let{
            it!!.text = "Your score is ${score.toString()} / ${total.toString()}"
        }

        btnFinish.let{
            it!!.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}