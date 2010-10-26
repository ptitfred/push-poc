package com.sfeir.websockets.jcd.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.websockets.jcd.client.MainService;
import com.sfeir.websockets.jcd.shared.Profile;
import com.sfeir.websockets.jcd.shared.User;

@SuppressWarnings("serial")
public class MainServiceImpl extends RemoteServiceServlet implements MainService {

	private static Map<String, User> users;
	private static Map<Integer, User> indexById;
	private static Set<User> usersOnlyCache;
	
	private static int sequence = 0;
	static {
		users = new HashMap<String, User>();
		indexById = new HashMap<Integer, User>();
		usersOnlyCache = new HashSet<User>();
		
		registerUser(new User(sequence++, "demo", "demo", Profile.ADMIN, ""));
		registerUser(new User(sequence++, "user", "user", Profile.USER, "Ne parle que anglais."));
		registerUser(new User(sequence++, "toto", "toto", Profile.USER, "A déjà résolu le problème ABC."));
	}
	
	static void registerUser(User u) {
		users.put(u.getUsername(), u);
		indexById.put(u.getId(), u);
		if (u.getProfile() == Profile.USER) {
			usersOnlyCache.add(u);
		}
	}
	
	@Override
	public int login(String username, String password) {
		if (password != null) {
			User user = users.get(username);
			if (users.containsKey(username) && password.equals(user.getPassword())) {
				return user.getId();
			}
		}
		return -1;
	}

	@Override
	public void logout(int userId) {
		// Nothing to do a priori.
	}

	@Override
	public User getDetails(int userId) {
		return indexById.get(userId);
	}
	
	@Override
	public Set<User> listUsers() {
		return Collections.unmodifiableSet(usersOnlyCache);
	}

}
