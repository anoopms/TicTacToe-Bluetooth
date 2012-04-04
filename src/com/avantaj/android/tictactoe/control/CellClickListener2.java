package com.avantaj.android.tictactoe.control;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.avantaj.android.tictactoe.TTTEngine;
import com.avantaj.android.tictactoe.TTTEngineInterface;
import com.avantaj.android.tictactoe.TTTEngineInterface.ClickSource;
import com.avantaj.android.tictactoe.TTTImageMaps;
import com.avantaj.android.tictactoe.twop.TwoPClientModule;
import com.avantaj.android.tictactoe.twop.TwoPManager;
import com.avantaj.android.tictactoe.twop.TwoPManager.GameType;
import com.avantaj.android.tictactoe.twop.TwoPServerModule;

public class CellClickListener2 implements OnClickListener {

	@Override
	public void onClick(View v) {
		
		Log.d("ONCLICK", "Onclick listener");
		TTTImageMaps c  = TTTImageMaps.getInstance();
		int id = v.getId();
		int index  = c.getIndexFromLayoutId(id);
		
		if(TwoPManager.getGameType()==GameType.SINGLE_PLAYER){
			Log.d("CellClickListener2", "SINGLE_PLAYER");
			TTTEngine engine  = TTTEngine.getInstance();
			int symbol = engine.getSymbolToDrawId();
			if(symbol == TTTEngineInterface.TICK){
				Log.d("CellClickListener2", "SINGLE_PLAYER TICK");
				TwoPServerModule.getInstance().onClick(index, ClickSource.SERVER);
			}else if(symbol == TTTEngineInterface.CROSS){
				Log.d("CellClickListener2", "SINGLE_PLAYER CROSS");
				TwoPClientModule.getInstance().onClick(index);
			}else{
				throw new IllegalArgumentException("Invalid symbol"+symbol);
			}
		}else if(TwoPManager.getGameType()==GameType.MULTIPLAYER_SERVER){
			Log.d("CellClickListener2", "MULTIPLAYER_SERVER");
			TwoPServerModule.getInstance().onClick(index, ClickSource.SERVER);
		}else if(TwoPManager.getGameType()==GameType.MULTIPLAYER_CLIENT){
			Log.d("CellClickListener2", "MULTIPLAYER_CLIENT");
			TwoPClientModule.getInstance().onClick(index);
		}
		
	}
	
}
