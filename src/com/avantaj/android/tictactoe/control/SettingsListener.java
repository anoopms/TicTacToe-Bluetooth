package com.avantaj.android.tictactoe.control;

import com.avantaj.android.tictactoe.TTTSettingsActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingsListener implements OnClickListener {

	@Override
	public void onClick(View v) {
		Activity a = (Activity)v.getContext();
		Intent i = new Intent(v.getContext(), TTTSettingsActivity.class);
		a.startActivity(i);

	}
}
