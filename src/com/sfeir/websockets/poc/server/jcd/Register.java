package com.sfeir.websockets.poc.server.jcd;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.websocket.WebSocket.Outbound;

public class Register {
	
	private static Register INSTANCE; 
	
	public static Register getInstance() {
		if (INSTANCE == null) INSTANCE = new Register();
		return INSTANCE;
	}
	
	private Map<String, Outbound> outbounds;
	
	private Register() {
		outbounds = new HashMap<String, Outbound>();
	}
	
	public void add(String key, Outbound socket) {
		System.out.println("v " + key);
		this.outbounds.put(key, socket);
	}
	
	public Outbound get(String key) {
		return this.outbounds.get(key);
	}
	
	public void remove(String key) {
		System.out.println("^ " + key);
		this.outbounds.remove(key);
	}
	
}
