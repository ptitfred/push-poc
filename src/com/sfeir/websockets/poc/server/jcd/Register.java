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
	
	private Map<Integer, Outbound> outbounds;
	
	private Register() {
		outbounds = new HashMap<Integer, Outbound>();
	}
	
	public void add(int i, Outbound socket) {
		System.out.println("v " + i);
		this.outbounds.put(i, socket);
	}
	
	public Outbound get(int i) {
		return this.outbounds.get(i);
	}
	
	public void remove(int i) {
		System.out.println("^ " + i);
		this.outbounds.remove(i);
	}
	
}
