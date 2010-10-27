package com.sfeir.websockets.poc.server.jcd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket.Outbound;

@SuppressWarnings("serial")
public class NotificationServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] data = new String[2];
		data[0] = req.getParameter("operator");
		data[1] = req.getParameter("client");
		handleRequest(data, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(
				req.getInputStream()));
		String line = r.readLine();
		// on mange uniquement le premier mot :
		String[] data = line.split(" ", 2);
		handleRequest(data, resp);
	}

	private void handleRequest(String[] data, HttpServletResponse resp) {
		try {
			final String operator = data[0];
			Outbound outbound = Register.getInstance().get(operator);
			if (outbound.isOpen()) {
				outbound.sendMessage(data[1]);
				resp.setStatus(HttpServletResponse.SC_OK);
			} else {
				resp.setStatus(HttpServletResponse.SC_GONE);
			}
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

}
