package com.sfeir.websockets.poc.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.sfeir.websockets.poc.externals.WebSocketCallback;
import com.sfeir.websockets.poc.externals.WebSocketClient;
import com.sfeir.websockets.poc.shared.Message;
import com.sfeir.websockets.poc.shared.News;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PushPOC implements EntryPoint, ClickHandler {

	private WebSocketClient ws = new WebSocketClient();
	private TextBox newsField;
	private Button pushButton;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel adminPanel = RootPanel.get("admin");
		newsField = new TextBox();
		pushButton = new Button("Push");
		adminPanel.add(newsField);
		adminPanel.add(pushButton);
		
		ws.setCallback(new WebSocketCallback() {
			
			@Override
			public void message(String message) {
				Message m = Message.read(message);
				RootPanel newsPanel = RootPanel.get("news");
				switch(m.getType()) {
				case NEWS:
					if (newsPanel.getWidgetCount() == 10) {
						newsPanel.remove(5);
					}
					newsPanel.insert(createNewsLabel(m.getNews()), 0);
					break;
				case SCORE:
//					final Score score = m.getScore();
					break;
				}
			}

			DateTimeFormat DTF = DateTimeFormat.getFormat("HH:mm");
			
			private Panel createNewsLabel(final News news) {
				Label date = new InlineLabel(DTF.format(news.getDate()));
				date.addStyleName("date");
				
				Label msg = new InlineLabel(news.getMessage());
				msg.addStyleName("message");
				
				Panel p = new FlowPanel();
				p.addStyleName("news");
				
				p.add(date);
				p.add(msg);
				
				return p;
			}
			
			@Override
			public void disconnected() {
				
			}
			
			@Override
			public void connected() {
				
			}
		});
		ws.connect((GWT.getHostPageBaseURL()+"ws").replace("http://", "ws://"));
		
		
		
		pushButton.addClickHandler(this);

	}

	@Override
	public void onClick(ClickEvent event) {
		DeferredCommand.addCommand(new Command() {
			
			@Override
			public void execute() {
				ws.send(Message.write(new Message(new News(new Date(), newsField.getValue()))));
			}
		});
	}
}
