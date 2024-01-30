package com.example.quizapp

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class QuestionsPage : AppCompatActivity(), View.OnClickListener {
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvOption1: TextView? = null
    private var tvOption2: TextView? = null
    private var tvOption3: TextView? = null
    private var tvOption4: TextView? = null
    private var btnSubmit: Button? = null

    private var mCurrentPosition = 0;
    private var mSelectedOption = 0;
    private var mCorrectAns = 0;

    private var mQuestionsList : ArrayList<Question>? = null

    var userName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_page)

        userName = intent.getStringExtra(Constants.userName)

        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_flag)
        progressBar = findViewById(R.id.pb_progress)
        tvProgress = findViewById(R.id.tv_progress)
        tvOption1 = findViewById(R.id.tv_option1)
        tvOption2 = findViewById(R.id.tv_option2)
        tvOption3 = findViewById(R.id.tv_option3)
        tvOption4 = findViewById(R.id.tv_option4)
        btnSubmit = findViewById(R.id.btn_submit)

        mQuestionsList = Constants.getQuestions()

        tvOption1?.setOnClickListener(this)
        tvOption2?.setOnClickListener(this)
        tvOption3?.setOnClickListener(this)
        tvOption4?.setOnClickListener(this)

        btnSubmit?.setOnClickListener(this)

        setQuestion()
    }

    private fun setQuestion(){
        defaultOptionsView()
        tvQuestion?.text = mQuestionsList!![mCurrentPosition].question
        ivImage?.setImageResource(mQuestionsList!![mCurrentPosition].image)
        tvOption1?.text = mQuestionsList!![mCurrentPosition].option1
        tvOption2?.text = mQuestionsList!![mCurrentPosition].option2
        tvOption3?.text = mQuestionsList!![mCurrentPosition].option3
        tvOption4?.text = mQuestionsList!![mCurrentPosition].option4
        tvProgress?.text = "$mCurrentPosition" + "/" + progressBar?.max
        progressBar?.progress = mCurrentPosition
        btnSubmit?.text = "Submit"
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_submit -> {
                submitButtonOnTap()
            }

            R.id.tv_option1 -> {
                selectedOptionView(tvOption1, 1)
            }

            R.id.tv_option2 -> {
                selectedOptionView(tvOption2, 2)
            }

            R.id.tv_option3 -> {
                selectedOptionView(tvOption3, 3)
            }

            R.id.tv_option4 -> {
                selectedOptionView(tvOption4, 4)
            }
        }
    }

    private fun submitButtonOnTap() {
        defaultOptionsView()
        if (mSelectedOption == 0) {
            if (mCurrentPosition < mQuestionsList!!.size - 1) {
                mCurrentPosition++;
                setQuestion();
            } else {
                val intent = Intent(this, FinishActivity::class.java)
                intent.putExtra(Constants.userName, userName)
                intent.putExtra(Constants.correctAnswers, mCorrectAns)
                intent.putExtra(Constants.total, mQuestionsList!!.size)
                startActivity(intent)
                finish()
            }
        } else {
            val ans = mQuestionsList!![mCurrentPosition].correctOption

            if(mSelectedOption != ans){
                val wrongOptionTv = getOptionTextViewFromOptionNumber(mSelectedOption)
                wrongOptionTv.let {
                    it!!.background = ContextCompat.getDrawable(
                        this,
                        R.drawable.wrong_option_border
                    )
                }
            } else {
                mCorrectAns++
            }
            val correctOptionTv = getOptionTextViewFromOptionNumber(ans)
            correctOptionTv.let {
                it!!.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.correct_option_border
                )
            }
            mSelectedOption = 0;
            if(mCurrentPosition == mQuestionsList!!.size - 1) {
                btnSubmit?.text = "Finish"
            } else {
                btnSubmit?.text = "Go to next question"
            }
        }
    }

    private fun getOptionTextViewFromOptionNumber(option : Int) : TextView? {
        return when(option){
            1 -> tvOption1
            2 -> tvOption2
            3 -> tvOption3
            4 -> tvOption4
            else -> tvOption1
        }
    }

    private fun defaultOptionsView(){
        val optionsList = ArrayList<TextView>()

        tvOption1.let {
            optionsList.add(it!!)
        }
        tvOption2.let {
            optionsList.add(it!!)
        }
        tvOption3.let {
            optionsList.add(it!!)
        }
        tvOption4.let {
            optionsList.add(it!!)
        }

        for (option in optionsList){
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border
            )
        }
    }

    private fun selectedOptionView(tv: TextView?, option: Int){
        defaultOptionsView()
        if(mSelectedOption == option){
            mSelectedOption = 0;
            return
        }
        tv.let{
            tv!!.typeface = Typeface.DEFAULT_BOLD
            tv.background = ContextCompat.getDrawable(
                this,
                R.drawable.selected_option_border
            )
        }
        mSelectedOption = option
    }
}