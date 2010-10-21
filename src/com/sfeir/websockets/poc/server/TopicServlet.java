package com.sfeir.websockets.poc.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class TopicServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;
	
	private final Set<TopicWS> _members = new HashSet<TopicWS>();
	
//	private int count=0;
//	private String[] history = new String[50];
//	private int index=history.length-1;

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
	        System.out.println("ws: new peer");
//	        try {
//				for (int k=index; k<index+count; k++ ) {
//					_outbound.sendMessage(WebSocket.SENTINEL_FRAME, history[k%history.length]);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		
		@Override
		public void onMessage(byte frame, byte[] data,int offset, int length) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMessage(byte frame, String data) {
//			synchronized(history) {
//				if (count<history.length) count++;
//				history[index--] = data;
//				if (index<0) index = history.length-1;
//			}
	        System.out.println("ws: message to " + _members.size() + " peers");
	        System.out.println("    " + frame + " - " + data);
			try {
				for (TopicWS peer : _members) {
					peer._outbound.sendMessage(frame, data);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onDisconnect() {
			_members.remove(this);
	        System.out.println("ws: peer gone");
		}
		
	}

}
