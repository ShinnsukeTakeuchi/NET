package com.shinnosuke_net.net;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shinnosuke_net.net.tool.*;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

public class OneChatActivity extends Activity {
	/* チャットデータリスト変数 */
	private List<CustomData> chatData;
	/* チャット画面用リストビュー変数 */
	private ListView chatTimeLine;
	/* チャットデータ用アダプター変数 */
	private CustomAdaptert customAdapter;
	/* socket変数 */
	private SocketIO socket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_chat);
		
		//テストデータ
		Date date = new Date();
		chatData = new ArrayList<CustomData>();
		for (int i=1 ; i<=10 ; i++) {
			CustomData customData = new CustomData();
			customData.setUserId("user00"+i);
			customData.setUserName("user"+i);
			customData.setMesseage("テストメッセージ"+i);
			customData.setPostDate(date);
			chatData.add(customData);
		}
		
		//テストデータをListViewにセット
		customAdapter = new CustomAdaptert(this, 0, chatData);
		chatTimeLine = (ListView) findViewById(R.id.oneChatTimeLine);
		chatTimeLine.setAdapter(customAdapter);
		
		//リストの最終行を表示
		chatTimeLine.setSelection(chatData.size());
		
		try {
			webSocketConnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void webSocketConnect() throws MalformedURLException {
		socket = new SocketIO("ws://echo.websocket.org");
		socket.connect(iocallback);
	}
	
	private IOCallback iocallback = new IOCallback() {
		
		@Override
		public void onMessage(JSONObject arg0, IOAcknowledge arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onMessage(String arg0, IOAcknowledge arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onError(SocketIOException arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onDisconnect() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onConnect() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void on(String arg0, IOAcknowledge ack, Object... args) {
			final String message = (String)args[0];
			
			if (message.length() == 0) {
				return;
			}
			createSetData(message);
		}
	};

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
	
	/**
	 * 送信ボタンを押された時の処理
	 * @param v
	 */
	public void postMesseage(View v) {
		EditText postMesseage = (EditText) findViewById(R.id.ocEditMessage);
		SpannableStringBuilder sp = (SpannableStringBuilder)postMesseage.getText();
		// 入力された文字がなければ何もしない
		if (sp.toString().length()==0) {
			//リストの最終行を表示
			chatTimeLine.setSelection(chatData.size());
			return;
		}
		
		socket.emit("message", sp.toString());
		
		//テキストボックスの初期化
		postMesseage.setText("");
		
		//リストの最終行を表示
		chatTimeLine.setSelection(chatData.size());
	}
	
	private void createSetData(String message) {
		Date nowDate = new Date();
		
		CustomData customData = new CustomData();
		customData.setUserId("user001");
		customData.setUserName("テストユーザーさん");
		customData.setMesseage(message);
		customData.setPostDate(nowDate);
		chatData.add(customData);
		customAdapter = new CustomAdaptert(this, 0, chatData);
		chatTimeLine.setAdapter(customAdapter);
	}
}
