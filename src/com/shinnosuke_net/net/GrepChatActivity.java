package com.shinnosuke_net.net;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shinnosuke_net.net.tool.CustomAdaptert;
import com.shinnosuke_net.net.tool.CustomData;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class GrepChatActivity extends Activity {
	private List<CustomData> chatData;
	private ListView chatTimeLine;
	private CustomAdaptert customAdapter;

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
		
		//テストデータをListViewにセット
		customAdapter = new CustomAdaptert(this, 0, chatData);
		chatTimeLine = (ListView) findViewById(R.id.grepChatTimeLine);
		chatTimeLine.setAdapter(customAdapter);
		
		//リストの最終行を表示
		chatTimeLine.setSelection(chatData.size());
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
	
	/**
	 * 送信ボタンを押された時の処理
	 * @param v
	 */
	public void postMesseage(View v) {
		EditText postMesseage = (EditText) findViewById(R.id.gcEditMessage);
		SpannableStringBuilder sp = (SpannableStringBuilder)postMesseage.getText();
		
		if (sp.toString().length()==0) {
			//リストの最終行を表示
			chatTimeLine.setSelection(chatData.size());
			return;
		}
		
		Date nowDate = new Date();
		
		CustomData customData = new CustomData();
		customData.setUserId("user001");
		customData.setUserName("テストユーザーさん");
		customData.setMesseage(sp.toString());
		customData.setPostDate(nowDate);
		chatData.add(customData);
		customAdapter = new CustomAdaptert(this, 0, chatData);
		chatTimeLine.setAdapter(customAdapter);
		
		//テキストボックスの初期化
		postMesseage.setText("");
		
		//リストの最終行を表示
		chatTimeLine.setSelection(chatData.size());
	}
}
