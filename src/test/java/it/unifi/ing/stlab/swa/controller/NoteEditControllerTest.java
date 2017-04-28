package it.unifi.ing.stlab.swa.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.stlab.swa.bean.UserSessionBean;
import it.unifi.ing.stlab.swa.dao.NoteDao;
import it.unifi.ing.stlab.swa.dao.UserDao;
import it.unifi.ing.stlab.swa.model.ModelFactory;
import it.unifi.ing.stlab.swa.model.Note;
import it.unifi.ing.stlab.swa.model.User;

public class NoteEditControllerTest {

	private NoteEditController noteEditController;
	private NoteDao noteDao;
	private UserDao userDao;
	private UserSessionBean userSession;
	
	private Note note;
	private User user;
	
	private Long noteId;
	private Long userId;
	
	@Before
	public void setUp() throws InitializationError {
		noteEditController = new NoteEditController();
		noteDao = mock(NoteDao.class);
		userDao = mock(UserDao.class);
		noteId = Long.valueOf(10);
		userId = Long.valueOf(7);
		note = ModelFactory.note();
		user = ModelFactory.user();
		note.setUser(user);
		
		userSession = new UserSessionBean();
		userSession.setUserId(userId);
		
		try {
			FieldUtils.writeField(note, "id", noteId, true);
			FieldUtils.writeField(user, "id", userId, true);
			FieldUtils.writeField(noteEditController, "noteDao", noteDao, true);
			FieldUtils.writeField(noteEditController, "userDao", userDao, true);
			FieldUtils.writeField(noteEditController, "userSession", userSession, true);
			FieldUtils.writeField(noteEditController, "noteId", noteId.toString(), true);
			
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
	}

	@Test
	public void testGetNoteAdding() throws InitializationError {
		when(userDao.findById(userId)).thenReturn(user);
		
		try {
			FieldUtils.writeField(noteEditController, "addFlag", "true", true);
			
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
		
		when(noteDao.findById(noteId)).thenReturn(note);
		
		assertEquals(user, noteEditController.getNote().getUser());
		assertNotNull(noteEditController.getNote().getCreationDate());
		assertTrue(noteEditController.getAdding());
	}
	
	@Test
	public void testGetNoteEditing() {
		when(noteDao.findById(noteId)).thenReturn(note);
		
		assertEquals(note, noteEditController.getNote());
		assertFalse(noteEditController.getAdding());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetNoteNoId() throws InitializationError {
		try {
			FieldUtils.writeField(noteEditController, "noteId", null, true);
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
		
		noteEditController.getNote();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetNoteBadIdFormat() throws InitializationError {
		try {
			FieldUtils.writeField(noteEditController, "noteId", "hello", true);
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
		
		noteEditController.getNote();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testGetNoteNotOwner() {
		when(noteDao.findById(noteId)).thenReturn(note);
		userSession.setUserId(Long.valueOf(1000));
		
		noteEditController.getNote();
	}
	
	@Test
	public void testSave() {
		when(noteDao.findById(noteId)).thenReturn(note);
		
		assertEquals("home", noteEditController.save());
		verify(noteDao, times(1)).save(note);
	}

}
