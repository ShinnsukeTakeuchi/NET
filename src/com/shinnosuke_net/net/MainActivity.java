package com.shinnosuke_net.net;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener {
	//ページ遷移用の変数
	private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //OnClickListenerのセット
        View oneChat = findViewById(R.id.oneChat);
        View grepChat = findViewById(R.id.grepChat);
        View config = findViewById(R.id.config);
        oneChat.setOnClickListener(this);
        grepChat.setOnClickListener(this);
        config.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
		//押されたボタンの判定
		if (v.getId() == R.id.oneChat) {// 二人でチャット
			intent = new Intent(this, OneChatActivity.class);
		} else if (v.getId() == R.id.grepChat) {// みんなでチャット
			intent = new Intent(this, GrepChatActivity.class);
		} else if (v.getId() == R.id.config) {// 設定
			intent = new Intent(this, ConfigActivity.class);
		} else {
			this.moveTaskToBack(true);
		}
		startActivity(intent);
	}
}