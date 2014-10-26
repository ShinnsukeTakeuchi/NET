package com.shinnosuke_net.net.tool;

import java.text.SimpleDateFormat;
import java.util.List;

import com.shinnosuke_net.net.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * チャットのリストデータ
 * @author shinnosuke takeuchi
 *
 */
public class CustomAdaptert extends ArrayAdapter<CustomData> {
	private LayoutInflater layoutInflater_;

	public CustomAdaptert(Context context, int resource,
			int textViewResourceId, List<CustomData> objects) {
		super(context, resource, textViewResourceId, objects);
		layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CustomData item = (CustomData)getItem(position);
		 
		 // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		 if (null == convertView) {
			 convertView = layoutInflater_.inflate(R.layout.custom_listview, null);
		 }
		 
		 // CustomDataのデータをViewの各Widgetにセットする
		 TextView userId;
		 userId = (TextView)convertView.findViewById(R.id.userID);
		 userId.setText(item.getUserId());
		 
		 TextView userName;
		 userName = (TextView)convertView.findViewById(R.id.userName);
		 userName.setText(item.getUserName());
		 
		 TextView messeage;
		 messeage = (TextView)convertView.findViewById(R.id.messeage);
		 messeage.setText(item.getMesseage());
		 
		 TextView postDate;
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		 postDate = (TextView)convertView.findViewById(R.id.postDate);
		 postDate.setText(sdf.format(item.getPostDate()));
		 
		 return convertView;
	}
}
