package com.sfeir.websockets.poc.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.websockets.poc.shared.Message;
import com.sfeir.websockets.poc.shared.News;
import com.sfeir.websockets.ws.utils.WebSocketCallback;
import com.sfeir.websockets.ws.utils.WebSocketClient;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PushPOC implements EntryPoint, ClickHandler, KeyPressHandler {

	private static final String PUBLIC_WS = "ws://kercoin.org:7777/pushws/ws";
	private static final String LOCAL_WS = "ws://localhost:7777/pushws/ws";
	
	private static final int MAX_NEWS = 30;
	private static final int DEFAULT_RETRY = 1000; // 1sec
	private static final int MAX_RETRY = 60000; // 1min
	private WebSocketClient ws = new WebSocketClient();
	private TextBox newsField;
	private Button pushButton;
	private Widget serviceStatus;

	private boolean alreadyConnected = false;
	private boolean officiallyConnected = false;
	private transient String currentUrl = null;
	
	private int retry = DEFAULT_RETRY;
	private TextBox wsUrl;
	private Button connectButton;
	private Anchor publicUrl;
	private Anchor localUrl;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel adminPanel = RootPanel.get("admin");
		newsField = new TextBox();
		newsField.getElement().setId("news-field");
		newsField.setEnabled(false);
		pushButton = new Button("Send");
		pushButton.setEnabled(false);
		adminPanel.add(newsField);
		adminPanel.add(pushButton);
		
		serviceStatus = RootPanel.get("service");
		
		RootPanel wsOption = RootPanel.get("wsOption");
		wsUrl = new TextBox();
		wsUrl.setText(LOCAL_WS);
		connectButton = new Button("Connect");
		connectButton.addClickHandler(this);
		wsOption.add(new InlineLabel("WS: "));
		wsOption.add(wsUrl);
		wsOption.add(connectButton);
		
		publicUrl = new Anchor(PUBLIC_WS);
		publicUrl.addClickHandler(this);
		publicUrl.setTitle("Click to use!");
		RootPanel.get("public-url").add(publicUrl);		
		
		localUrl = new Anchor(LOCAL_WS);
		localUrl.addClickHandler(this);
		localUrl.setTitle("Click to use!");
		RootPanel.get("local-url").add(localUrl);		
		
		ws.setCallback(new WebSocketCallback() {
			
			@Override
			public void message(String message) {
				Message m = Message.read(message);
				RootPanel newsPanel = RootPanel.get("news");
				switch(m.getType()) {
				case NEWS:
					if (newsPanel.getWidgetCount() == MAX_NEWS) {
						newsPanel.remove(MAX_NEWS -1);
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
				p.addStyleName("news-item");
				
				p.add(date);
				p.add(msg);
				
				return p;
			}
			
			@Override
			public void connected() {
				retry = DEFAULT_RETRY;
				newsField.setEnabled(true);
				pushButton.setEnabled(true);
				serviceStatus.getElement().setInnerText("online");
				serviceStatus.setStyleName("online");
				if (alreadyConnected) log("Back online!");
				else alreadyConnected = true;
			}

			@Override
			public void disconnected() {
				newsField.setEnabled(false);
				pushButton.setEnabled(false);
				serviceStatus.getElement().setInnerText("offline");
				serviceStatus.setStyleName("offline");
				if (officiallyConnected)
					triggerReconnect();
			}
			
		});
		
		pushButton.addClickHandler(this);
		newsField.addKeyPressHandler(this);
		wsUrl.addKeyPressHandler(this);

	}

	private void connect(String url) {
		ws.connect(url);
		this.currentUrl = url;
		this.alreadyConnected = true;
	}
		
	private void disconnect() {
		ws.close();
		this.currentUrl = null;
		this.alreadyConnected = false;
	}

	public void triggerReconnect() {
		Timer t = new Timer() {
			@Override
			public void run() {
				connect(currentUrl);
			}
		};
		log("Connection lost, retry in " + (retry / 1000) + " seconds.");
		t.schedule(retry);
		retry = Math.min(2*retry, MAX_RETRY);
	}
	
	private void log(String message) {
		RootPanel.get("logger").getElement().setInnerText(message);
		GWT.log(message);
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == pushButton) {
		DeferredCommand.addCommand(new Command() {
			
			@Override
			public void execute() {
//				if (ws.getStatus()==WebSocketClient.OPEN) {
					ws.send(Message.write(new Message(new News(new Date(), newsField.getValue()))));
//				} else {
//					Window.alert("Not connected, but didn't see the disconnection...");
//				}
				newsField.setValue("", false);
			}
		});
		} else if (event.getSource() == connectButton) {
			if (officiallyConnected) {
				disconnect();
				connectButton.setText("Connect");
			} else {
				connect(wsUrl.getText());
				connectButton.setText("Disconnect");
			}

			// Toggle UI status:
			wsUrl.setEnabled(officiallyConnected);
			pushButton.setEnabled(officiallyConnected);
			newsField.setEnabled(officiallyConnected);
			if (officiallyConnected) newsField.setFocus(true); else wsUrl.setFocus(true);
			
			officiallyConnected = !officiallyConnected;
		}
		else if (event.getSource() == publicUrl && !officiallyConnected) {
			updateWsUrl(PUBLIC_WS);
		}
		else if (event.getSource() == localUrl && !officiallyConnected) {
			updateWsUrl(LOCAL_WS);
		}
	}

	private void updateWsUrl(String url) {
		wsUrl.setText(url);
		wsUrl.setFocus(true);
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		if (event.getCharCode() == KeyCodes.KEY_ENTER) {
			if (event.getSource() == newsField) {
				pushButton.click();
			} else if (event.getSource() == wsUrl) {
				connectButton.click();
			}
		}
		
	}
}
