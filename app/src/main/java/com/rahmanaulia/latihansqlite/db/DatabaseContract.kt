package com.rahmanaulia.latihansqlite.db

object DatabaseContract {
    class NoteColumns{
        companion object{
            const val TABLE_NOTE = "note"
            const val ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
        }
    }
}