package com.avantaj.android.tictactoe;

import java.util.Collection;
import java.util.HashMap;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avantaj.android.tictactoe.TTTTheme.IconGroup;
import com.avantaj.android.ttt.R;

public class TTTImageMaps {

	private static final TTTImageMaps instance = new TTTImageMaps();

	private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

	private HashMap<Integer, Integer> mapIndex = new HashMap<Integer, Integer>();

	private HashMap<Integer, Integer> mapIndexImage = new HashMap<Integer, Integer>();

	private int[] layoutIdsArray = new int[] { R.id.ll_0_0, R.id.ll_0_1,
			R.id.ll_0_2, R.id.ll_1_0, R.id.ll_1_1, R.id.ll_1_2, R.id.ll_2_0,
			R.id.ll_2_1, R.id.ll_2_2, };

	private int[] imageViewArray = new int[] { R.id.imageView_0_0,
			R.id.imageView_0_1, R.id.imageView_0_2, R.id.imageView_1_0,
			R.id.imageView_1_1, R.id.imageView_1_2, R.id.imageView_2_0,
			R.id.imageView_2_1, R.id.imageView_2_2 };

	public static TTTImageMaps getInstance() {
		return instance;
	}

	private TTTImageMaps() {

		for (int i = 0; i < 9; i++) {
			map.put(layoutIdsArray[i], imageViewArray[i]);
			mapIndex.put(layoutIdsArray[i], i);
			mapIndexImage.put(i, imageViewArray[i]);
		}
	}

	public Collection<Integer> getLayoutIds() {
		return map.keySet();
	}

	public int getImageViewFromLayoutId(int key) {
		return map.get((Integer) key);
	}

	public int getIndexFromLayoutId(int key) {
		return mapIndex.get((Integer) key);
	}

	public int getImageViewFromIndex(int index) {
		return mapIndexImage.get((Integer) index);
	}

	// TODO this should not be here. its not mvc. so no other choice
	public void restoreImages(Activity act, TTTEngineInterface engine, TTTScoreInterface score) {
		TTTTheme theme = TTTTheme.getInstance();
		IconGroup iconGroup = theme.getIconGroup();

		int[] tttData = engine.getTTTData();

		int[] winningCombo = updateWinnerLabel(act, engine);

		for (int i = 0; i < tttData.length; i++) {
			ImageView imgView = (ImageView) act.findViewById(imageViewArray[i]);
			if (tttData[i] == TTTEngineInterface.TICK) {
				if(isInWinningCombo(i, winningCombo)){
					imgView.setImageResource(iconGroup.getTickImageWinner());
				}else{
					imgView.setImageResource(iconGroup.getTickImage());
				}
			} else if (tttData[i] == TTTEngineInterface.CROSS) {
				
				if(isInWinningCombo(i, winningCombo)){
					imgView.setImageResource(iconGroup.getCrossImageWinner());
				}else{
					imgView.setImageResource(iconGroup.getCrossImage());
				}
			} else {
				imgView.setImageBitmap(null);
			}
		}
		updateSelectionIcons(act, engine.getSymbolToDrawId());
		updateScore(score, act);
	}
	
	private boolean isInWinningCombo(int index, int[] winningCombo){
		
		if(winningCombo == null){
			return false;
		}
		for(int i = 0; i < winningCombo.length; i++){
			if(index == winningCombo[i]){
				return true;
			}
		}
		return false;
		
	}


	
	private void updateSelectionIcons(Activity act, int symbol){
		
		int selLayout  = -1;
		int nonSelLayout  = -1;
		
		if(symbol == TTTEngineInterface.TICK){
			selLayout = R.id.select_mark_tick_layout;
			nonSelLayout = R.id.select_mark_cross_layout;
		}else{
			nonSelLayout = R.id.select_mark_tick_layout;
			selLayout = R.id.select_mark_cross_layout;
		}
		
		View lv = act.findViewById(nonSelLayout);
		lv.setBackgroundColor(0x00000000);
		lv = act.findViewById(selLayout);
		lv.setBackgroundColor(0xA287F717);
	}

	private int[] updateWinnerLabel(Activity act, TTTEngineInterface engine) {
		IconGroup iconGroup = TTTTheme.getInstance().getIconGroup();
		int[] tttData = engine.getTTTData();
		int[] winningCombo = engine.getWinningComb();

		if (winningCombo != null) {

			int imageRef = 0;
			Log.d("WINNING_COMBO", "SETTNG");
			if (tttData[winningCombo[0]] == TTTEngineInterface.TICK) {
				imageRef = iconGroup.getTickImage();
			} else {
				imageRef = iconGroup.getCrossImage();
			}

			TextView imgView = (TextView) act
					.findViewById(R.id.result_winner_icon);
			imgView.setCompoundDrawablesWithIntrinsicBounds(imageRef, 0, 0, 0);
			imgView.setText(R.string.HAS_WON);
			// incrementScore(act, engine.getLastDrawnSymbol());
		} else {
			TextView textView = (TextView) act
					.findViewById(R.id.result_winner_icon);
			textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			textView.setText(null);
		}
		return winningCombo;
	}
	

	public void incrementScore(Activity act, int winId) {
		TTTScore tScore = TTTScore.getInstance();
		if (winId == TTTEngineInterface.TICK) {
			tScore.incrementTickScore();
		} else {
			tScore.incrementCrossScore();
		}
	}

	public void updateScore(TTTScoreInterface tScore, Activity act) {
		TextView textView = null;
		textView = (TextView) act.findViewById(R.id.textView_tickScore);
		textView.setText(Integer.toString(tScore.getTickScore()));
		textView = (TextView) act.findViewById(R.id.textView_crossScore);
		textView.setText(Integer.toString(tScore.getCrossScore()));

	}

}
