package com.shinnosuke_net.net;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shinnosuke_net.net.tool.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

public class OneChatActivity extends Activity implements OnClickListener {
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
		
		// ボタン押下時の処理準備
		View ocPostBtn = findViewById(R.id.ocPostBtn);
		ocPostBtn.setOnClickListener(this);
		
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
		//テスト用echoサーバー
		socket = new SocketIO("http://echo.websocket.org");
		socket.connect(iocallback);

	}
	
	private IOCallback iocallback = new IOCallback() {
		
		@Override
		public void onMessage(JSONObject arg0, IOAcknowledge arg1) {
			// TODO Auto-generated method stub
			System.out.println("onMessageJSON:Access");
		}
		
		@Override
		public void onMessage(String arg0, IOAcknowledge arg1) {
			// TODO Auto-generated method stub
			System.out.println("onMessageString:Access");
		}
		
		@Override
		public void onError(SocketIOException arg0) {
			// TODO Auto-generated method stub
			System.out.println("onError:Access");
		}
		
		@Override
		public void onDisconnect() {
			// TODO Auto-generated method stub
			System.out.println("onDisconnect:Access");
		}
		
		@Override
		public void onConnect() {
			// TODO Auto-generated method stub
			System.out.println("onConnect:Access");
		}
		
		@Override
		public void on(String arg0, IOAcknowledge ack, Object... args) {
			final String message = (String)args[0];
			System.out.println("on:"+message);
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

	@Override
	public void onClick(View v) {
		EditText postMesseage = (EditText) findViewById(R.id.ocEditMessage);
		SpannableStringBuilder sp = (SpannableStringBuilder)postMesseage.getText();
		System.out.println(sp.toString());
		// 入力された文字がなければ何もしない
		if (sp.toString().length()==0) {
			System.out.println("文字入力されてない");
			//リストの最終行を表示
			chatTimeLine.setSelection(chatData.size());
			return;
		}
		System.out.println("文字入力されていた");
		
		socket.emit("message", sp.toString());
		
		//テキストボックスの初期化
		postMesseage.setText("");
		//リストの最終行を表示
		chatTimeLine.setSelection(chatData.size());
	}
}
