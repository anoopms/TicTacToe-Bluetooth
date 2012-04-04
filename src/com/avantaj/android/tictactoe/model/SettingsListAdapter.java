package com.avantaj.android.tictactoe.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avantaj.android.ttt.R;

public class SettingsListAdapter extends ArrayAdapter<String> {

	
	private final Context context;
	private final String[] values;
	private int selectedPosition = -1;
	
	public SettingsListAdapter(Context context,
			String[] values) {
		super(context, R.layout.settings_row, values);
		this.context = context;
		this.values = values;
	}
	
	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
		notifyDataSetChanged();
	}
	
	public int getSelectedPosition() {
		return selectedPosition;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView  = null;
		LinearLayout layout = (LinearLayout)convertView;
		
		if(layout == null){
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout = (LinearLayout)inflater.inflate(R.layout.settings_row, parent, false);
			
		}
		textView = (TextView) layout.findViewById(R.id.settings_item_text);
		textView.setText(values[position]);
		layout.setBackgroundColor(0xFF686868);
		ImageView imgView = (ImageView)layout.findViewById(R.id.settings_item_image_3);
		if (getSelectedPosition() == position) {
			imgView.setImageResource(R.drawable.tick_normal_1_60_60);
		} else {
			imgView.setImageDrawable(null);
		}
		
		return layout;
	}
	
	
	// This should be more clean

}
