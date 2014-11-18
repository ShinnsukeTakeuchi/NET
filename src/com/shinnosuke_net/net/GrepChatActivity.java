package com.shinnosuke_net.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.shinnosuke_net.net.tool.CustomAdaptert;
import com.shinnosuke_net.net.tool.CustomData;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class GrepChatActivity extends Activity {
	/* チャットデータリスト変数 */
	private List<CustomData> chatData;
	/* チャット画面用リストビュー変数 */
	private ListView chatTimeLine;
	/* チャットデータ用アダプター変数 */
	private CustomAdaptert customAdapter;
	
	private WebSocketClient socket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grep_chat);
		
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
				
				//描画
				onDrow();
				
				//リストの最終行を表示
				chatTimeLine.setSelection(chatData.size());

				System.out.println(Build.PRODUCT);
				java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
				java.lang.System.setProperty("java.net.preferIPv4Stack", "true");

				//ここからWebSocket
				try{
					URI uri = new URI("ws://kojikoji.mydns.jp:8080/WebSocketServer/Post");
					
					socket = new WebSocketClient(uri, new Draft_17()){
						@Override
						public void onClose(int arg0, String arg1, boolean arg2) {
							// TODO Auto-generated method stub
							System.out.println("onClose:Access");
						}

						@Override
						public void onError(Exception arg0) {
							// TODO Auto-generated method stub
							System.out.println("onError:Access");
							arg0.printStackTrace();
						}

						@Override
						public void onMessage(String arg0) {
							// TODO Auto-generated method stub
							System.out.println("onMessage:Access");
							System.out.println(arg0);
							
							Date date = new Date();
							
							CustomData customData = new CustomData();
							customData.setUserId("testuser");
							customData.setUserName("TestUser");
							customData.setMesseage(arg0);
							customData.setPostDate(date);
							chatData.add(customData);
							
							//描画
							onDrow();
							
							//リストの最終行を表示
							chatTimeLine.setSelection(chatData.size());
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
		getMenuInflater().inflate(R.menu.grep_chat, menu);
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
		EditText postMesseage = (EditText) findViewById(R.id.gcEditMessage);
		SpannableStringBuilder sp = (SpannableStringBuilder)postMesseage.getText();
		System.out.println(sp.toString());
		// 入力された文字がなければ何もしない
		if (sp.toString()!=null && sp.toString().length()==0) {
			System.out.println("文字入力されてない");
			//リストの最終行を表示
			chatTimeLine.setSelection(chatData.size());
			return;
		}
		System.out.println("文字入力されていた");
		
		socket.send(sp.toString());
		
		//テキストボックスの初期化
		postMesseage.setText("");
	}
	
	private void onDrow() {
		System.out.println("onDrow:Acess");
		//テストデータをListViewにセット
		customAdapter = new CustomAdaptert(this, 0, chatData);
		chatTimeLine = (ListView) findViewById(R.id.grepChatTimeLine);
		chatTimeLine.setAdapter(customAdapter);
		
		//リストの最終行を表示
//		chatTimeLine.setSelection(chatData.size());
	}
}
