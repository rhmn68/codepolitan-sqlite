package com.rahmanaulia.latihansqlite.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rahmanaulia.latihansqlite.model.Note

class NoteAdapter(private val listener: (Note, Int) -> Unit)
    :RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindItem(note: Note, listener: (Note, Int) -> Unit){

        }
    }
}