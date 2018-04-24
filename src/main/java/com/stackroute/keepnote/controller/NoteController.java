package com.stackroute.keepnote.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mysql.cj.conf.BooleanPropertyDefinition.AllowableValues;
import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.model.Note;

/*
 * Annotate the class with @Controller annotation.@Controller annotation is used to mark 
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 */
@Controller
public class NoteController {
	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the persistence data. Each note
	 * should contain Note Id, title, content, status and created date. 2. Add a new
	 * note which should contain the note id, title, content and status. 3. Delete
	 * an existing note 4. Update an existing note
	 * 
	 */

	/*
	 * Autowiring should be implemented for the NoteDAO and Note. Please note that
	 * we should not create any object using the new keyword
	 * 
	 */
	@Autowired
	NoteDAO noteDao;

//	@Autowired
	Note note = new Note();

	/*
	 * Define a handler method to read the existing notes from the database and add
	 * it to the ModelMap which is an implementation of Map, used when building
	 * model data for use with views. it should map to the default URL i.e. "/"
	 */
	@GetMapping("/")
	public String showHome(Model map) {
		map.addAttribute("allNotes", noteDao.getAllNotes());
		
		return "index";
	}

	/*
	 * Define a handler method which will read the NoteTitle, NoteContent,
	 * NoteStatus from request parameters and save the note in note table in
	 * database. Please note that the CreatedAt should always be auto populated with
	 * system time and should not be accepted from the user. Also, after saving the
	 * note, it should show the same along with existing messages. Hence, reading
	 * note has to be done here again and the retrieved notes object should be sent
	 * back to the view using ModelMap This handler method should map to the URL
	 * "/add".
	 */

	@PostMapping("/add")
	public String addNewNoteToDB(HttpServletRequest req, Model map) {
		
		String noteTitle = req.getParameter("noteTitle");
		String noteContent = req.getParameter("noteContent");
		String noteStatus = req.getParameter("noteStatus");

		if (noteTitle == null || noteTitle.isEmpty() || noteContent == null || noteContent.isEmpty()) {
			map.addAttribute("error", "All fields are mandatory");

//			map.addAttribute("allNotes", noteDao.getAllNotes());
			return "index";
		}

		try {
			note.setNoteTitle(noteTitle);
			note.setNoteContent(noteContent);
			note.setNoteStatus(noteStatus);
			note.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

			noteDao.saveNote(note);
		} catch (NumberFormatException ne) {
			map.addAttribute("error", "Note Id must be in numeric ");
		}

//		map.addAttribute("allNotes", noteDao.getAllNotes());
		return "redirect:/";
	}

	/*
	 * Define a handler method which will read the NoteId from request parameters
	 * and remove an existing note by calling the deleteNote() method of the
	 * NoteRepository class.This handler method should map to the URL "/delete".
	 */
	@GetMapping("/delete")
	public String deleteNoteFromDB(HttpServletRequest req, Model map) {
		String noteId = req.getParameter("noteId");

		if (noteId.isEmpty()) {
			map.addAttribute("error", "All fields are mandatory");

//			map.addAttribute("allNotes", noteDao.getAllNotes());
			return "index";
		}

		try {
			noteDao.deleteNote(Integer.parseInt(noteId));
		} catch (NumberFormatException ne) {
			map.addAttribute("error", "Note Id must be in numeric ");
		}

//		map.addAttribute("allNotes", noteDao.getAllNotes());
		return "redirect:/";
	}

	/*
	 * Define a handler method which will update the existing note. This handler
	 * method should map to the URL "/update".
	 */
	@PostMapping("/update")
	public String updateNoteToDB(HttpServletRequest req, Model map) {
		String noteId = req.getParameter("noteId");
		String noteTitle = req.getParameter("noteTitle");
		String noteContent = req.getParameter("noteContent");
		String noteStatus = req.getParameter("noteStatus");

		if (noteTitle.isEmpty() || noteContent.isEmpty()) {
			map.addAttribute("error", "All fields are mandatory");

			map.addAttribute("allNotes", noteDao.getAllNotes());
			return "redirect:/";
		}

		try {
			note.setNoteId(Integer.parseInt(noteId));
			note.setNoteTitle(noteTitle);
			note.setNoteContent(noteContent);
			note.setNoteStatus(noteStatus);
			note.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

			noteDao.UpdateNote(note);
		} catch (NumberFormatException ne) {
			map.addAttribute("error", "Note Id must be in numeric ");
		}

//		map.addAttribute("allNotes", noteDao.getAllNotes());
		return "redirect:/";
	}
}
