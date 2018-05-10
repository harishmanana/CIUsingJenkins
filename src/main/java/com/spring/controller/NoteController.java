package com.spring.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.dao.NoteDAO;
import com.spring.model.Note;

@Controller
public class NoteController {
	@Autowired
	NoteDAO noteDao;

	Note note = new Note();

	@GetMapping("/")
	public String showHome(Model map) {
		map.addAttribute("allNotes", noteDao.getAllNotes());
		
		return "index";
	}

	@PostMapping("/add")
	public String addNewNoteToDB(HttpServletRequest req, Model map) {
		
		String noteTitle = req.getParameter("noteTitle");
		String noteContent = req.getParameter("noteContent");
		String noteStatus = req.getParameter("noteStatus");

		if (noteTitle == null || noteTitle.isEmpty() || noteContent == null || noteContent.isEmpty()) {
			map.addAttribute("error", "All fields are mandatory");

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

		return "redirect:/";
	}

	@GetMapping("/delete")
	public String deleteNoteFromDB(HttpServletRequest req, Model map) {
		String noteId = req.getParameter("noteId");

		if (noteId.isEmpty()) {
			map.addAttribute("error", "All fields are mandatory");

			return "index";
		}

		try {
			noteDao.deleteNote(Integer.parseInt(noteId));
		} catch (NumberFormatException ne) {
			map.addAttribute("error", "Note Id must be in numeric ");
		}

		return "redirect:/";
	}

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

		return "redirect:/";
	}
}
