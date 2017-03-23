package it.unifi.ing.stlab.swa.bean.startup;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;

import it.unifi.ing.stlab.swa.dao.NoteDao;
import it.unifi.ing.stlab.swa.dao.UserDao;
import it.unifi.ing.stlab.swa.model.ModelFactory;
import it.unifi.ing.stlab.swa.model.Note;
import it.unifi.ing.stlab.swa.model.User;

//@Singleton
//@Startup
public class StartupBean {

	@Inject
	private UserDao userDao;
	
	@Inject
	private NoteDao noteDao;
	
	private static final String DATE_PATTERN = "dd/MM/yyyy HH:mm";
	
	@PostConstruct
	@Transactional
	public void init() throws Exception {
		User user1 = user("user1@example.com", "pass1");
		User user2 = user("user2@example.com", "pass2");
		
		userDao.save(user1);
		userDao.save(user2);
		noteDao.save( note(
					"Paperworks", 
					"- Call Social Security\n- Hand tax form\n- Pay bills", 
					DateUtils.parseDate("10/03/2017 11:15", DATE_PATTERN), 
					user1) );
		
		noteDao.save( note(
				"Favorite quotes", 
				"A good laugh is sunshine in the house\nIn love the paradox occurs that two beings become one and yet remain two", 
				DateUtils.parseDate("14/03/2017 13:20", DATE_PATTERN), 
				user1) );
		
		noteDao.save( note(
				"Bash Cheatsheet", 
				"mv <a> <b>\ncp <a> <b>\ncat <file>\nmkdir <path>", 
				DateUtils.parseDate("14/03/2017 09:34", DATE_PATTERN), 
				user2) );
		
		
	}
	
	private User user(String email, String password) {
		User user = ModelFactory.user();
		user.setEmail(email);
		user.setPassword(password);
		
		return user;
	}
	
	private Note note(String title, String body, Date creationDate, User author) {
		Note note = ModelFactory.note();
		note.setTitle(title);
		note.setBody(body);
		note.setCreationDate(creationDate);
		note.setUser(author);
		
		return note;
	}
}
