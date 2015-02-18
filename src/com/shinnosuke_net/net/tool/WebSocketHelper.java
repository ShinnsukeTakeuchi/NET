package com.shinnosuke_net.net.tool;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import com.shinnosuke_net.net.OneChatActivity;

import android.os.Handler;

public class WebSocketHelper extends WebSocketClient {
	ChatData chatData = new ChatData();
	Handler handler = new Handler();

	public WebSocketHelper(String roomId) throws URISyntaxException {
		super(new URI("ws://kojikoji.mydns.jp:8080/WebSocketServer/Post/" + roomId), new Draft_17());
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		System.out.println("onClose:Access");		
	}

	@Override
	public void onError(Exception arg0) {
		System.out.println("onError:Access");
		arg0.printStackTrace();
	}

	@Override
	public void onMessage(String arg0) {
		System.out.println("onMessage:Access");
		System.out.println(arg0);

		JSONObject getJson = new JSONObject();
		JsonEscape escJson = new JsonEscape();
		getJson = escJson.getJson(arg0);

		CustomData customData = new CustomData();
		try {
			customData.setUserId(getJson.getString("userId"));
			customData.setUserName(getJson.getString("userName"));
			customData.setMesseage(getJson.getString("messeage"));
			customData.setPostDate(getJson.getString("date"));

			chatData.add(customData);
			
			handler.post(new Runnable(){
				@Override
				public void run() {
					setData(chatData.getChatData());
					onDrow();
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onOpen(ServerHandshake arg0) {
		System.out.println("onOpen:Access");
		System.out.println(arg0);
	}

}
