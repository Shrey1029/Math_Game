package com.example.mathgame

import android.content.Intent // Import for launching new activities
import androidx.appcompat.app.AppCompatActivity // Import for activity creation
import android.os.Bundle // Bundle to pass data to activity lifecycle methods
import android.os.CountDownTimer // Import for timer functionality
import android.widget.Button // Import for button UI elements
import android.widget.EditText // Import for input fields (user answer)
import android.widget.TextView // Import for text views (to show question, score, etc.)
import android.widget.Toast // Import for showing Toast messages to the user
import java.util.Locale // Import for formatting locale-specific strings
import kotlin.random.Random // Import for generating random numbers

class GameActivity : AppCompatActivity() { // Defining GameActivity which inherits from AppCompatActivity

    // Declare UI components and game variables
    lateinit var score: TextView
    lateinit var lives: TextView
    lateinit var time: TextView
    lateinit var question: TextView
    lateinit var answer: EditText
    lateinit var ok: Button
    lateinit var buttonnext: Button
    var correctans = 0 // Variable to store the correct answer
    var userscore = 0 // Variable to track user's score
    var userlife = 3 // Variable to track user's lives
    lateinit var timer: CountDownTimer // Variable for countdown timer
    private val timeinmil: Long = 30000 // Time for each question (30 seconds)
    var timeleft: Long = timeinmil // Time left for the current question

    override fun onCreate(savedInstanceState: Bundle?) { // Method called when the activity is created
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game) // Set the layout file for the activity

        // Get the operation type from the intent extras
        val operation = intent.getStringExtra("operation")

        // Set the title of the action bar based on the operation
        supportActionBar?.title = when (operation) {
            "addition" -> "Addition"
            "subtraction" -> "Subtraction"
            "multiplication" -> "Multiplication"
            "division" -> "Division"
            else -> "Math Game"
        }

        // Initialize UI elements by finding their views by ID
        score = findViewById(R.id.points)
        lives = findViewById(R.id.total)
        time = findViewById(R.id.second)
        question = findViewById(R.id.textView)
        answer = findViewById(R.id.editText)
        ok = findViewById(R.id.buttonok)
        buttonnext = findViewById(R.id.buttonnext)

        // Start the game with the appropriate operation type
        game(operation)

        // Listener for OK button to check the user's answer
        ok.setOnClickListener {
            val input = answer.text.toString() // Get the answer input by the user
            if (input.isEmpty()) {
                // If input is empty, show a Toast message
                Toast.makeText(applicationContext, "Please write an answer or click next", Toast.LENGTH_LONG).show()
            } else {
                pauseTimer() // Pause the timer
                val useranswer = input.toInt() // Convert user input to an integer
                if (useranswer == correctans) { // If the answer is correct
                    userscore += 10 // Increase the score
                    question.text = "Congratulations, Your answer is correct" // Update the question TextView
                    score.text = userscore.toString() // Update the score TextView
                } else { // If the answer is incorrect
                    question.text = "Sorry, your answer is incorrect" // Update the question TextView
                    userlife-- // Decrease the user's life
                    lives.text = userlife.toString() // Update the lives TextView
                }
            }
        }

        // Listener for the next button to proceed to the next question
        buttonnext.setOnClickListener {
            pauseTimer() // Pause the timer
            resetTimer() // Reset the timer for the next question
            answer.setText("") // Clear the answer input field
            if (userlife == 0) { // If the user has no lives left
                Toast.makeText(applicationContext, "Sorry, Game Over", Toast.LENGTH_LONG).show()
                // Navigate to the Result activity and pass the user's score
                val intent = Intent(this@GameActivity, Result::class.java)
                intent.putExtra("Score", userscore)
                startActivity(intent)
                finish() // Finish the current activity
            } else {
                game(operation) // Continue the game with the same operation type
            }
        }
    }

    // Function to start the game and generate the question based on the selected operation
    fun game(operation: String?) {
        when (operation) {
            "addition" -> { // If the operation is addition
                val number1 = Random.nextInt(0, 1000) // Generate a random number between 0 and 1000
                val number2 = Random.nextInt(0, 1000)
                question.text = "$number1 + $number2" // Display the question
                correctans = number1 + number2 // Calculate the correct answer
            }
            "subtraction" -> { // If the operation is subtraction
                val number1 = Random.nextInt(0, 1000)
                val number2 = Random.nextInt(0, 1000)
                question.text = "$number1 - $number2"
                correctans = number1 - number2
            }
            "multiplication" -> { // If the operation is multiplication
                val number1 = Random.nextInt(0, 100) // Smaller range to avoid large numbers
                val number2 = Random.nextInt(0, 100)
                question.text = "$number1 * $number2"
                correctans = number1 * number2
            }
            "division" -> { // If the operation is division
                val number1 = Random.nextInt(1, 1000) // Avoid dividing by zero
                val number2 = Random.nextInt(1, 100)
                question.text = "$number1 / $number2"
                correctans = number1 / number2
            }
        }
        startTimer() // Start the countdown timer for the question
    }

    // Function to start the countdown timer for each question
    fun startTimer() {
        timer = object : CountDownTimer(timeleft, 1000) { // Timer duration in milliseconds
            override fun onTick(millisUntilFinished: Long) { // Called every second
                timeleft = millisUntilFinished
                updateText() // Update the time left on the screen
            }

            override fun onFinish() { // Called when the timer finishes
                pauseTimer() // Pause the timer
                resetTimer() // Reset the timer for the next question
                userlife-- // Decrease the user's life
                lives.text = userlife.toString() // Update the lives TextView
                question.text = "Sorry, your time is up" // Display time-up message
                if (userlife == 0) { // If the user has no lives left
                    Toast.makeText(applicationContext, "Sorry, Game Over", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@GameActivity, Result::class.java)
                    intent.putExtra("Score", userscore) // Pass the user's score to the result activity
                    startActivity(intent)
                    finish() // Finish the current activity
                }
            }
        }.start() // Start the timer
    }

    // Function to update the time left on the screen
    private fun updateText() {
        val remainingtime: Int = (timeleft / 1000).toInt() // Convert milliseconds to seconds
        time.text = String.format(Locale.getDefault(), "%02d", remainingtime) // Display the time in 2-digit format
    }

    // Function to reset the timer for the next question
    private fun resetTimer() {
        timeleft = timeinmil // Reset the timer to the initial time (30 seconds)
        updateText() // Update the time on the screen
    }

    // Function to pause the timer
    private fun pauseTimer() {
        timer.cancel() // Cancel the ongoing timer
    }
}
