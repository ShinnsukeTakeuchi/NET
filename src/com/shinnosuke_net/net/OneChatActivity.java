package com.shinnosuke_net.net;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shinnosuke_net.net.tool.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class OneChatActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_chat);
		
		//テストデータ
		Date date = new Date();
		List<CustomData> chatData = new ArrayList<CustomData>();
		for (int i=1 ; i<=30 ; i++) {
			CustomData customData = new CustomData();
			customData.setUserId("user00"+i);
			customData.setUserName("user"+i);
			customData.setMesseage("テストメッセージ"+i);
			customData.setPostDate(date);
			chatData.add(customData);
		}
		
		//テストデータをListViewにセット
		CustomAdaptert customAdapter = new CustomAdaptert(this, 0, chatData);
		ListView chatTimeLine = (ListView) findViewById(R.id.oneChatTimeLine);
		chatTimeLine.setAdapter(customAdapter);
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
}
