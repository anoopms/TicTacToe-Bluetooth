package com.avantaj.android.ttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectPlayActivity extends Activity {

//	private static final String TAG = "BluetoothChat";
//	private static final boolean D = true;



	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	
	public static final int SELECT_PLAY_SINGLE = 0;
	public static final int SELECT_PLAY_MULTI = 1;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectplay);
		String[] values = new String[] { "Single player",
				"Mutliplayer" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);

		ListView list = (ListView) findViewById(R.id.select_playtype_list);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(SelectPlayActivity.this, TicTacToeActivity.class);
				i.putExtra("index", position);
				startActivity(i);
			}
		});

	}
}
