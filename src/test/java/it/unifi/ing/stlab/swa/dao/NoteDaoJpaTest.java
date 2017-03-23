package it.unifi.ing.stlab.swa.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.stlab.swa.model.ModelFactory;
import it.unifi.ing.stlab.swa.model.Note;
import it.unifi.ing.stlab.swa.model.User;

public class NoteDaoJpaTest extends JpaTest {

	private NoteDao noteDao;
	private User user;
	private Note note;

	@Override
	protected void init() throws InitializationError {
		user = ModelFactory.user();
		user.setEmail("user@example.com");
		user.setPassword("mysecretpass");

		note = ModelFactory.note();
		note.setTitle("a title");
		note.setBody("my body");
		note.setCreationDate(new Date());
		note.setUser(user);
		user.setPassword("mysecretpass");

		entityManager.persist(user);
		entityManager.persist(note);

		noteDao = new NoteDao();
		try {
			FieldUtils.writeField(noteDao, "entityManager", entityManager, true);
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
	}

	@Test
	public void testSave() {
		Note anotherNote = ModelFactory.note();
		anotherNote.setTitle("another title");
		anotherNote.setBody("another body");
		anotherNote.setCreationDate(new Date());
		anotherNote.setUser(user);

		noteDao.save(anotherNote);

		assertEquals(anotherNote, entityManager
									.createQuery("from Note where uuid = :uuid", Note.class)
									.setParameter("uuid", anotherNote.getUuid())
									.getSingleResult());

	}
	
	@Test
	public void testSaveUpdate() {
		String changedTitle = "changed title";
		note.setTitle(changedTitle);

		noteDao.save(note);

		assertEquals(note.getTitle(), entityManager
									.createQuery("from Note where uuid = :uuid", Note.class)
									.setParameter("uuid", note.getUuid())
									.getSingleResult()
									.getTitle());

	}

	@Test
	public void testFindById() {
		Note result = noteDao.findById(note.getId());

		assertEquals(note.getId(), result.getId());
		assertEquals(note.getUuid(), result.getUuid());
		assertEquals(note.getTitle(), result.getTitle());
		assertEquals(note.getBody(), result.getBody());
		assertEquals(note.getCreationDate(), result.getCreationDate());
		assertEquals(note.getUser(), result.getUser());
	}

	@Test
	public void testFindByUserId() {
		List<Note> result = noteDao.findByUserId(user.getId());

		assertEquals(1, result.size());
		assertEquals(note.getUuid(), result.get(0).getUuid());
	}

	@Test
	public void testFindByWrongUserId() {
		List<Note> result = noteDao.findByUserId(Long.valueOf(9999));

		assertEquals(0, result.size());
	}

}
