package it.unifi.ing.stlab.swa.controller;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import it.unifi.ing.stlab.swa.bean.UserSessionBean;
import it.unifi.ing.stlab.swa.dao.NoteDao;
import it.unifi.ing.stlab.swa.model.Note;

@Model
public class NoteListController {

	@Inject
	private UserSessionBean userSession;
	
	@Inject
	private NoteDao noteDao;
	
	private List<Note> notes;
	
	public List<Note> getNotes() {
		if(notes == null) {
			notes = noteDao.findByUserId( userSession.getUserId() );
		}
		
		return notes;
	}
	
}
