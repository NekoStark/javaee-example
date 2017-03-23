package it.unifi.ing.stlab.swa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.stlab.swa.model.ModelFactory;
import it.unifi.ing.stlab.swa.model.User;

public class UserDaoJpaTest extends JpaTest {

	private UserDao userDao;
	private User user;
	
	@Override
	protected void init() throws InitializationError {
		user = ModelFactory.user();
		user.setEmail("user@example.com");
		user.setPassword("mysecretpass");
		
		entityManager.persist(user);
		
		userDao = new UserDao();
		try {
			FieldUtils.writeField(userDao, "entityManager", entityManager, true);
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
	}

	@Test
	public void testSave() {
		User anotherUser = ModelFactory.user();
		anotherUser.setEmail("user2@example.com");
		anotherUser.setPassword("anothersecretpass");
		
		userDao.save(anotherUser);
		
		assertEquals( anotherUser, entityManager
									.createQuery("from User where uuid =:uuid", User.class)
									.setParameter("uuid", anotherUser.getUuid())
									.getSingleResult());
				
	}
	
	@Test
	public void testSaveUpdate() {
		user = entityManager.find(User.class, user.getId());
		String changedEmail = "changed@example.com";
		user.setEmail(changedEmail);
		
		userDao.save(user);
		
		assertEquals( changedEmail, entityManager
									.createQuery("from User where uuid =:uuid", User.class)
									.setParameter("uuid", user.getUuid())
									.getSingleResult()
									.getEmail());
				
	}
	
	@Test
	public void testFindById() {
		User result = userDao.findById(user.getId());
		
		assertEquals( user.getId(), result.getId() );
		assertEquals( user.getUuid(), result.getUuid() );
		assertEquals( user.getEmail(), result.getEmail() );
		assertEquals( user.getPassword(), result.getPassword() );
	}
	
	@Test
	public void testLogin() {
		User credentials = ModelFactory.user();
		credentials.setEmail("user@example.com");
		credentials.setPassword("mysecretpass");
		
		assertEquals( user, userDao.login(credentials) );
	}
	
	@Test
	public void testLoginWrongCredentials() {
		User credentials = ModelFactory.user();
		credentials.setEmail("user2@example.com");
		credentials.setPassword("mywrongpass");
		
		assertNull( userDao.login(credentials) );
	}
	
	
}
