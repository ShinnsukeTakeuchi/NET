package com.shinnosuke_net.net.tool;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonEscape {
	public JSONObject getJson(String str) {
		JSONObject json = null;
		try {
			json = new JSONObject(str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getString(JSONObject json) {
		String str = "";
		str = json.toString();
		return str;
	}
}
