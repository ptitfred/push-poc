package com.sfeir.websockets.poc.client;

import com.google.gwt.core.client.EntryPoint;
import com.sfeir.websockets.poc.externals.WebSocketCallback;
import com.sfeir.websockets.poc.externals.WebSocketClient;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PushPOC implements EntryPoint {

	private WebSocketCallback callback = new WSCallback();
	private WebSocketClient ws = new WebSocketClient(callback );

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		ws.connect("/pushpoc/ws");
	}
}
