package com.spring.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

@Entity
@Table (name="Note")
public class Note {

	@Id
	@Column(name = "note_id")
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer noteId;
	
	@Column(name = "note_title")
	private String noteTitle;
	
	@Column(name = "note_content")
	private String noteContent;
	
	@Column(name = "note_status")
	private String noteStatus;
	
	@Column(name = "note_creation_date")
	private LocalDateTime createdAt;
	
	public Note() {
	}

	public Note(Integer noteId, String noteTitle, String noteContent, String noteStatus, LocalDateTime createdAt) {
		super();
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteStatus = noteStatus;
		this.createdAt = createdAt;
	}

	@Id
	@Column(name = "note_id")
	@GeneratedValue (strategy = GenerationType.AUTO)
	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	
	@Column(name = "note_title")
	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	@Column(name = "note_content")
	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	@Column(name = "note_Status")
	public String getNoteStatus() {
		return noteStatus;
	}

	public void setNoteStatus(String noteStatus) {
		this.noteStatus = noteStatus;
	}

	@Column(name = "note_creation_date")
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public String toString() {
		return getNoteId() + ", " + getNoteTitle() + ", " + getNoteContent() + ", " + getNoteStatus() + ", " + getCreatedAt(); 
	}

	@Override
	public boolean equals(Object arg0) {
		Note n = (Note) arg0;
		
		if(n.getNoteId()==getNoteId() && n.getCreatedAt().equals(this.getCreatedAt()) 
				&& n.getNoteContent().equals(this.getNoteContent()) && n.getNoteStatus().equals(this.getNoteStatus()) 
				&& n.getNoteTitle().equals(this.getNoteTitle()))
			return true;
		
		return false;
	}
}
