package com.sfeir.websockets.users.client;

import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.websockets.users.model.User;

@RemoteServiceRelativePath("user-service")
public interface UserService extends RemoteService {

	boolean login(String username);
	
	Set<User> listClients();
	Set<User> listOperators();
	
	User getDetails(String userId);
	
	void makeCall(String operator, String client);
}
