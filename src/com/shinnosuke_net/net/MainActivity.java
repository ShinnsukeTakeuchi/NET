package com.shinnosuke_net.net;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


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
        View appEnd = findViewById(R.id.appEnd);
        oneChat.setOnClickListener(this);
        grepChat.setOnClickListener(this);
        config.setOnClickListener(this);
        appEnd.setOnClickListener(this);
        
        InputStream input;
		try {
			input = new FileInputStream(Environment.getExternalStorageDirectory() + "/" +  "user_profile.json");
			int size = input.available();
			byte[] buffer = new byte[size];
			input.read(buffer);
			input.close();

			String json = new String(buffer);

			JSONObject userProfileJson = new JSONObject(json);

			Toast.makeText(this, "ようこそ"+userProfileJson.getString("userName")+"さん", Toast.LENGTH_SHORT).show();

		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (JSONException e) {
		    e.printStackTrace();
		}
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
			this.finish();
		}
		startActivity(intent);
	}
}