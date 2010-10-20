package com.sfeir.websockets.poc.externals;

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
    
    @SuppressWarnings("unused")
    private final void onopen() {
    	lock = true;
        callback.connected();
    }

    @SuppressWarnings("unused")
    private final void onclose() {
        callback.disconnected();
        lock = false;
    }

    @SuppressWarnings("unused")
    private final void onmessage(String message) {
        callback.message(message);
    }

    public native void connect(String server) /*-{
        var that = this;
        if (!window.WebSocket) {
            alert("WebSocket connections not supported by this browser");
            return;
        }
        console.log("WebSocket connecting to "+server);
        that._ws=new WebSocket(server);
        console.log("WebSocket connected "+that._ws.readyState);

        that._ws.onopen = function() {
            if(!that._ws) {
                console.log("WebSocket not really opened?");
                console.log("WebSocket["+server+"]._ws.onopen()");
                return;
            }
             console.log("onopen, readyState: "+that._ws.readyState);
             that.@com.sfeir.websockets.poc.externals.WebSocketClient::onopen()();
             console.log("onopen done");
        };


        that._ws.onmessage = function(response) {
            console.log("WebSocket _onmessage() data="+response.data);
            if (response.data) {
                that.@com.sfeir.websockets.poc.externals.WebSocketClient::onmessage(Ljava/lang/String;)( response.data );
            }
        };

        that._ws.onclose = function(m) {
             console.log("WebSocket["+server+"]_ws.onclose() state:"+that._ws.readyState);
             that.@com.sfeir.websockets.poc.externals.WebSocketClient::onclose()();
        };
    }-*/;

    public native void send(String message) /*-{
        if (this._ws) {
            console.log("WebSocket sending:"+message);
            this._ws.send(message);
        } else {
            alert("not connected!" + this._ws);
        }
    }-*/;

    public native void close() /*-{
        console.log("WebSocket closing");
        this._ws.close();
        console.log("WebSocket closed");
    }-*/;

}