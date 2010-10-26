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
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.sfeir.websockets.common.utils.WebSocketCallback;
import com.sfeir.websockets.common.utils.WebSocketClient;
import com.sfeir.websockets.jcd.shared.User;
import com.sfeir.websockets.jcd.view.UserDetails;

public class ClientSurvey implements EntryPoint, WebSocketCallback {

	private enum Status {
		OFF, LOGGED, CONNECTED
	}
	@SuppressWarnings("unused")
	private Status state = Status.OFF;

	private MainServiceAsync service = GWT.create(MainService.class);
	
	private WebSocketClient ws = new WebSocketClient(this);
	private static final String WS_LOCAL = "ws://localhost:7777/pushws/jcd/ws";

	private RootPanel loginPanel;
	private TextBox loginUser;
	private PasswordTextBox loginPassword;
	private Button loginGo;
	private Button logoffGo;

	private int userId;

	private Element statePanel;

	@Override
	public void onModuleLoad() {
		statePanel = RootPanel.get("state").getElement();
		
		loginPanel = RootPanel.get("login");
		loginUser = new TextBox();
		loginPassword = new PasswordTextBox();
		loginPanel.add(loginUser);
		loginPanel.add(loginPassword);
		loginGo = new Button("Login");
		loginPanel.add(loginGo);
		loginGo.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (event.getSource()==loginGo) {
					service.login(loginUser.getValue(), loginUser.getValue(), new AsyncCallback<Integer>() {
						
						@Override
						public void onSuccess(Integer result) {
							ackLogin(result);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Login failed");
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
					service.logout(userId, new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							ackLogoff();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Log out failed...");
						}
					});
				}
			}
		});
	}

	private void ackLogin(Integer result) {
		// Change state and activate other widgets
		this.userId = result.intValue();
		setState(Status.LOGGED);
		connect();
	}
	
	private void ackLogoff() {
		setState(Status.OFF);
		ws.send("DECON " + this.userId);
		ws.close();
	}
	
	private void displayPopup(User userDetails) {
		// TODO Create a richer widget to display user details;
		UserDetails d = new UserDetails(userDetails);
		d.show();
	}

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
		if (this.userId >= 0) {
			setState(Status.LOGGED);
			triggerReconnect();
		} else {
			setState(Status.OFF);
		}
	}
	
	private void connect() {
		if (this.userId >= 0) {
			ws.connect(WS_LOCAL);
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
			int user = Integer.parseInt(message);
			service.getDetails(user, new AsyncCallback<User>() {

				@Override
				public void onSuccess(User result) {
					displayPopup(result);
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}
			});
		} catch (NumberFormatException e) {
			// Something unexpected written on the websocket...
			GWT.log("Unexpected data on WS", e);
		}

	}

}
