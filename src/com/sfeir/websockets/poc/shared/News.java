package com.sfeir.websockets.poc.shared;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class News extends JSON {
	
	private Date date;
	private String message;

	public News() {
		reset();
	}
	
	public News(Date date, String message) {
		super();
		this.date = date;
		this.message = message;
	}

	public Date getDate() {
		return date;
	}
	
	public String getMessage() {
		return message;
	}
	
	private static final DateFormat df = new SimpleDateFormat();
	
	@Override
	protected JSONValue toJSON() {
		JSONObject ob = new JSONObject();
		ob.put("date", new JSONString(df.format(date)));
		ob.put("message", new JSONString(message));
		return ob;
	}

	@Override
	protected void update(JSONValue o) {
		reset();
		JSONObject ob = o.isObject();
		if (ob != null) {
			JSONString str;
			str = ob.get("date").isString();
			try {
				if (str != null) this.date = df.parse(str.stringValue());
			} catch (ParseException e) {
				e.printStackTrace();
				this.date = null;
			}
			str = ob.get("message").isString();
			if (str != null) message = str.stringValue();
		}
		
	}

	private void reset() {
		this.date = null;
		this.message = null;
	}
}
