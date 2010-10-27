package com.sfeir.websockets.jcd.client;

import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.websockets.jcd.shared.User;

@RemoteServiceRelativePath("main-service")
public interface MainService extends RemoteService {

	boolean login(String username);
	
	Set<User> listClients();
	Set<User> listOperators();
	
	User getDetails(String userId);
	
	void makeCall(String operator, String client);
}
