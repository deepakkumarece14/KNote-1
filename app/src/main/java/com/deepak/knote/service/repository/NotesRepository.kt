package com.deepak.knote.service.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.deepak.knote.service.db.MyNoteDatabase
import com.deepak.knote.service.db.Note
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.anko.doAsync

class NotesRepository(context: Context) {
    private var database : MyNoteDatabase? = MyNoteDatabase.getInstance(context)
    private lateinit var liveNoteList : LiveData<List<Note>>
    private lateinit var noteList: MutableList<Note>

    fun getAllNotes(): LiveData<List<Note>> {
        runBlocking {
            async(CommonPool) {
                liveNoteList = database?.noteDao()?.getAllNotes()!!
                return@async liveNoteList
            }.await()
        }
        return liveNoteList
    }

    fun getAllNotesList(): MutableList<Note> {
        runBlocking {
            async(CommonPool) {
                noteList = database?.noteDao()?.getAllNotesList()!!
                return@async noteList
            }.await()
        }
        return noteList
    }

    fun getNoteById(id: Int): LiveData<List<Note>> {
        runBlocking {
            async(CommonPool) {
                liveNoteList = database?.noteDao()?.getNoteById(id)!! as MutableLiveData<List<Note>>
                return@async liveNoteList
            }.await()
        }
        return liveNoteList
    }

    fun insertNote(note: Note) = doAsync { database?.noteDao()?.insertNote(note) }

    fun updateNote(note: Note) = doAsync { database?.noteDao()?.updateNote(note) }

    fun deleteNote(note: Note) = doAsync { database?.noteDao()?.deleteNote(note) }
}