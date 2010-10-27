package com.sfeir.websockets.poc.server.jcd;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

@SuppressWarnings("serial")
public class WSServlet extends WebSocketServlet {

	@Override
	protected WebSocket doWebSocketConnect(
			HttpServletRequest request, String protocol) {
		return new LWebSocket();
	}
	
	class LWebSocket implements WebSocket {

		private Outbound outbound;
		private String userId = null;
		
		@Override
		public void onConnect(Outbound outbound) {
			System.out.println("ws: new client");
			this.outbound = outbound;
		}

		/**
		 * <pre>Protocole: 
		 * CON userId
		 * DECON userId</pre>
		 * @see org.eclipse.jetty.websocket.WebSocket#onMessage(byte, java.lang.String)
		 */
		@Override
		public void onMessage(byte opcode, String data) {
			String[] d = data.split(" ", 3);
			final String cmd = d[0];
			final String args = d[1];
			if ("CON".equals(cmd)) {
				userId = args;
				Register.getInstance().add(args, outbound);
			} else if ("DECON".equals(cmd)) {
				Register.getInstance().remove(args);
			}
		}

		@Override
		public void onMessage(byte opcode, byte[] data, int offset, int length) {
			onMessage(opcode, new String(data, offset, length));
		}

		@Override
		public void onDisconnect() {
			System.out.println("ws: client gone");
			if (userId != null) {
				Register.getInstance().remove(userId);
			}
			this.outbound = null;
			this.userId = null;
		}
		
	}

}
