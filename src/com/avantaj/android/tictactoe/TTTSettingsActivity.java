package com.avantaj.android.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.avantaj.android.tictactoe.control.SettingsItemsClickListener;
import com.avantaj.android.tictactoe.model.SettingsListAdapter;
import com.avantaj.android.ttt.R;

public class TTTSettingsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		String[] bgValues = new String[]{"Wood", "Steel"};
		String[] iconValues = new String[]{"Normal", "Hearts"};
		
		
		ListView l1  = (ListView)findViewById(R.id.settings_bg_list);
		ListView l2  = (ListView)findViewById(R.id.settings_icon_list);
		
		l1.setOnItemClickListener(new SettingsItemsClickListener());
		l2.setOnItemClickListener(new SettingsItemsClickListener());
		
		
		final SettingsListAdapter adapter1 = new SettingsListAdapter(this, bgValues);
		final SettingsListAdapter adapter2 = new SettingsListAdapter(this, iconValues);
		
		TTTTheme theme = TTTTheme.getInstance();
		adapter1.setSelectedPosition(theme.getSelectedIndex());
		adapter2.setSelectedPosition(theme.getIconSelectedIndex());
		
		l1.setAdapter(adapter1);
		l2.setAdapter(adapter2);
		
		
		View saveButton = (View)findViewById(R.id.settings_button_save);
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TTTTheme theme  = TTTTheme.getInstance();
				theme.setThemeIndexes(adapter1.getSelectedPosition(), adapter2.getSelectedPosition());
				Log.d("SETTINGS", "ON SAVE");
				TTTSettingsActivity.this.finish();
			}
		});
		
		
		View cancelButton = (View)findViewById(R.id.settings_button_cancel);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("SETTINGS", "ON CANCEL");
				TTTSettingsActivity.this.finish();
			}
		});
	}
	
	

}
