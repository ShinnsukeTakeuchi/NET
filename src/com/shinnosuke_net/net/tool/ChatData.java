package com.shinnosuke_net.net.tool;

import java.util.ArrayList;
import java.util.List;

public class ChatData {
	private List<CustomData> chatData = new ArrayList<CustomData>();
	
	/**
	 * コンストラクタ
	 */
	public void ChatData() {}

	public List<CustomData> getChatData() {
		return chatData;
	}

	public void setChatData(List<CustomData> chatData) {
		this.chatData = chatData;
	}
	
	public void add(CustomData cd) {
		chatData.add(cd);
	}
	
	public int size() {
		return chatData.size();
	}
}
