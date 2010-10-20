package com.sfeir.websockets.poc.server;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class SportsResultsServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;
	
	private final Set<SportsResultsWebSocket> _members = new HashSet<SportsResultsWebSocket>();

	@Override
	protected WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
		return new SportsResultsWebSocket();
	}
	
	public class SportsResultsWebSocket implements WebSocket {

	    Outbound _outbound;

		@Override
		public void onConnect(Outbound outbound) {
	        _outbound=outbound;
	        _members.add(this);
		}
		
		@Override
		public void onMessage(byte frame, byte[] data,int offset, int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMessage(byte frame, String data) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onDisconnect() {
			_members.remove(this);
		}
		
	}

}
