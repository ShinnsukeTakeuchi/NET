package com.shinnosuke_net.net.tool;

import java.util.List;

import android.os.AsyncTask;
import android.widget.ListView;

public class UpdateListView extends AsyncTask<String, String, String> {
	/* チャットデータリスト変数 */
	private List<CustomData> chatData;
	/* チャット画面用リストビュー変数 */
	private ListView chatTimeLine;
	/* チャットデータ用アダプター変数 */
	private CustomAdaptert customAdapter;

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update() {
		chatTimeLine.setSelection(chatData.size());
	}
	
	public void setAdapter(CustomAdaptert customAdapter) {
		chatTimeLine.setAdapter(customAdapter);
	}
}
