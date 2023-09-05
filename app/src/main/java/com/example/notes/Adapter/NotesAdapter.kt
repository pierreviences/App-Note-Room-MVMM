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

class NotesAdapter(private val context: Context, val listener: NotesItemClickListener): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

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

        holder.notes_layout.setOnClickListener {
            listener.onItemClicked(NotesList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener{
            listener.onLongItemClicked(NotesList[holder.position], holder.notes_layout)
            true
        }

    }

    fun randomColor() : Int{
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    interface NotesItemClickListener{
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }


}