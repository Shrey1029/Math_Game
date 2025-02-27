package com.example.mathgame

import android.content.Intent // Import for launching new activities
import androidx.appcompat.app.AppCompatActivity // Import for activity creation
import android.os.Bundle // Bundle to pass data to activity lifecycle methods
import android.widget.Button // Import for handling button UI elements

class MainActivity : AppCompatActivity() { // Defining MainActivity which inherits from AppCompatActivity
    // Declare variables for buttons (lateinit: initialized later)
    lateinit var additon: Button
    lateinit var multiplication: Button
    lateinit var subtraction: Button
    lateinit var division: Button

    override fun onCreate(savedInstanceState: Bundle?) { // Method called when the activity is created
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set the layout file for the activity

        // Initialize buttons by finding their respective views by ID
        additon = findViewById(R.id.buttonadd)
        subtraction = findViewById(R.id.buttonsub)
        multiplication = findViewById(R.id.buttonmultiply)
        division = findViewById(R.id.buttondiv)

        // Set onClickListeners for each button to start GameActivity with the respective operation
        additon.setOnClickListener {
            // Intent to navigate from MainActivity to GameActivity with the operation type as "addition"
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("operation", "addition") // Passing "addition" as extra data to GameActivity
            startActivity(intent) // Start GameActivity
        }

        subtraction.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("operation", "subtraction") // Passing "subtraction" as extra data to GameActivity
            startActivity(intent)
        }

        multiplication.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("operation", "multiplication") // Passing "multiplication" as extra data to GameActivity
            startActivity(intent)
        }

        division.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("operation", "division") // Passing "division" as extra data to GameActivity
            startActivity(intent)
        }
    }
}
