package com.rahmanaulia.latihansqlite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahmanaulia.latihansqlite.R
import com.rahmanaulia.latihansqlite.model.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(private val listener: (Note, Int) -> Unit)
    :RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
    var listNotes = ArrayList<Note>()
        set(listNotes){
            if (this.listNotes.size > 0){
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false))
    }

    override fun getItemCount(): Int = this.listNotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(this.listNotes[position], listener)
    }

    fun addItem(note: Note){
        this.listNotes.add(note)
        notifyItemInserted(this.listNotes.size - 1)
    }

    fun updateItem(position: Int, note: Note){
        this.listNotes[position] = note
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int){
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindItem(note: Note, listener: (Note, Int) -> Unit){
            itemView.tvTitle.text = note.title
            itemView.tvDescription.text = note.description
            itemView.setOnClickListener {
                listener(note, adapterPosition)
            }
        }
    }
}