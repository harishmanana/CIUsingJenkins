package com.spring.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.model.Note;

@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	@Autowired
	SessionFactory sessioFactory;
	
	public NoteDAOImpl() {
		
	}
	
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessioFactory = sessionFactory;
	}

	public boolean saveNote(Note note) {
		Session session = sessioFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(note);
		tx.commit();
		session.close();
		
		return true;
	}

	public boolean deleteNote(int noteId) {
		Session session = sessioFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("delete from Note where noteId = :noteId");
		query.setParameter("noteId", noteId);
		query.executeUpdate();
		tx.commit();
		session.close();
		
		return true;
	}

	public List<Note> getAllNotes() {
		Session session = sessioFactory.openSession();
		Query query = session.createQuery("from Note note order by note.createdAt desc");
		List<Note> allNotes = (List<Note>) query.getResultList();
		return allNotes;
	}

	public Note getNoteById(int noteId) {
		Session session = sessioFactory.openSession();
		Query query = session.createQuery("from Note note where note.noteId = :noteId");
		query.setParameter("noteId", noteId);
		
		List<Note> n = query.getResultList(); 
		return n.get(0);
	}

	/* Update existing note */
	public boolean UpdateNote(Note note) {
		Session session = sessioFactory.openSession();		
		Transaction tx = session.beginTransaction();
		session.update(note);
		tx.commit();
		return true;
	}
}
