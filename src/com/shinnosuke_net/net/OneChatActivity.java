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
	/* チャットデータリスト */
	ChatData chatData = new ChatData();
	/* チャット画面用リストビュー変数 */
	private ListView chatTimeLine;
	/* チャットデータ用アダプター変数 */
	private CustomAdapter customAdapter;
	/* 個人の情報 */
	private JSONObject userProfileJson = new JSONObject();
	/* 日付初期化 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy.MM.dd HH:mm");

	WebSocketHelper wsh;

	private EditText postMesseage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_chat);
		System.out.println("OneChatActivity:onCreate");

		System.out.println(Build.PRODUCT);
		java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
		java.lang.System.setProperty("java.net.preferIPv4Stack", "true");

		chatTimeLine = (ListView) findViewById(R.id.oneChatTimeLine);
		postMesseage = (EditText) findViewById(R.id.ocEditMessage);

		Date date = new Date();

		CustomData customData = new CustomData();
		customData.setUserId("BestOwner");
		customData.setUserName("システムくん");
		customData.setMesseage("ようこそチャットルームへ。");
		customData.setPostDate(sdf.format(date));
		chatData.add(customData);
		 
		searchRoom();
	}

	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("OneChatActivity:onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("OneChatActivity:onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("OneChatActivity:onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("OneChatActivity:onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("OneChatActivity:onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		System.out.println("OneChatActivity:onDestroy");
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
		System.out.println("OneChatActivity:onClick");
		
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

		wsh.send(escJson.getString(sendJson));
		
		// テキストボックスの初期化
		postMesseage.setText("");
	}
	
	public void onChatClose(View v) {
		System.out.println("OneChatActivity:onChatClose");
		wsh.onClose(0, "退室しました", true);
		wsh.close();
		Toast.makeText(this, "Chatを終了しました", Toast.LENGTH_SHORT).show();
		this.finish();
	}

	public void setData(List<CustomData> chatData) {
		System.out.println("OneChatActivity:setDate");
		customAdapter = new CustomAdapter(this, 0, chatData);
		chatTimeLine.setAdapter(customAdapter);

		onDrow();
	}

	private void onDrow() {
		System.out.println("OneChatActivity:onDrow");
		System.out.println("onDrow:Acess");
		chatTimeLine.setSelection(chatData.size());
	}

	private void searchRoom() {
		System.out.println("OneChatActivity:searchRoom");
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

			boolean taskWait = true;
			while(taskWait) {
				try {
					Thread.sleep(500);
					if(httpGetTask.getRoomId()!=null && !"".equals(httpGetTask.getRoomId())) {
						taskWait = false;
						wsh = new WebSocketHelper(httpGetTask.getRoomId());
						System.out.println("RoomID:"+httpGetTask.getRoomId());
					} else {
						System.out.println("RoomID not get");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					Toast.makeText(this, "チャットが始まりませんでした。(WSE:02)", Toast.LENGTH_SHORT).show();
					this.finish();
				} catch (URISyntaxException e) {
					e.printStackTrace();
					Toast.makeText(this, "チャットが始まりませんでした。(WSE:01)", Toast.LENGTH_SHORT).show();
					this.finish();
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
