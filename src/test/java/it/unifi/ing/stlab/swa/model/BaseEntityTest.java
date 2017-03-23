package it.unifi.ing.stlab.swa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class BaseEntityTest {

	private FakeBaseEntity e1, e2, e3;
	
	@Before
	public void setUp() {
		String uuid1 = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		e1 = new FakeBaseEntity(uuid1);
		e2 = new FakeBaseEntity(uuid2);
		e3 = new FakeBaseEntity(uuid1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullUUID() {
		new FakeBaseEntity(null);
	}
	
	@Test
	public void testEquals() {
		assertEquals(e1, e1);
		assertEquals(e1, e3);
		assertEquals(e3, e3);
	}
	
	@Test
	public void testHashCode() {
		assertEquals(e1.hashCode(), e1.hashCode());
		assertEquals(e1.hashCode(), e3.hashCode());
		assertEquals(e3.hashCode(), e3.hashCode());
	}
	
	@Test
	public void testNotEquals() {
		assertNotEquals(e1, e2);
		assertNotEquals(e3, e2);
		
		assertNotEquals(e1, null);
		assertNotEquals(e1, "Ciao");
	}
	
	class FakeBaseEntity extends BaseEntity {
		public FakeBaseEntity(String uuid) {
			super(uuid);
		}
	}
	
}
