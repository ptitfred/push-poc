package com.sfeir.websockets.jcd.client;

import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.websockets.jcd.shared.User;

@RemoteServiceRelativePath("main-service")
public interface MainService extends RemoteService {
	
	Set<User> listUsers();
	
	int login(String username, String password);

	void logout(int userId);

	User getDetails(int userId);
}
