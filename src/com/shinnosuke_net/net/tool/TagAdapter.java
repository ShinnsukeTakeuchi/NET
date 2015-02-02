package com.shinnosuke_net.net.tool;

import java.util.List;

import com.shinnosuke_net.net.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class TagAdapter extends ArrayAdapter<TagData> {
	private LayoutInflater layoutInflater_;

	public TagAdapter(Context context, int textViewResourceId,
			List<TagData> objects) {
		super(context, textViewResourceId, objects);

		layoutInflater_ = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final TagData item = (TagData) getItem(position);

		// convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		if (null == convertView) {
			convertView = layoutInflater_.inflate(R.layout.tag_listview, null);
		}
		
		final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
		
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				item.setFlag(isChecked);
				Log.v("onCheckedChanged", String.valueOf(isChecked));
				Log.v("onCheckedChanged", String.valueOf(item.isFlag()));
			}
		});
		checkBox.setText(item.getValue());
		checkBox.setChecked(item.isFlag());
		
		return convertView;
	}
}
