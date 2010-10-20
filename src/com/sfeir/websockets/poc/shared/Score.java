package com.sfeir.websockets.poc.shared;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class Score extends JSON {

	private Team home;
	private String score;
	private Team visitor;
	
	public Score() {
		reset();
	}
	
	public Score(Team home, String score, Team visitor) {
		super();
		this.home = home;
		this.score = score;
		this.visitor = visitor;
	}

	public Team getHome() {
		return home;
	}
	public String getScore() {
		return score;
	}
	public Team getVisitor() {
		return visitor;
	}
	
	private void reset() {
		this.home = new Team();
		this.visitor = new Team();
		this.score = "";
	}
	
	@Override
	protected JSONValue toJSON() {
		JSONObject o = new JSONObject();
		o.put("home", home.toJSON());
		o.put("score", new JSONString(score));
		o.put("visitor", visitor.toJSON());
		return null;
	}

	@Override
	protected void update(JSONValue o) {
		reset();
		JSONObject ob = o.isObject();
		if (ob !=null) {
			this.home.update(ob.get("home"));
			this.visitor.update(ob.get("visitor"));
			JSONString str = ob.get("score").isString();
			if (str != null) {
				this.score = str.stringValue();
			}
		}

	}


}
