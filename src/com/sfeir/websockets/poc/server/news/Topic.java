package com.sfeir.websockets.poc.server.news;

public final class Topic {
	
	private static Topic SINGLETON = null;

	public static interface Listener {
		void handleMessage(String message);
	}
	
	public static Topic getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new Topic();
		}
		return SINGLETON;
	}

	private Listener listener;
	
	private Topic() {
	}

	public void register(Listener listener) {
		this.listener = listener;
	}
	
	public void sendMessage(String message) {
		listener.handleMessage(message);
	}
	
}
