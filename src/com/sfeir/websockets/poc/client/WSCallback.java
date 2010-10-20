package com.sfeir.websockets.poc.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.sfeir.websockets.poc.externals.WebSocketCallback;

public class WSCallback implements WebSocketCallback {

	@Override
	public void connected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnected() {
		Window.alert("Connection lost!");

	}

	@Override
	public void message(String message) {
		JSONValue parse = JSONParser.parse(message);
		if (parse.isObject() != null) {
			JSONObject data = parse.isObject();
			JSONString type = data.get("type").isString();
			if (type != null) {
				String sType = type.stringValue();
				if ("news".equals(sType)) {
					
				}
			}
		} else {
			
		}
	}

}
