package it.unifi.ing.stlab.swa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="notes")
public class Note {

	private Long id;
	private String uuid;
	
	private String title;
	private String body;
	private Date creationDate;
	
	private User user;
	
	Note() {
	}
	
	public Note(String uuid) {
		this.uuid = uuid;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	void setId(Long id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid;
	}
	void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Lob
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@ManyToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
