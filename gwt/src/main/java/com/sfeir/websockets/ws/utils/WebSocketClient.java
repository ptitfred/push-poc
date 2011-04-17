package com.sfeir.websockets.ws.utils;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Very simple approach to using a websocket from within gwt
 * @author Peter Bridge - 2010-01-02
 * @version $Id: $
 */
public class WebSocketClient {
    private WebSocketCallback callback;
	private boolean lock;

    /**
     * @param callback - used for websocket callback
     */
    public WebSocketClient(WebSocketCallback callback) {
        this.callback = callback;
    }
    
    public WebSocketClient() {
	}

    public void setCallback(WebSocketCallback callback) {
    	if (!lock)
		this.callback = callback;
	}
    
    private final void onopen() {
    	lock = true;
        callback.connected();
    }

    private final void onclose() {
        callback.disconnected();
        lock = false;
    }

    private final void onmessage(String message) {
        callback.message(message);
    }
    
    public final static int CONNECTING = 0;
    public final static int OPEN = 1;
    public final static int CLOSED = 2;
    
    private JavaScriptObject _ws = null; // cf issue 4469

    public String getTextStatus() {
    	int i = getStatus();
    	switch (i) {
    	case CONNECTING:
    		return "CONNECTING";
    	case OPEN:
    		return "OPEN";
    	case CLOSED:
    		return "CLOSED";
    		default:
    			return "UNKNOWN";
    	}
    }
    
    public native int getStatus() /*-{
    	var ws = this.@com.sfeir.websockets.ws.utils.WebSocketClient::_ws;
    	if (ws) {
    		return ws.readyState;
    	}
    }-*/;

    public native void connect(String server) /*-{
    	var that = this;
        if (!window.WebSocket) {
            alert("WebSocket connections not supported by this browser");
            return;
        }
        console.log("WebSocket connecting to " + server);
    	var ws = this.@com.sfeir.websockets.ws.utils.WebSocketClient::_ws = new WebSocket(server);
        console.log("WebSocket connected " + ws.readyState);
        
        ws.onopen = function() {
            if(!ws) {
                console.log("WebSocket not really opened?");
                console.log("WebSocket["+server+"]._ws.onopen()");
                return;
            }
             console.log("onopen, readyState: " + ws.readyState);
             that.@com.sfeir.websockets.ws.utils.WebSocketClient::onopen()();
             console.log("onopen done");
        };


        ws.onmessage = function(response) {
            console.log("WebSocket _onmessage() data="+response.data);
            if (response.data) {
                that.@com.sfeir.websockets.ws.utils.WebSocketClient::onmessage(Ljava/lang/String;)( response.data );
            }
        };

        ws.onclose = function(m) {
             console.log("WebSocket["+server+"]_ws.onclose() state: "+
             	ws.readyState);
             that.@com.sfeir.websockets.ws.utils.WebSocketClient::onclose()();
        };
        
        ws.onerror = function() {
        	console.log("WebSocket[" + server + "]_ws.onerror() state: " + ws.readyState); 
        };
        
        console.log("WebSocket is ready! " + ws.readyState);
                
    }-*/;

    public native void send(String message) /*-{
    	var that = this;
		var ws = this.@com.sfeir.websockets.ws.utils.WebSocketClient::_ws;
        if (ws) {
            console.log("WebSocket sending:"+message);
            ws.send(message);
        } else {
            alert("WebSocket not connected...");
        }
    }-*/;

    public native void close() /*-{
    	var ws = this.@com.sfeir.websockets.ws.utils.WebSocketClient::_ws;
        console.log("WebSocket closing");
        ws.close();
        console.log("WebSocket closed");
    }-*/;

}