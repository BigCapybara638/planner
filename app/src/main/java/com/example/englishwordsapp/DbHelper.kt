package com.example.englishwordsapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DbHelper(val context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE plans (id INT PRIMARY KEY, title TEXT, date TEXT, published TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS plans")
        onCreate(db)    }

    fun addGoal(goal: Plans) {
        val values = ContentValues()
        values.put("title", goal.title)
        values.put("date", goal.date)
        values.put("published", goal.published)

        val db = this.writableDatabase
        db.insert("plans", null, values)

        db.close()
    }

    // Метод для получения всех элементов
    fun getAllItems(): List<Item> {
        val items = mutableListOf<Item>()
        val db = readableDatabase
        val cursor = db.query(
            "plans",
            null,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val title = getString(getColumnIndexOrThrow("title"))
                val date = getString(getColumnIndexOrThrow("date"))
                val publish = getString(getColumnIndexOrThrow("published"))
                items.add(Item(id, title, date, publish))
            }
            close()
        }
        db.close()
        return items
    }

    fun getTodayItems(): List<Item> {
        val today = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        val todayItems = mutableListOf<Item>()

        val db = readableDatabase

        // Лучший вариант: если дата хранится в формате dd.MM.yyyy
        val cursor = db.rawQuery(
            """SELECT * FROM plans 
           WHERE date = ?""",
            arrayOf(today)
        )

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow("id"))
                val title = it.getString(it.getColumnIndexOrThrow("title"))
                val date = it.getString(it.getColumnIndexOrThrow("date"))
                val publish = it.getString(it.getColumnIndexOrThrow("published"))
                todayItems.add(Item(id, title, date, publish))
            }
        }

        return todayItems
    }
}