package com.shinnosuke_net.net.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.shinnosuke_net.net.OneChatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class HttpGetTask extends AsyncTask<String, String, String> {
	private String roomId;
	private Context context;
	
	public void HttpGetTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {

		String response = "";
		try {
			String searchUrl = "http://kojikoji.mydns.jp:8080/WebSocketServer/SearchRoom?userName="
					+ params[0];
			HttpGet httpGet = new HttpGet(searchUrl);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(httpGet);
			// ステータスコードを取得
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			System.out.println("statusCode:" + statusCode);

			// レスポンスを取得
			HttpEntity entity = httpResponse.getEntity();
			InputStream stream = entity.getContent();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					stream));
			String s = "";
			while ((s = buffer.readLine()) != null) {
				response += s;
			}
			System.out.println("response:" + response);

			setRoomId(response);

			// リソース解放
			entity.consumeContent();
			// クライアントの終了
			client.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	protected void onPostExecute(String result) {

	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
