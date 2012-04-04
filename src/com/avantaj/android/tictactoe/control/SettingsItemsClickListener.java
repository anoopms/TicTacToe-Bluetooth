package com.avantaj.android.tictactoe.control;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.avantaj.android.tictactoe.model.SettingsListAdapter;

public class SettingsItemsClickListener implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> adView, View view, int position, long id) {
		// TODO Auto-generated method stub
		Log.d("ONCLICK", "LIST CLICKED");
		((SettingsListAdapter)adView.getAdapter()).setSelectedPosition(position);

	}

}
