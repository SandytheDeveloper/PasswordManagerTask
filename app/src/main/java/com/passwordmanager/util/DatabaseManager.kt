package com.passwordmanager.util

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

class DatabaseManager(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "PasswordDatabase"
        private val TABLE_NAME = "PasswordList"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }


    //method to insert data
    fun insertData(name : String){

        if (getStringData().isNotEmpty()){
            updateData(name)
            return
        }

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, 1)
        contentValues.put(KEY_NAME, name)

        val success = db.insert(TABLE_NAME, null, contentValues)

        db.close() // Closing database connection
    }

    //method to read data
    @SuppressLint("Range", "Recycle")
    fun getStringData():String{
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ""
        }
        var value = ""
        if (cursor.moveToFirst()) {

            value = cursor.getString(cursor.getColumnIndex("$KEY_NAME"))

        }
        return value
    }

    //method to update data
    fun updateData(name : String):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, 1)
        contentValues.put(KEY_NAME, name)

        // Updating Row
        val success = db.update(TABLE_NAME, contentValues, "$KEY_ID=1",null)

        db.close() // Closing database connection
        return success
    }

}