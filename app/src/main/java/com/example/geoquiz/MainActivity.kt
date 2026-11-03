package com.example.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnNext: Button

    private val questions = arrayOf(
        Question("Canberra is the capital of Australia.", true),
        Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
        Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
        Question("The source of the Nile River is in Egypt.", false),
        Question("The Amazon River is the longest river in the Americas.", true),
        Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
    )

    private var currentIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
        updateQuestion()
    }

    private fun initViews() {
        tvQuestion = findViewById(R.id.tvQuestion)
        btnTrue = findViewById(R.id.btnTrue)
        btnFalse = findViewById(R.id.btnFalse)
        btnNext = findViewById(R.id.btnNext)
    }

    private fun updateQuestion() {
        tvQuestion.text = questions[currentIndex].questionText
    }

    private fun showResults() {
        val percentage = (score.toDouble() / questions.size) * 100

        val message = when {
            percentage == 100.0 -> "Perfect! All answers correct!"
            percentage >= 80.0 -> "Great job!"
            percentage >= 60.0 -> "Good effort!"
            else -> "Keep practicing!"
        }

        android.app.AlertDialog.Builder(this)
            .setTitle("Quiz Completed!")
            .setMessage("$message\n\nYour score: $score out of ${questions.size}\n($percentage%)")
            .setPositiveButton("Play Again") { _, _ ->
                restartQuiz()
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun restartQuiz() {
        currentIndex = 0
        score = 0
        btnTrue.visibility = View.VISIBLE
        btnFalse.visibility = View.VISIBLE
        btnNext.isEnabled = true
        btnNext.visibility = View.VISIBLE
        updateQuestion()
    }

    private fun setupListeners() {
        btnTrue.setOnClickListener {
            checkAnswer(true)
        }

        btnFalse.setOnClickListener {
            checkAnswer(false)
        }

        btnNext.setOnClickListener {
            currentIndex++

            if (currentIndex < questions.size) {
                updateQuestion()
                // Показываем кнопки ответов для следующего вопроса
                btnTrue.visibility = View.VISIBLE
                btnFalse.visibility = View.VISIBLE
            } else {
                // Последний вопрос - блокируем кнопку Next
                btnNext.isEnabled = false
                btnNext.visibility = View.INVISIBLE
                showResults()
            }
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questions[currentIndex].answer

        if (userAnswer == correctAnswer) {
            score++
        }

        // Скрываем кнопки ответов
        btnTrue.visibility = View.INVISIBLE
        btnFalse.visibility = View.INVISIBLE
    }
}