package com.spring.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.config.ApplicationContextConfig;
import com.spring.dao.NoteDAO;
import com.spring.dao.NoteDAOImpl;
import com.spring.model.Note;

@RunWith(SpringRunner.class)
@Transactional
@ContextConfiguration(classes = { ApplicationContextConfig.class })
public class NoteDAOImplTest {

	@Autowired
	private SessionFactory sessionFactory;

	private NoteDAO noteDAO;
	private Note note;

	@Before
	public void setUp() {
		noteDAO = new NoteDAOImpl(sessionFactory);
		note = new Note(1, "Testing-1", "Unit test for DAO", "active", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

	}

	@After
	public void tearDown() {
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE  from Note");
		query.executeUpdate();
	}

	@Test
	@Rollback(true)
	public void testSaveNoteSuccess() {

		noteDAO.saveNote(note);
		List<Note> notes = noteDAO.getAllNotes();
		assertEquals("Testing-1", notes.get(0).getNoteTitle());
		noteDAO.deleteNote(note.getNoteId());

	}

	@Test
	@Rollback(true)
	public void testSaveNoteFailure() {
		noteDAO.saveNote(note);
		List<Note> notes = noteDAO.getAllNotes();
		assertNotEquals("Testing-2", notes.get(0).getNoteTitle());
		noteDAO.deleteNote(note.getNoteId());
	}

	@Test
	@Rollback(true)
	public void testDeleteNoteSuccess() {
		noteDAO.saveNote(note);
		Note noteData = noteDAO.getNoteById(note.getNoteId());
		boolean status = noteDAO.deleteNote(noteData.getNoteId());
		assertEquals(true, status);

	}

	@Test
	@Rollback(true)
	public void testGetNoteById() {

		noteDAO.saveNote(note);
		Note noteData = noteDAO.getNoteById(note.getNoteId());
		assertEquals(note, noteData);
		
		noteDAO.deleteNote(note.getNoteId());

	}

	@Test
	@Rollback(true)
	public void testUpdateNote() {
		noteDAO.saveNote(note);
		Note noteData = noteDAO.getNoteById(note.getNoteId());
		noteData.setNoteContent("Unit testing for DAO layer");
		noteData.setCreatedAt(LocalDateTime.now());
		boolean status = noteDAO.UpdateNote(noteData);
		Note updatedNote = noteDAO.getNoteById(noteData.getNoteId());
		assertEquals("Unit testing for DAO layer", updatedNote.getNoteContent());
		assertEquals(true, status);
		noteDAO.deleteNote(updatedNote.getNoteId());

	}

	@Test
	@Rollback(true)
	public void testGetAllNotes() {
		Note note1 = new Note(1, "Testing-1", "Unit test for DAO", "active", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		Note note2 = new Note(2, "Testing-2", "Unit test for DAO", "active", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		noteDAO.saveNote(note1);
		noteDAO.saveNote(note2);
		List<Note> notes = noteDAO.getAllNotes();
		assertEquals(2, notes.size());
		noteDAO.deleteNote(note1.getNoteId());
		noteDAO.deleteNote(note2.getNoteId());
	}
}
