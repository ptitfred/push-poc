package com.sfeir.websockets.cti.client;

import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.sfeir.websockets.jcd.client.MainService;
import com.sfeir.websockets.jcd.client.MainServiceAsync;
import com.sfeir.websockets.jcd.shared.User;

public class EntryPoint implements com.google.gwt.core.client.EntryPoint, ClickHandler {

	private MainServiceAsync service = GWT.create(MainService.class);
	
	private ListBox clients;
	private ListBox operators;

	private Button callButton;
	
	@Override
	public void onModuleLoad() {
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
		// TODO Call HTTP
		
	}
}
