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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logo == null) ? 0 : logo.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Team other = (Team) obj;
		if (logo == null) {
			if (other.logo != null)
				return false;
		} else if (!logo.equals(other.logo))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
