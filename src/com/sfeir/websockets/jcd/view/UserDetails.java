package com.sfeir.websockets.jcd.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.websockets.users.model.User;

public class UserDetails extends Composite {

	private static UserDetailsUiBinder uiBinder = GWT
			.create(UserDetailsUiBinder.class);

	interface UserDetailsUiBinder extends UiBinder<Widget, UserDetails> {
	}

	@UiField DivElement userName;
	@UiField SpanElement profile;
	@UiField SpanElement city;
	@UiField SpanElement language;
	@UiField DivElement notes;
	@UiField Anchor closeButton;
	
	public UserDetails(User u, ClickHandler handler) {
		initWidget(uiBinder.createAndBindUi(this));
		userName.setInnerText(u.getFullName());
		profile.setInnerText(u.getProfile().name());
		city.setInnerText(u.getCity());
		language.setInnerText(u.getLanguage().name());
		notes.setInnerText(u.getNotes());
		if (handler != null) {
			closeButton.addClickHandler(handler);
		}
	}

}
