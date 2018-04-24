package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus 
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */
	@Autowired
	SessionFactory sessioFactory;
	
	public NoteDAOImpl() {
		
	}
	
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessioFactory = sessionFactory;
	}

	/*
	 * Save the note in the database(note) table.
	 */
	public boolean saveNote(Note note) {
		Session session = sessioFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(note);
		tx.commit();
		session.close();
		
		return true;
	}

	/*
	 * Remove the note from the database(note) table.
	 */
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

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	public List<Note> getAllNotes() {
		Session session = sessioFactory.openSession();
		Query query = session.createQuery("from Note note order by note.createdAt desc");
		List<Note> allNotes = (List<Note>) query.getResultList();
		return allNotes;
	}

	/*
	 * retrieve specific note from the database(note) table
	 */
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
