package com.avantaj.android.tictactoe.control;

import com.avantaj.android.tictactoe.TTTEngine;
import com.avantaj.android.tictactoe.TTTEngineInterface;
import com.avantaj.android.tictactoe.TTTEngineInterface.ClickSource;
import com.avantaj.android.tictactoe.twop.TwoPClientModule;
import com.avantaj.android.tictactoe.twop.TwoPManager;
import com.avantaj.android.tictactoe.twop.TwoPServerModule;
import com.avantaj.android.tictactoe.twop.TwoPManager.GameType;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 	
 * @author ms
 *
 */
public class SelectMarkListener2 {
	
	public static class TickMarkListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(TwoPManager.getGameType()==GameType.SINGLE_PLAYER){
				Log.d("CellClickListener2", "SINGLE_PLAYER");
				TTTEngine engine  = TTTEngine.getInstance();
				int symbol = engine.getSymbolToDrawId();
				if(symbol == TTTEngineInterface.TICK){
					Log.d("CellClickListener2", "SINGLE_PLAYER TICK");
					TwoPServerModule.getInstance().onClick(TTTEngineInterface.CELL_CHOOSE_ID_TICK, ClickSource.SERVER);
				}else if(symbol == TTTEngineInterface.CROSS){
					Log.d("CellClickListener2", "SINGLE_PLAYER CROSS");
					TwoPClientModule.getInstance().onClick(TTTEngineInterface.CELL_CHOOSE_ID_TICK);
				}else{
					throw new IllegalArgumentException("Invalid symbol"+symbol);
				}
			}else if(TwoPManager.getGameType()==GameType.MULTIPLAYER_SERVER){
				Log.d("CellClickListener2", "MULTIPLAYER_SERVER");
				TwoPServerModule.getInstance().onClick(TTTEngineInterface.CELL_CHOOSE_ID_TICK, ClickSource.SERVER);
			}else if(TwoPManager.getGameType()==GameType.MULTIPLAYER_CLIENT){
				Log.d("CellClickListener2", "MULTIPLAYER_CLIENT");
				TwoPClientModule.getInstance().onClick(TTTEngineInterface.CELL_CHOOSE_ID_TICK);
			}
			
		}
		
	}
	
	public static class CrossMarkListener implements OnClickListener{

		@Override
		public void onClick(View v) {

			if(TwoPManager.getGameType()==GameType.SINGLE_PLAYER){
				Log.d("CellClickListener2", "SINGLE_PLAYER");
				TTTEngine engine  = TTTEngine.getInstance();
				int symbol = engine.getSymbolToDrawId();
				if(symbol == TTTEngineInterface.TICK){
					Log.d("CellClickListener2", "SINGLE_PLAYER TICK");
					TwoPServerModule.getInstance().onClick(TTTEngineInterface.CELL_CHOOSE_ID_CROSS, ClickSource.SERVER);
				}else if(symbol == TTTEngineInterface.CROSS){
					Log.d("CellClickListener2", "SINGLE_PLAYER CROSS");
					TwoPClientModule.getInstance().onClick(TTTEngineInterface.CELL_CHOOSE_ID_CROSS);
				}else{
					throw new IllegalArgumentException("Invalid symbol"+symbol);
				}
			}else if(TwoPManager.getGameType()==GameType.MULTIPLAYER_SERVER){
				Log.d("CellClickListener2", "MULTIPLAYER_SERVER");
				TwoPServerModule.getInstance().onClick(TTTEngineInterface.CELL_CHOOSE_ID_CROSS, ClickSource.SERVER);
			}else if(TwoPManager.getGameType()==GameType.MULTIPLAYER_CLIENT){
				Log.d("CellClickListener2", "MULTIPLAYER_CLIENT");
				TwoPClientModule.getInstance().onClick(TTTEngineInterface.CELL_CHOOSE_ID_CROSS);
			}
			
		
		}
}

}
