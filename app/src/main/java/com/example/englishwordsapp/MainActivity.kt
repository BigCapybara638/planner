package com.example.englishwordsapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemsAdapter
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.itemList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        dbHelper = DbHelper(this, null)

        val items = dbHelper.getTodayItems()

        adapter = ItemsAdapter(items, this)
        recyclerView.adapter = adapter

        val linkToAdd: TextView = findViewById(R.id.buttonAdd)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        linkToAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_tasks -> {
                    // Показать экран задач
                    supportFragmentManager.beginTransaction()
                        .commit()
                    true
                }
                R.id.nav_calendar -> {
                    // Показать экран календаря
                    supportFragmentManager.beginTransaction()
                        .commit()
                    true
                }
                R.id.nav_settings -> {
                    // Показать экран настроек
                    supportFragmentManager.beginTransaction()
                        .commit()
                    true
                }
                else -> false
            }
        }

        // Установить начальный фрагмент
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_tasks
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}