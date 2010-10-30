package com.sfeir.websockets.jcd.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.sfeir.websockets.jcd.view.UserDetails;
import com.sfeir.websockets.users.client.UserService;
import com.sfeir.websockets.users.client.UserServiceAsync;
import com.sfeir.websockets.users.model.User;
import com.sfeir.websockets.ws.utils.WebSocketCallback;
import com.sfeir.websockets.ws.utils.WebSocketClient;

public class ClientSurvey implements EntryPoint, WebSocketCallback {

	private enum Status {
		OFF, LOGGED, CONNECTED
	}
	private Status state = Status.OFF;

	private UserServiceAsync service = GWT.create(UserService.class);
	
	private WebSocketClient ws = new WebSocketClient(this);
	private static final String WS_PATH = "/pushws/jcd/ws";
	private static final String WS_URL = "ws://localhost:7777" +  WS_PATH;
	private String wsUrl = WS_URL;

	private RootPanel loginPanel;
	private TextBox loginUser;
//	private PasswordTextBox loginPassword;
	private Button loginGo;
	private Button logoffGo;

	private String userId;

	private Element statePanel;

	@Override
	public void onModuleLoad() {
		String wsHost = com.google.gwt.user.client.Window.Location.getParameter("ws.host");
		if (wsHost != null && wsHost.length()>0) {
			this.wsUrl = "ws://" + wsHost + WS_PATH;
			GWT.log("Websocket will be: " + this.wsUrl);
		}
		
		statePanel = RootPanel.get("state").getElement();
		
		loginPanel = RootPanel.get("login");
		loginUser = new TextBox();
//		loginPassword = new PasswordTextBox();
		loginPanel.add(loginUser);
//		loginPanel.add(loginPassword);
		loginGo = new Button("Login");
		loginPanel.add(loginGo);
		loginGo.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (event.getSource()==loginGo) {
					ClientSurvey.this.service.login(loginUser.getValue(), new AsyncCallback<Boolean>() {
						
						@Override
						public void onSuccess(Boolean result) {
							if (result.booleanValue()) doLogin(loginUser.getValue());
							else Window.alert(loginUser.getValue() + " not an operator.");
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Error while logging");
						}
					});
				}
			}
		});
		logoffGo = new Button("Log out");
		logoffGo.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (event.getSource() == logoffGo) {
					doLogoff();
				}
			}
		});
		logoffGo.setEnabled(false);
		loginPanel.add(logoffGo);
		setState(Status.OFF);
	}

	private void doLogin(String userId) {
		// Change state and activate other widgets
		this.userId = userId;
		logoffGo.setEnabled(true);
		loginGo.setEnabled(false);
		loginUser.setEnabled(false);
		setState(Status.LOGGED);
		connect();
	}
	
	private void doLogoff() {
		ws.send("DECON " + this.userId);
		ws.close();
		setState(Status.OFF);
		logoffGo.setEnabled(false);
		loginGo.setEnabled(true);
		loginUser.setEnabled(true);
	}
	
	private void displayPopup(User userDetails) {
		final PopupPanel popup = new PopupPanel();
		UserDetails d = new UserDetails(userDetails, new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				popup.hide();
			}
		});
		log("Details popped for userId=" + userDetails.getId());
		popup.add(d);
		popup.showRelativeTo(RootPanel.get("popups"));
	}
	
	private native void log(String message) /*-{
		console.log(message);
	}-*/;

	public void setState(Status state) {
		this.state = state;
		this.statePanel.setInnerText(state.name());
	}
	
	@Override
	public void connected() {
		ws.send("CON " + this.userId);
		setState(Status.CONNECTED);
	}

	@Override
	public void disconnected() {
		if (this.userId != null) {
			setState(Status.LOGGED);
			triggerReconnect();
		} else {
			setState(Status.OFF);
		}
	}
	
	private void connect() {
		if (this.userId != null) {
			ws.connect(wsUrl);
		}
	}

	private void triggerReconnect() {
		Timer t = new Timer() {
			
			@Override
			public void run() {
				connect();
			}
		};
		t.schedule(15 * 1000);
	}

	@Override
	public void message(String message) {
		try {
			final String user = message;
			service.getDetails(user, new AsyncCallback<User>() {

				@Override
				public void onSuccess(User result) {
					log("Details loaded for userId=" + result.getId());
					displayPopup(result);
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Can't find profile for userId=" + user);
				}
			});
		} catch (NumberFormatException e) {
			// Something unexpected written on the websocket...
			GWT.log("Unexpected data on WS", e);
		}

	}

}
