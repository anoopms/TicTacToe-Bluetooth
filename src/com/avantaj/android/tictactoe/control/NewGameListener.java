package com.avantaj.android.tictactoe.control;

import com.avantaj.android.tictactoe.TTTEngine;
import com.avantaj.android.tictactoe.TTTImageMaps;
import com.avantaj.android.tictactoe.TTTScore;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class NewGameListener implements OnClickListener {

	@Override
	public void onClick(View view) {
		Log.d("BUTTON_CLICK", "In new game listener");
		Activity act = (Activity)view.getContext();
		TTTEngine.getInstance().reset_tttData();
		TTTImageMaps.getInstance().restoreImages(act, TTTEngine.getInstance(), TTTScore.getInstance());

	}

}
