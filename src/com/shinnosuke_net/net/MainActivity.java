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
		System.out.println("MainActivity:onCreate");
        
        //OnClickListenerのセット
        View oneChat = findViewById(R.id.oneChat);
        View config = findViewById(R.id.config);
        oneChat.setOnClickListener(this);
        config.setOnClickListener(this);
        
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
	protected void onStart() {
		super.onStart();
		System.out.println("MainActivity:onStart");
	}


	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("MainActivity:onRestart");
	}


	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("MainActivity:onResume");
	}


	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("MainActivity:onPause");
	}


	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("MainActivity:onStop");
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("MainActivity:onDestroy");
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
		} else if (v.getId() == R.id.config) {// 設定
			intent = new Intent(this, ConfigActivity.class);
		}
		startActivity(intent);
	}
	
	public void appEnd(View v) {
		this.finish();
	}
}