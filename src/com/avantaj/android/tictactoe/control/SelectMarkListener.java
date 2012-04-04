package com.avantaj.android.tictactoe.control;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.avantaj.android.tictactoe.TTTEngine;
import com.avantaj.android.ttt.R;

/**
 * @deprecated 	
 * @author ms
 *
 */
public class SelectMarkListener {
	
	public static class TickMarkListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Log.d("MARK_CLICK", "On tick");
			TTTEngine engine = TTTEngine.getInstance();
			if(engine.isGameStarted()){
				Log.d("MARK_CLICK", "tick: Game started");
				return;
			}
			engine.setSymbolToDraw(TTTEngine.TICK);
			set(v);
		}
		
		public void set(View v){
			
			Activity act = (Activity)v.getContext();
			View lv = act.findViewById(R.id.select_mark_cross_layout);
			lv.setBackgroundColor(0x00000000);
			lv = act.findViewById(R.id.select_mark_tick_layout);
			lv.setBackgroundColor(0xA287F717);
			
		}
	}
	
	public static class CrossMarkListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Log.d("MARK_CLICK", "On cross");
			TTTEngine engine = TTTEngine.getInstance();
			if(engine.isGameStarted()){
				Log.d("MARK_CLICK", "cross: Game started");
				return;
			}
			engine.setSymbolToDraw(TTTEngine.CROSS);
			set(v);
		}
		
		public void set(View v){
			
			Activity act = (Activity)v.getContext();
			View lv = act.findViewById(R.id.select_mark_tick_layout);
			lv.setBackgroundColor(0x00000000);
			lv = act.findViewById(R.id.select_mark_cross_layout);
			lv.setBackgroundColor(0xA287F717);
			
		}
		
	}

}
