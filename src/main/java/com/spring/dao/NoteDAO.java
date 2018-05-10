package com.spring.dao;

import java.util.List;

import com.spring.model.Note;

public interface NoteDAO {
	
	public boolean saveNote(Note note);

	public boolean deleteNote(int noteId);

	public List<Note> getAllNotes();

	public Note getNoteById(int noteId);

	public boolean UpdateNote(Note note);

}
