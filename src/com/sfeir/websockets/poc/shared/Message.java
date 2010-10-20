package com.sfeir.websockets.poc.shared;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class Message extends JSON {

	public final static Message read(String json) {
		Message m = new Message();
		m.update(JSONParser.parse(json));
		return m;
	}
	
	public final static String write(Message m) {
		return m.toJSON().toString();
	}
	
	public enum Type {NEWS, SCORE};
	
	private Type type;
	private News news;
	private Score score;
	
	public Message() {
		reset();
	}
	
	public Message(News news) {
		this.type = Type.NEWS;
		this.news = news;
		this.score = null;
	}
	
	public Message(Score score) {
		this.type = Type.SCORE;
		this.news = null;
		this.score = score;
	}
	
	public Type getType() {
		return type;
	}
	public News getNews() {
		return news;
	}
	public Score getScore() {
		return score;
	}
	
	@Override
	protected JSONValue toJSON() {
		JSONObject o = new JSONObject();
		
		o.put("type", new JSONString(type.toString()));
		switch(type) {
		case NEWS:
			o.put("news", news.toJSON());
			break;
		case SCORE:
			o.put("score", score.toJSON());
			break;
		}
		
		return o;
	}
	
	@Override
	protected void update(JSONValue v) {
		reset();
		
		if (v.isObject()!=null) {
			JSONObject o = v.isObject();
			JSONValue jsonValue = o.get("type");
			if (jsonValue.isString() != null) {
				this.type = Type.valueOf(jsonValue.isString().stringValue());
				switch(type) {
				case NEWS:
					this.news = new News();
					this.news.update(o.get("news"));
					break;
				case SCORE:
					this.score = new Score();
					this.score.update(o.get("score"));
					break;
				}
			}
		}
	}
	private void reset() {
		type = null;
		news = null;
		score = null;
	}
	
}
