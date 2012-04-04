package com.avantaj.android.tictactoe.control;

import com.avantaj.android.tictactoe.TTTImageMaps;
import com.avantaj.android.tictactoe.TTTScore;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ResetScoreListener implements OnClickListener {

	@Override
	public void onClick(View view) {
		TTTScore.getInstance().reset();
		TTTImageMaps.getInstance().updateScore(TTTScore.getInstance(), (Activity)view.getContext());
	}

}
