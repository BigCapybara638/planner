package com.example.englishwordsapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
}