package com.sfeir.websockets.jcd.client;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.websockets.jcd.shared.User;

public interface MainServiceAsync {

	void login(String username, String password, AsyncCallback<Integer> callback);

	void logout(int t, AsyncCallback<Void> callback);

	void getDetails(int userId, AsyncCallback<User> callback);

	void listUsers(AsyncCallback<Set<User>> callback);

}
