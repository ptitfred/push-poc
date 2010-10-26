package com.sfeir.websockets.jcd.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.websockets.jcd.shared.User;

public class UserDetails extends Composite {

	private static UserDetailsUiBinder uiBinder = GWT
			.create(UserDetailsUiBinder.class);

	interface UserDetailsUiBinder extends UiBinder<Widget, UserDetails> {
	}

	@UiField PopupPanel popupPanel;
	@UiField InlineLabel userName;
	@UiField InlineLabel profile;
	@UiField InlineLabel notes;
	
	public UserDetails(User u) {
		initWidget(uiBinder.createAndBindUi(this));
		userName.setText(u.getUsername());
		profile.setText(u.getProfile().name());
		notes.setText(u.getNotes());
	}

	public void show() {
		popupPanel.show();
	}
	
	public void hide() {
		popupPanel.hide();
	}

}
