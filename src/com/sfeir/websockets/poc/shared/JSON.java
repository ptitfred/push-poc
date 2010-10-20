package com.sfeir.websockets.poc.shared;

import com.google.gwt.json.client.JSONValue;

abstract class JSON {
	protected abstract JSONValue toJSON();
	protected abstract void update(JSONValue o);
}
