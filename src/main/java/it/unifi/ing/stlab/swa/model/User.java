package it.unifi.ing.stlab.swa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

	private Long id;
	private String uuid;
	private String email;
	private String password;
	
	User(){
	}
	
	public User(String uuid) {
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
