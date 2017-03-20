package it.unifi.ing.stlab.swa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unifi.ing.stlab.swa.model.Note;

public class NoteDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Note> findByUserId(Long userId) {
		return entityManager
				.createQuery("from Note "
						+ "where user.id = :uid", Note.class)
				.setParameter("uid", userId)
				.getResultList();
	}
	
	public Note findById(Long noteId) {
		return entityManager.find(Note.class, noteId);
	}
	
	public void save(Note note) {
		if(note.getId() != null) {
			entityManager.merge(note);
		} else {
			entityManager.persist(note);
		}
	}
	
}
