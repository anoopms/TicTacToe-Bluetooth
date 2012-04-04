package com.avantaj.android.tictactoe.twop;

import android.app.Activity;
import android.content.Context;

import com.avantaj.android.tictactoe.TTTImageMaps;
import com.avantaj.android.tictactoe.model.TTTUtils;
import com.avantaj.android.tictactoe.model.TTTUtils.TTTClientDummyEngine;
import com.avantaj.android.tictactoe.network.NWDataListener;
import com.avantaj.android.tictactoe.network.NWInterface;

public class TwoPClientModule implements NWDataListener{

	//Add here
	private NWInterface inter;
	private Context ctxt = null;
	
	private static TwoPClientModule instance = new TwoPClientModule();
	
	public static TwoPClientModule getInstance() {
		return instance;
	}
	
	private TwoPClientModule(){
	}
	
	//Should be done on start activity
		public void setContext(Context ctxt) {
			this.ctxt = ctxt;
		}
	
	//Should be done on start activity
	public void setClientNetworkInterface(NWInterface inter){
		this.inter = inter;
		inter.registerDataListener(this);
	}
	
	public void onClick(int cellId){
		inter.send(TTTUtils.getClientToServerCellClickData(cellId));
	}
	
	private boolean updateCell(byte[] indata){
		TTTClientDummyEngine engine = TTTUtils.getClientDummyEngine(indata);
		
		TTTImageMaps imageMaps = TTTImageMaps.getInstance();
		imageMaps.restoreImages((Activity)ctxt, engine, engine);
		return true;
		
	}
	
	public void receive(byte[] indata) {
		updateCell(indata);
	}
}
