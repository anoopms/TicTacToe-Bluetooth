package com.avantaj.android.tictactoe.twop;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.avantaj.android.tictactoe.TTTEngine;
import com.avantaj.android.tictactoe.TTTEngineInterface;
import com.avantaj.android.tictactoe.TTTEngineInterface.ClickSource;
import com.avantaj.android.tictactoe.TTTImageMaps;
import com.avantaj.android.tictactoe.TTTScore;
import com.avantaj.android.tictactoe.model.TTTUtils;
import com.avantaj.android.tictactoe.network.NWDataListener;
import com.avantaj.android.tictactoe.network.NWInterface;

public class TwoPServerModule implements NWDataListener{

	//Add here
	private NWInterface inter;
	private Context ctxt = null;
	
	private static TwoPServerModule instance = new TwoPServerModule();
	
	private TwoPServerModule(){
	}
	
	public static TwoPServerModule getInstance() {
		return instance;
	}
	
	
	//Should be done on start activity
	public void setContext(Context ctxt) {
		this.ctxt = ctxt;
	}
	
	//Should be done on start activity
	public void setServerNetworkInterface(NWInterface inter){
		this.inter = inter;
		inter.registerDataListener(this);
	}
	
	public void onClick(int cellId, ClickSource source){
		boolean ret = updateEngine(cellId, source);
		if(ret == false){
			return;
		}
		TTTImageMaps.getInstance().restoreImages((Activity)ctxt, TTTEngine.getInstance(), TTTScore.getInstance());
		inter.send(TTTUtils.getServerToClientFulldata(TTTEngine.getInstance(), TTTScore.getInstance()));
	}
	
	private boolean updateEngine(int cellid, ClickSource source){
		TTTEngine engine = TTTEngine.getInstance();
		boolean ret;   
		if(cellid < TTTEngineInterface.TOTAL_ITEMS_IN_TTT){
			
			ret = engine.setSymbol(cellid, source);
			
		}else{
			if(cellid == TTTEngineInterface.CELL_CHOOSE_ID_TICK){
				ret = engine.setSymbolToDraw(TTTEngineInterface.TICK);
			}else{
				ret = engine.setSymbolToDraw(TTTEngineInterface.CROSS);
			}
		}
		if (ret == false){
			return false;
		}
		return true;
		
	}
	
	// Client data will have 2 items, cellid to draw, symbol to draw
	public void receive(byte[] data) {
		Log.d("TwoPServerModule", "RECEIVED FROM CLIENT");
		
		onClick(data[1], ClickSource.CLIENT);
	}
}
