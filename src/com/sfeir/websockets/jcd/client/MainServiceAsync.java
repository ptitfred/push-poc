package com.sfeir.websockets.jcd.client;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.websockets.jcd.shared.User;

public interface MainServiceAsync {

	void getDetails(String login, AsyncCallback<User> callback);

	void login(String username, AsyncCallback<Boolean> callback);

	void listClients(AsyncCallback<Set<User>> callback);

	void listOperators(AsyncCallback<Set<User>> callback);

	void makeCall(String operator, String client, AsyncCallback<Void> callback);

}
