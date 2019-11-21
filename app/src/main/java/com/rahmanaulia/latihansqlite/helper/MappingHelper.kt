package com.rahmanaulia.latihansqlite.helper

import android.database.Cursor
import com.rahmanaulia.latihansqlite.db.DatabaseContract.NoteColumns.Companion.DESCRIPTION
import com.rahmanaulia.latihansqlite.db.DatabaseContract.NoteColumns.Companion.ID
import com.rahmanaulia.latihansqlite.db.DatabaseContract.NoteColumns.Companion.TITLE
import com.rahmanaulia.latihansqlite.model.Note

object MappingHelper {

    fun mapCursorToArrayList(noteCursor: Cursor)
            : ArrayList<Note> {
        val noteList = ArrayList<Note>()
        noteCursor.moveToFirst()
        while (noteCursor.moveToNext()){
            val id = noteCursor
                .getInt(noteCursor.getColumnIndexOrThrow(ID))
            val title = noteCursor
                .getString(noteCursor.getColumnIndexOrThrow(TITLE))
            val description = noteCursor
                .getString(noteCursor.getColumnIndexOrThrow(DESCRIPTION))
            noteList.add(Note(id, title, description))
        }
        return noteList
    }

}