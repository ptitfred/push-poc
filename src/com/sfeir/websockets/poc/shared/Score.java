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

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((home == null) ? 0 : home.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((visitor == null) ? 0 : visitor.hashCode());
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
		Score other = (Score) obj;
		if (home == null) {
			if (other.home != null)
				return false;
		} else if (!home.equals(other.home))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (visitor == null) {
			if (other.visitor != null)
				return false;
		} else if (!visitor.equals(other.visitor))
			return false;
		return true;
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
		return o;
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
