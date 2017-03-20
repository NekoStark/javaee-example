package it.unifi.ing.stlab.swa.controller;

import java.util.Date;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import it.unifi.ing.stlab.swa.bean.UserSessionBean;
import it.unifi.ing.stlab.swa.dao.NoteDao;
import it.unifi.ing.stlab.swa.dao.UserDao;
import it.unifi.ing.stlab.swa.model.ModelFactory;
import it.unifi.ing.stlab.swa.model.Note;

@Model
public class NoteEditController {

	@Inject
	private NoteDao noteDao;
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private UserSessionBean userSession;
	
	private Note note;
	private Boolean adding;
	
	public Note getNote() {
		if(note == null) {
			initNote();
		}
		return note;
	}
	
	@Transactional
	public String save() {
		noteDao.save(note);
		return "home";
	}
	
	public Boolean getAdding() {
		return adding;
	}
	public void setAdding(Boolean adding) {
		this.adding = adding;
	}
	
	/**
	 * N.B. this bean has a lot of common code with NoteViewController
	 * for the sake of clarity, we have decided to keep them separated
	 * instead of adding a super class with common code.
	 */
	protected void initNote() {
		ServletRequest request = (ServletRequest)FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRequest();
		
		if(Boolean.valueOf( request.getParameter("add") ) ) {
			adding = true;
			note = ModelFactory.note();
			note.setCreationDate( new Date() );
			note.setUser( userDao.findById(userSession.getUserId()) );
			return;
		}
		
		String noteId = request.getParameter("id");
		if(StringUtils.isEmpty(noteId)) {
			throw new IllegalArgumentException("id not found");
		}
		
		try {
			Long id = Long.valueOf(noteId);
			note = noteDao.findById(id);
			
			if(!note.getUser().getId().equals( userSession.getUserId())) {
				throw new IllegalStateException("you are not the owner of the note");
			}
			
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("id not a number");
		}
	}
	
}
