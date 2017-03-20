package it.unifi.ing.stlab.swa.model;

import java.util.UUID;

public class ModelFactory {

	private ModelFactory(){
	}
	
	public static User user() {
		return new User(UUID.randomUUID().toString());
	}
	
	public static Note note() {
		return new Note(UUID.randomUUID().toString());
	}
}
