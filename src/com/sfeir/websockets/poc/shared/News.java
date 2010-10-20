package com.sfeir.websockets.poc.shared;

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (date.getTime() - other.date.getTime() >=1000)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	protected JSONValue toJSON() {
		JSONObject ob = new JSONObject();
		ob.put("date", new JSONString(date.toGMTString()));
		ob.put("message", new JSONString(message));
		return ob;
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void update(JSONValue o) {
		reset();
		JSONObject ob = o.isObject();
		if (ob != null) {
			JSONString str;
			str = ob.get("date").isString();
			this.date = new Date(str.stringValue());
			str = ob.get("message").isString();
			if (str != null) message = str.stringValue();
		}
		
	}

	private void reset() {
		this.date = null;
		this.message = null;
	}
}
