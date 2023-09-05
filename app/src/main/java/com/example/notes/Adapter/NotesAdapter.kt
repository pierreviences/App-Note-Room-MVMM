package com.example.notes.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Models.Note
import com.example.notes.R
import kotlin.random.Random

class NotesAdapter(private val context: Context): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val NotesList : ArrayList<Note>()
    private val fullList : ArrayList<Note>()
    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val note = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int = NotesList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val currentNote = NotesList[position]
        holder.title.text  = currentNote.title
        holder.title.isSelected = true
        holder.note.text  = currentNote.note
        holder.date.text  = currentNote.date
        holder.date.isSelected = true

        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(), null))
    }

  



}