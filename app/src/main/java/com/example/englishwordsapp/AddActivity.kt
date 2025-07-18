package com.example.englishwordsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)

        val addTitle: EditText = findViewById(R.id.addTitle)
        val addDate: EditText = findViewById(R.id.addDate)
        val addGoal: Button = findViewById(R.id.buttonAddGoal)
        val linkToPlans: TextView = findViewById(R.id.ToPlans)

        linkToPlans.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        addGoal.setOnClickListener {
            val title = addTitle.text.toString().trim()
            val date = addTitle.text.toString().trim()
            val publish = "no"

            if(title == "" || date == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else {
                val goal = Plans(title, date, publish)

                val db = DbHelper(this, null)
                db.addGoal(goal)
                Toast.makeText(this, "Цель добавлена", Toast.LENGTH_LONG).show()

                addTitle.text.clear()
                addDate.text.clear()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}