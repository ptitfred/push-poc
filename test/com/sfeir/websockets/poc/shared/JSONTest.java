package com.sfeir.websockets.poc.shared;

import java.util.Date;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class JSONTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.sfeir.websockets.poc.pushpoc";
	}
	
	@Test
	public void testNews() {
		Message original = new Message(new News(new Date(), "Début des matchs de la 3ème journée."));
		String data = Message.write(original);
		System.out.println(data);
		Message copy = Message.read(data);
		assertEquals(original, copy);
	}
	
	public void testScore() {
		Team madrid = new Team("Real Madrid", "http://www.madrid.es/logo.png");
		Team barcelone = new Team("FC Barcelone", "http://www.barca.es/logo.png");
		Message original = new Message(new Score(barcelone, "2-2", madrid));
		String data = Message.write(original);
		System.out.println(data);
		Message copy = Message.read(data);
		assertEquals(original, copy);
	}
	
}
