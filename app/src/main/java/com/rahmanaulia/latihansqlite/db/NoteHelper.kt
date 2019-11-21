package com.rahmanaulia.latihansqlite.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rahmanaulia.latihansqlite.db.DatabaseContract.NoteColumns.Companion.ID
import com.rahmanaulia.latihansqlite.db.DatabaseContract.NoteColumns.Companion.TABLE_NOTE
import java.sql.SQLException

class NoteHelper (context: Context){

    companion object{
        private const val DATABASE_TABLE = TABLE_NOTE
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: NoteHelper? = null
        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): NoteHelper{
            if (INSTANCE == null){
                synchronized(SQLiteOpenHelper::class){
                    if (INSTANCE == null){
                        INSTANCE = NoteHelper(context)
                    }
                }
            }
            return INSTANCE as NoteHelper
        }
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close(){
        databaseHelper.close()

        if (database.isOpen){
            database.close()
        }
    }

    fun queryAll(): Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC"
        )
    }

    fun insert(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE,
            null,
            values)
    }

    fun update(id: String, values: ContentValues?): Int{
        return database.update(DATABASE_TABLE,
            values,
            "$ID = ?",
            arrayOf(id))
    }

    fun deleteByID(id: String): Int{
        return database.delete(DATABASE_TABLE,
            "$ID = $id",
            null)
    }

}