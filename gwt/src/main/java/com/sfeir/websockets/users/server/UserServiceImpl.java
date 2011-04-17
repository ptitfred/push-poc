package com.sfeir.websockets.users.server;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.websockets.users.client.UserService;
import com.sfeir.websockets.users.model.Profile;
import com.sfeir.websockets.users.model.User;

@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	private static Map<String, User> users;
	private static Set<User> clients;
	private static Set<User> operators;
	
	private static int sequence = 0;
	static {
		users = new HashMap<String, User>();
		clients = new HashSet<User>();
		operators = new HashSet<User>();
		
		registerUser(new User(sequence++, "operateur1", "Opérateur 1", Profile.OPERATOR, "Paris", User.Language.FRENCH, ""));
		registerUser(new User(sequence++, "operateur2", "Opérateur 2", Profile.OPERATOR, "Paris", User.Language.FRENCH, ""));
		registerUser(new User(sequence++, "client1", "Client 1", Profile.CLIENT, "London", User.Language.ENGLISH, "Ne parle que anglais."));
		registerUser(new User(sequence++, "client2", "Client 2", Profile.CLIENT, "Geneva", User.Language.FRENCH, "A déjà appelé le 3 sept."));
		registerUser(new User(sequence++, "client3", "Client 3", Profile.CLIENT, "Paris", User.Language.FRENCH, ""));
	}
	
	static void registerUser(User u) {
		users.put(u.getUsername(), u);
		switch (u.getProfile()) {
		case CLIENT:
			clients.add(u);
			break;
		case OPERATOR:
			operators.add(u);
			break;
		}
	}
	
	@Override
	public boolean login(String username) {
		User user = users.get(username);
		return user != null && Profile.OPERATOR.equals(user.getProfile());
	}
	
	@Override
	public User getDetails(String userId) {
		return users.get(userId);
	}
	
	@Override
	public Set<User> listClients() {
		return clients;
	}
	
	@Override
	public Set<User> listOperators() {
		return operators;
	}

	@Override
	public void makeCall(String host, String operator, String client) {
		StringBuffer path = new StringBuffer();
		path.append("/pushws/jcd/notif").append("?");
		path.append("operator=").append(operator).append("&");
		path.append("client=").append(client);
		try {
			get("http://" + host, path.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void get(String serverPath, String relativePath) throws Exception {
		URL url = new URL(serverPath + relativePath);
		URLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(10000); //Total request time is limited to 30s on appengine
		urlConnection.getInputStream();
	}

}
