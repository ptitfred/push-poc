package com.sfeir.websockets.poc.shared;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class Team extends JSON {

	private String name;
	private String logo;

	public Team() {
	}
	
	public Team(String name, String logo) {
		super();
		this.name = name;
		this.logo = logo;
	}

	public String getLogo() {
		return logo;
	}
	public String getName() {
		return name;
	}
	
	@Override
	protected JSONValue toJSON() {
		JSONObject o = new JSONObject();
		o.put("name", new JSONString(name));
		o.put("logo", new JSONString(logo));
		return o;
	}

	@Override
	protected void update(JSONValue o) {
		JSONObject ob = o.isObject();
		if (ob != null) {
			JSONString str;
			str = ob.get("name").isString();
			if (str!=null) this.name = str.stringValue();
			str = ob.get("logo").isString();
			if (str!=null) this.logo = str.stringValue();
		}

	}

}
