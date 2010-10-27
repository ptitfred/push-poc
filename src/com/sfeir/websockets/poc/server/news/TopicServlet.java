package com.sfeir.websockets.poc.server.news;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class TopicServlet extends WebSocketServlet implements Topic.Listener {

	private static final long serialVersionUID = 1L;
	
	private final Set<TopicWS> _members = new HashSet<TopicWS>();
	
	public TopicServlet() {
		Topic.getInstance().register(this);
	}
	
//	private int count=0;
//	private String[] history = new String[50];
//	private int index=history.length-1;

	@Override
	protected String checkOrigin(HttpServletRequest request, String host,
			String origin) {
		System.out.println("ws:  host: " + host + " origin: " + origin);
		return super.checkOrigin(request, host, origin);
	}
	
	@Override
	protected WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
		return new TopicWS();
	}
	
	public class TopicWS implements WebSocket {

	    Outbound _outbound;

		@Override
		public void onConnect(Outbound outbound) {
	        _outbound=outbound;
	        _members.add(this);
	        System.out.println("ws: new peer, " + _members.size() + " peers");
		}
		
		@Override
		public void onMessage(byte frame, byte[] data,int offset, int length) {
			
		}

		@Override
		public void onMessage(byte frame, String data) {
	        System.out.println("ws: message to " + _members.size() + " peers");
	        System.out.println("    " + frame + " - " + data);
	        for (TopicWS peer : _members) {
	        	try {
	        		if (peer._outbound.isOpen()) {
	        			peer._outbound.sendMessage(frame, data);
	        		} else {
	        			purge(peer);
	        		}
	        	} catch (IOException e) {
	        		e.printStackTrace();
	        		purge(peer);
	        	}
	        }
		}

		private void purge(TopicWS peer) {
			peer._outbound.disconnect();
    		_members.remove(peer);			
		}

		@Override
		public void onDisconnect() {
			_members.remove(this);
	        System.out.println("ws: peer gone, " + _members.size() + " peers");
		}
		
	}

	@Override
	public void handleMessage(String message) {
        for (TopicWS peer : _members) {
        	try {
        		if (peer._outbound.isOpen()) {
        			peer._outbound.sendMessage(WebSocket.SENTINEL_FRAME, message);
//        		} else {
//        			purge(peer);
        		}
        	} catch (IOException e) {
        		e.printStackTrace();
//        		purge(peer);
        	}
        }
	}

}
