package com.sfeir.websockets.poc.shared;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class JSONTest {

	@Test
	public void testMessageReadWrite() {
		News news = new News(new Date(), "Début des matchs de la 3ème journée.");
		Message m = new Message(news);
		Message copy = Message.read(Message.write(m));
		assertEquals(m.getType(), copy.getType());
		assertEquals(m.getNews(), copy.getNews());
	}
	
}
