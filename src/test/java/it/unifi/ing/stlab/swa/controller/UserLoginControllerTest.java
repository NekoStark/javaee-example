package it.unifi.ing.stlab.swa.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import it.unifi.ing.stlab.swa.bean.UserSessionBean;
import it.unifi.ing.stlab.swa.dao.UserDao;
import it.unifi.ing.stlab.swa.model.ModelFactory;
import it.unifi.ing.stlab.swa.model.User;

public class UserLoginControllerTest {

	private UserLoginController userLoginController;
	private UserDao userDao;
	private UserSessionBean userSession;
	private User user;

	@Before
	public void setUp() throws InitializationError {
		userLoginController = new UserLoginController();
		
		userDao = mock(UserDao.class);
		
		userSession = new UserSessionBean();
		
		user = ModelFactory.user();
		user.setEmail("user@example.com");
		user.setPassword("pass");
		
		try {
			FieldUtils.writeField(user, "id", Long.valueOf(10), true);
			FieldUtils.writeField(userLoginController, "userDao", userDao, true);
			FieldUtils.writeField(userLoginController, "userSession", userSession, true);
		} catch (IllegalAccessException e) {
			throw new InitializationError(e);
		}
	}

	@Test
	public void testLogin() {
		when(userDao.login( any(User.class) )).thenReturn(user);
		
		String result = userLoginController.login();
		
		assertTrue(result.contains("home"));
		assertEquals(user.getId(), userSession.getUserId());
		assertTrue(userSession.isLoggedIn());
	}
	
	@Test(expected=RuntimeException.class)
	public void testLoginError() {
		when(userDao.login( any(User.class) )).thenReturn(null);
		
		userLoginController.login();
	}
	
	@Test
	public void testLogout() {
		when(userDao.login( any(User.class) )).thenReturn(user);
		
		userLoginController.login();
		String result = userLoginController.logout();
		
		assertTrue(result.contains("home"));
		assertNull(userSession.getUserId());
		assertFalse(userSession.isLoggedIn());
	}

}
