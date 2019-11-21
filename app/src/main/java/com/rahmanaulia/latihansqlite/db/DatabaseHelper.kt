package com.rahmanaulia.latihansqlite.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rahmanaulia.latihansqlite.db.DatabaseContract.NoteColumns.Companion.DESCRIPTION
import com.rahmanaulia.latihansqlite.db.DatabaseContract.NoteColumns.Companion.ID
import com.rahmanaulia.latihansqlite.db.DatabaseContract.NoteColumns.Companion.TABLE_NOTE
import com.rahmanaulia.latihansqlite.db.DatabaseContract.NoteColumns.Companion.TITLE

class DatabaseHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOTE")
        onCreate(db)
    }

    companion object{
        private const val DATABASE_NAME = "dbnoteapp"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NOTE" +
                " ($ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $TITLE TEXT NOT NULL," +
                " $DESCRIPTION TEXT NOT NULL)"
    }

}