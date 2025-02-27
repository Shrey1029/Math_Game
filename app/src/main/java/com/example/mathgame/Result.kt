package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Result : AppCompatActivity() {
    lateinit var result: TextView
    lateinit var cong: TextView
    lateinit var play: Button
    lateinit var leave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        result = findViewById(R.id.finalscore)
        cong = findViewById(R.id.congrats)
        leave = findViewById(R.id.exit)
        play = findViewById(R.id.again)

        val score = intent.getIntExtra("Score", 0)
        result.text = "Your Score is $score"

        play.setOnClickListener {
            val intent = Intent(this@Result, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        leave.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
