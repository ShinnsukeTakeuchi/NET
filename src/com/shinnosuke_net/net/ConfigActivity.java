package com.shinnosuke_net.net;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		
		EditText userId = (EditText)findViewById(R.id.user_id);
		EditText userName = (EditText)findViewById(R.id.user_name);
		EditText sex = (EditText)findViewById(R.id.sex);
		EditText age = (EditText)findViewById(R.id.age);
		EditText address = (EditText)findViewById(R.id.address);
		
		InputStream input;
		try {
			input = new FileInputStream(Environment.getExternalStorageDirectory() + "/" +  "user_profile.json");
			int size = input.available();
			byte[] buffer = new byte[size];
			input.read(buffer);
			input.close();

			String json = new String(buffer);

			JSONObject userProfileJson = new JSONObject(json);
			
			userId.setText(userProfileJson.getString("userId"));
			userName.setText(userProfileJson.getString("userName"));
			sex.setText(userProfileJson.getString("sex"));
			age.setText(userProfileJson.getString("age"));
			address.setText(userProfileJson.getString("address"));

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
		getMenuInflater().inflate(R.menu.config, menu);
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
	
	public void chengeUserProfile(View v) {
		EditText userId = (EditText)findViewById(R.id.user_id);
		EditText userName = (EditText)findViewById(R.id.user_name);
		EditText sex = (EditText)findViewById(R.id.sex);
		EditText age = (EditText)findViewById(R.id.age);
		EditText address = (EditText)findViewById(R.id.address);
			
//		Toast.makeText(this, userId.getText().toString(), Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, userName.getText().toString(), Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, sex.getText().toString(), Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, age.getText().toString(), Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, address.getText().toString(), Toast.LENGTH_SHORT).show();

		JSONObject userProfileJson = new JSONObject();

		try {
			userProfileJson.put("userId", userId.getText().toString());
			userProfileJson.put("userName", userName.getText().toString());
			userProfileJson.put("sex", sex.getText().toString());
			userProfileJson.put("age", age.getText().toString());
			userProfileJson.put("address", address.getText().toString());

			File file = new File(Environment.getExternalStorageDirectory() + "/" +  "user_profile.json");
			Log.v("file", Environment.getExternalStorageDirectory() + "/" +  "user_profile.json");
		    FileWriter filewriter = new FileWriter(file);

		    BufferedWriter bw = new BufferedWriter(filewriter);
			PrintWriter pw = new PrintWriter(bw);
			pw.write(userProfileJson.toString());
			pw.close();
			Toast.makeText(this, "変更されました", Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(this, "変更に失敗しました", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "変更に失敗しました", Toast.LENGTH_SHORT).show();
		}
	}
}
