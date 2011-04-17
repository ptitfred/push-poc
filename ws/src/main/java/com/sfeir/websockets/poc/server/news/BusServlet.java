package com.sfeir.websockets.poc.server.news;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BusServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(req.getInputStream()));
		StringBuffer msg = new StringBuffer();
		String buf;
		while ((buf = r.readLine()) != null) msg.append(buf).append(" ");
		try {
			Topic.getInstance().sendMessage(msg.toString());
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
}
