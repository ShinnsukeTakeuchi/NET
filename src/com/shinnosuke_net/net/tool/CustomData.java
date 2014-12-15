package com.shinnosuke_net.net.tool;

import java.util.Date;

/**
 * チャット1行のカスタムデータ
 * @author shinnosuke takeuchi
 *
 */
public class CustomData {
	//1行のデータ変数
	private String userId;
	private String userName;
	private String messeage;
	private String postDate;
	
	//getterとsetter
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMesseage() {
		return messeage;
	}
	public void setMesseage(String messeage) {
		this.messeage = messeage;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
}
