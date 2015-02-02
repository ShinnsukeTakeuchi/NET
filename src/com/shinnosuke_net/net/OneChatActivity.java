package com.shinnosuke_net.net;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shinnosuke_net.net.tool.*;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.java_websocket.util.*;
import org.java_websocket.server.*;
import org.java_websocket.handshake.*;
import org.java_websocket.framing.*;
import org.java_websocket.exceptions.*;
import org.java_websocket.drafts.*;
import org.java_websocket.client.*;
import org.json.JSONException;
import org.json.JSONObject;

public class OneChatActivity extends Activity implements OnClickListener {
	/* チャットデータ用リスト */
	List<CustomData> chatData = new ArrayList<CustomData>();
	/* チャット画面用リストビュー変数 */
	private ListView chatTimeLine;
	/* チャットデータ用アダプター変数 */
	private CustomAdapter customAdapter;

	private WebSocketClient socket;

	/* 個人の情報 */
	private JSONObject userProfileJson = new JSONObject();

	/* 日付初期化 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm");

	Handler handler = new Handler();
	
	private EditText postMesseage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_chat);

		System.out.println(Build.PRODUCT);
		java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
		java.lang.System.setProperty("java.net.preferIPv4Stack", "true");

		chatTimeLine = (ListView) findViewById(R.id.oneChatTimeLine);
		postMesseage = (EditText) findViewById(R.id.ocEditMessage);
//		postMesseage.setBackgroundColor(Color.rgb(254, 154, 46));

		Date date = new Date();

		CustomData customData = new CustomData();
		customData.setUserId("BestOwner");
		customData.setUserName("運営者");
		customData.setMesseage("ようこそチャットルームへ。");
		customData.setPostDate(sdf.format(date));
		chatData.add(customData);
		
//		Handler handler= new Handler();
//
//		handler.post(new Runnable() {
//		  @Override
//		  public void run() {
//			  setData(chatData);
//			  try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		  }
//		});
		
		setData(chatData);

		searchRoom();
	}

	private void webSocketAccess(String roomId) {
		try {
			URI uri = new URI(
					"ws://kojikoji.mydns.jp:8080/WebSocketServer/Post/"
							+ roomId);

			socket = new WebSocketClient(uri, new Draft_17()) {
				
				
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
						// customData.setUserId("userId");
						// customData.setUserName("userName");
						// customData.setMesseage(arg0);
						// customData.setPostDate("date");

						chatData.add(customData);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					handler.post(new Runnable(){
						
						@Override
						public void run() {
							setData(chatData);
							onDrow();
						}
					}); 

				}

				@Override
				public void onOpen(ServerHandshake arg0) {
					// TODO Auto-generated method stub
					System.out.println("onOpen:Access");
					System.out.println(arg0);
				}
			};

			socket.connect();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.one_chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View v) {
		System.out.println("onClick:Acess");
		
		SpannableStringBuilder sp = (SpannableStringBuilder) postMesseage
				.getText();
		System.out.println(sp.toString());
		// 入力された文字がなければ何もしない
		if (sp.toString() != null && sp.toString().length() == 0) {
			System.out.println("文字入力されてない");
			return;
		}
		JSONObject sendJson = new JSONObject();
		try {
			Date date = new Date();
			sendJson.put("userId", userProfileJson.getString("userId"));
			sendJson.put("userName", userProfileJson.getString("userName"));
			sendJson.put("messeage", sp.toString());
			sendJson.put("date", sdf.format(date));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JsonEscape escJson = new JsonEscape();

		System.out.println(escJson.getString(sendJson));
		System.out.println("文字入力されていた");

		socket.send(escJson.getString(sendJson));
		
		// テキストボックスの初期化
		postMesseage.setText("");
	}
	
	public void onChatClose(View v) {
		socket.onClose(0, "退室しました", true);
		socket.close();
		Toast.makeText(this, "Chatを終了しました", Toast.LENGTH_SHORT).show();
		this.finish();
	}

	private void setData(List<CustomData> chatData) {
		customAdapter = new CustomAdapter(this, 0, chatData);
		chatTimeLine.setAdapter(customAdapter);

		onDrow();
	}

	private void onDrow() {
		System.out.println("onDrow:Acess");
		chatTimeLine.setSelection(chatData.size());
	}

	private void searchRoom() {
		// チャットルームの検索
		// chatroom access
		InputStream input;
		try {
			input = new FileInputStream(
					Environment.getExternalStorageDirectory() + "/"
							+ "user_profile.json");
			int size = input.available();
			byte[] buffer = new byte[size];
			input.read(buffer);
			input.close();
			String json = new String(buffer);
			userProfileJson = new JSONObject(json);

			System.out.println("userId:" + userProfileJson.getString("userId"));

			// HTTPアクセス
			HttpGetTask httpGetTask = new HttpGetTask();
			httpGetTask.execute(userProfileJson.getString("userId"));

			// ここからWebSocket
			boolean taskWait = true;
			while(taskWait) {
				try {
					Thread.sleep(500);
					if(httpGetTask.getRoomId()!=null && !"".equals(httpGetTask.getRoomId())) {
						taskWait = false;
						webSocketAccess(httpGetTask.getRoomId());
						System.out.println("RoomID:"+httpGetTask.getRoomId());
					} else {
						System.out.println("RoomID not get");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
