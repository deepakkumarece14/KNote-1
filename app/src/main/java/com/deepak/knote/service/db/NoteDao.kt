package com.deepak.knote.service.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * The Data Access Object(Dao) implementation for the Room
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<MutableList<Note>>

    @Query("SELECT * FROM notes")
    fun getAllNotesList(): MutableList<Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): LiveData<MutableList<Note>>

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}