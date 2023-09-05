package com.example.notes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.Adapter.NotesAdapter
import com.example.notes.Database.NoteDatabase
import com.example.notes.Models.Note
import com.example.notes.Models.NoteViewModel
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selectedNote: Note

    private val updateOne = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            val note = result.data?.getSerializableExtra("note") as? Note
            if(note != null){
                viewModel.updateNote(note)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allnotes.observe(this){
            list ->
            list?.let{
                adapter.updateList(list)
            }
        }

        database = NoteDatabase.getDatabase(this)
    }

    private fun initUI(){
        binding.recycleView.setHasFixedSize(true)
        binding.recycleView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = NotesAdapter(this, this)
        binding.recycleView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                val note = result.data?.getSerializableExtra("note") as? Note
                if(note != null){
                    viewModel.insertNote(note)
                }
            }
        }
        binding.fbAddNote.setOnClickListener {
            val intent = Intent(this, AddNote::class.java)
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newtext: String?): Boolean {
                if(newtext != null){
                    adapter.filterList(newtext)
                }

                return true
            }

        })
    }
}