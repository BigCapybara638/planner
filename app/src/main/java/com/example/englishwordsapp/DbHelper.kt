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

    fun getGoal() : String {
        val db = this.readableDatabase

        val result = db.rawQuery("SELECT * FROM plans", null)
        return result.toString()
    }

}