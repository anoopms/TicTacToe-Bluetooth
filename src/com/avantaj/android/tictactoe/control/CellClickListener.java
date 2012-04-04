package com.avantaj.android.tictactoe.control;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.avantaj.android.tictactoe.TTTEngine;
import com.avantaj.android.tictactoe.TTTImageMaps;
import com.avantaj.android.tictactoe.TTTScore;
import com.avantaj.android.tictactoe.TTTTheme;
import com.avantaj.android.tictactoe.TTTTheme.IconGroup;
import com.avantaj.android.ttt.R;


/**
 * @deprecated use {@link CellClickListener2} instead
 * @author ms
 *
 */
public class CellClickListener implements OnClickListener {
	
	

	@Override
	public void onClick(View v) {
		
		TTTImageMaps c  = TTTImageMaps.getInstance();
		TTTEngine engine = TTTEngine.getInstance();
		
		if(engine.getWinningComb() != null){
			Log.d("TTT","YOU HAVE ALREDY WON !!");
			return;
		}
		
		int id = v.getId();
		int imgId = c.getImageViewFromLayoutId(id);
		int index  = c.getIndexFromLayoutId(id);
		
		boolean ret = engine.setSymbol(index);
		if(ret == false){
			Log.d("TTT","ALREADY_SET");
			return;
		}
		
		Activity a = (Activity)v.getContext();
		ImageView i = (ImageView) a.findViewById(imgId);
		
		IconGroup iconGroup = TTTTheme.getInstance().getIconGroup();
		
		int symId = engine.getLastDrawnSymbol();
		int imageRef = 0;
		if(symId == TTTEngine.TICK){
			imageRef = iconGroup.getTickImage();
		}else{
			imageRef = iconGroup.getCrossImage();
		}
		
		i.setImageResource(imageRef);
		
		toggleNextSymbol(a);
		
		int[] result = engine.getWinningComb();
		if(result == null){
			Log.d("RESULT","NULL");
		}else{
			setWinnerLogo(a, result);
			Log.d("RESULT",result[0]+" "+result[1]+" "+result[2]);
		}
	}
	
	private void toggleNextSymbol(Activity act) {

		TTTEngine engine = TTTEngine.getInstance();

		SelectMarkListener.TickMarkListener tickMarkListener = new SelectMarkListener.TickMarkListener();
		SelectMarkListener.CrossMarkListener crossMarkListener = new SelectMarkListener.CrossMarkListener();

		View selectTick = act.findViewById(R.id.select_mark_tick_image);
		View selectCross = act.findViewById(R.id.select_mark_cross_image);

		if (engine.getSymbolToDrawId() == TTTEngine.TICK) {
			tickMarkListener.set(selectTick);
		} else {
			crossMarkListener.set(selectCross);
		}

	}
	
	private void setWinnerLogo(Context ctxt, int[] cells){
		IconGroup iconGroup = TTTTheme.getInstance().getIconGroup();
		TTTImageMaps imageMaps  = TTTImageMaps.getInstance();
		TTTEngine engine = TTTEngine.getInstance();
		
		int symId = engine.getLastDrawnSymbol();
		int imageRef = 0;
		int imageWinRef = 0;
		if(symId == TTTEngine.TICK){
			imageWinRef = iconGroup.getTickImageWinner();
			imageRef = iconGroup.getTickImage();
		}else{
			imageWinRef = iconGroup.getCrossImageWinner();
			imageRef = iconGroup.getCrossImage();
		}
		Activity act = (Activity)ctxt;
		for(int i = 0; i < cells.length; i++){
			ImageView imgView = (ImageView) act.findViewById(imageMaps.getImageViewFromIndex(cells[i]));
			imgView.setImageResource(imageWinRef);
		}
		
		TextView imgView = (TextView) act.findViewById(R.id.result_winner_icon);
		imgView.setCompoundDrawablesWithIntrinsicBounds(imageRef, 0, 0, 0);
		imgView.setText(R.string.HAS_WON);
		imageMaps.incrementScore(act, engine.getLastDrawnSymbol());
		imageMaps.updateScore(TTTScore.getInstance(), act);
		 
	}

}
