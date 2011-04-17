package com.sfeir.websockets.cti.client;

import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.sfeir.websockets.users.client.UserService;
import com.sfeir.websockets.users.client.UserServiceAsync;
import com.sfeir.websockets.users.model.User;

public class CTIEntryPoint implements EntryPoint, ClickHandler {

	private UserServiceAsync service = GWT.create(UserService.class);
	
	private ListBox clients;
	private ListBox operators;

	private Button callButton;
	
	private String wsHost = "localhost:7777";
	
	@Override
	public void onModuleLoad() {
		String wsHost = Location.getParameter("ws.host");
		if (wsHost != null && wsHost.length()>0) {
			this.wsHost = wsHost;
		}

		clients = new ListBox();
		service.listClients(new AsyncCallback<Set<User>>() {
			
			@Override
			public void onSuccess(Set<User> result) {
				clients.clear();
				if (result.size()>0) {
					for (User u : result) {
						clients.addItem(u.getFullName(), u.getUsername());
					}
				} else {
					clients.addItem("No clients found...");	
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				clients.addItem("No clients found...");
			}
		});
		operators = new ListBox();
		service.listOperators(new AsyncCallback<Set<User>>() {
			
			@Override
			public void onSuccess(Set<User> result) {
				operators.clear();
				if (result.size()>0) {
					for (User u : result) {
						operators.addItem(u.getFullName(), u.getUsername());
					}
				} else {
					operators.addItem("No operator found...");	
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				operators.clear();
				operators.addItem("No operator found...");
			}
		});
		
		RootPanel main = RootPanel.get("main");
		Panel p = new FlowPanel();
		p.add(clients);
		p.add(new InlineLabel(" is calling "));
		p.add(operators);
		callButton = new Button("Call", this);
		p.add(callButton);
		main.add(p);
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == callButton) {
			String client = clients.getValue(clients.getSelectedIndex());
			String operator = operators.getValue(operators.getSelectedIndex());
			makeCall(client, operator);
		}
	}

	private void makeCall(String client, String operator) {
		service.makeCall(this.wsHost, operator, client, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
