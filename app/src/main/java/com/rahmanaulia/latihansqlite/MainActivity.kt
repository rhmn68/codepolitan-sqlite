package com.rahmanaulia.latihansqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahmanaulia.latihansqlite.adapter.NoteAdapter
import com.rahmanaulia.latihansqlite.db.NoteHelper
import com.rahmanaulia.latihansqlite.helper.MappingHelper
import com.rahmanaulia.latihansqlite.model.Note
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NoteAdapter
    private lateinit var noteHelper: NoteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNoteHelper()
        onClick()
        initAdapter()
        loadNote()
    }

    private fun loadNote() {
        GlobalScope.launch(Dispatchers.Main){
            progressbar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO){
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0){
                adapter.listNotes = notes
            }else{
                adapter.listNotes = ArrayList()
                Toast.makeText(this@MainActivity
                    , "Tidak ada saat ini"
                    ,Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initNoteHelper() {
        noteHelper = NoteHelper.getInstance(this)
        noteHelper.open()
    }

    private fun initAdapter() {
        adapter = NoteAdapter{note, position ->
            val intent = Intent(this, NoteAddUpdateActivity::class.java)
            intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
            intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
            startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
        }
        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.adapter = adapter
    }

    private fun onClick() {
        fabAdd.setOnClickListener {
            val intent = Intent(this, NoteAddUpdateActivity::class.java)
            startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_ADD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            NoteAddUpdateActivity.REQUEST_ADD -> {
               if (resultCode == NoteAddUpdateActivity.RESULT_ADD){
                   val note = data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE)
                    if (note != null){
                        adapter.addItem(note)
                    }
                   rvNotes.smoothScrollToPosition(adapter.itemCount - 1)
                   Toast.makeText(this, "Satu item berhasil ditambah",
                       Toast.LENGTH_SHORT)
                       .show()
               }
            }
            NoteAddUpdateActivity.REQUEST_UPDATE -> {
                when(resultCode){
                    NoteAddUpdateActivity.RESULT_UPDATE -> {
                        val note  = data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE)
                        val position = data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0)
                        if (position != null) {
                            if (note != null) {
                                adapter.updateItem(position, note)
                                rvNotes.smoothScrollToPosition(position)
                                Toast.makeText(this, "Satu item berhasil diubah",
                                    Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                    NoteAddUpdateActivity.RESULT_DELETE -> {
                        val position = data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0)
                        if (position != null) {
                            adapter.removeItem(position)
                            Toast.makeText(this, "Satu item berhasil dihapus",
                                Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}
