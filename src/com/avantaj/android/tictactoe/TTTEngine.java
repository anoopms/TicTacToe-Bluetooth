package com.avantaj.android.tictactoe;

import android.util.Log;

public class TTTEngine implements TTTEngineInterface{
	
	
	private static TTTEngine instance = new TTTEngine();
	
	private int serverSymbol = EMPTY;
	
	private int clientSymbol = EMPTY;
	
	private int symbolToDrawId = TICK;
	private int[] ttt_data = new int[9];
	
	
	private final int[][] winning_comb = new int[][]{
			{0,1,2},
			{3,4,5},
			{6,7,8},
			{0,3,6},
			{1,4,7},
			{2,5,8},
			{0,4,8},
			{2,4,6},
	};
	
	
	private TTTEngine(){
		Log.d("ENGINE", "Engine Created");
	};
	
	public static TTTEngine getInstance() {
		return instance;
	}
	
	@Override
	public int[] getTTTData() {
		return ttt_data;
	}
	
	@Override
	public boolean isGameStarted(){
		for (int i: ttt_data){
			if(i != 0){
				Log.d("GAME_STARTED", ""+i);
				return true;
			}
		}
		Log.d("GAME_NOT_STARTED", "");
		return false;
	}
	
	
	public void reset_tttData(){
		for(int i = 0; i < TOTAL_ITEMS_IN_TTT ; i++){
			ttt_data[i] = EMPTY;
		}
		clientSymbol = EMPTY;
		serverSymbol = EMPTY;
				
//		symbolToDrawId = TICK;
	}
	
	public boolean setSymbolToDraw(int symbol_to_draw_id) {
		if(isGameStarted()){
			return false;
		}
		this.symbolToDrawId = symbol_to_draw_id;
		return true;
	}
	
	@Override
	public int getSymbolToDrawId() {
		return symbolToDrawId;
	}
	
	private void toggleSymbolToDraw(){
		if(this.symbolToDrawId == CROSS){
			this.symbolToDrawId  = TICK;
		}else{
			this.symbolToDrawId  = CROSS;
		}
	}
	
	@Override
	public int getLastDrawnSymbol() {
		if(this.symbolToDrawId == CROSS){
			return TICK;
		}else{
			return CROSS;
		}
	}
	/** @deprecated */
	public boolean setSymbol(int index){
		if(ttt_data[index] != EMPTY){
			return false;
		}
		toggleSymbolToDraw();
		ttt_data[index] = getLastDrawnSymbol();
		return true;
	}
	
	
	
	public boolean setSymbol(int index, ClickSource source){
		
		if(getWinningComb() != null){
			Log.d("TTTEngine", "Already won");
			return false;
		}
		if(ttt_data[index] != EMPTY){
			return false;
		}
		if(!isValid(source)){
			return false;
		}
		toggleSymbolToDraw();
		ttt_data[index] = getLastDrawnSymbol();
		if(isWon()){
			TTTScore.getInstance().incrementScore(ttt_data[index]);
		}
		
		return true;
	}
	
	
	private boolean isValid(ClickSource source){
		
		
		int symbol = getSymbolToDrawId();
		switch(source){
			case CLIENT:
				if(clientSymbol == EMPTY){
					clientSymbol = symbol;
					return true;
				}else{
					return clientSymbol == symbol;
				}
			case SERVER:
				if(serverSymbol == EMPTY){
					serverSymbol = symbol;
					return true;
				}else{
					return serverSymbol == symbol;
				}

		}
		return false;
		
	}
	
	
	public boolean isWon(){
		if(getWinningComb()!=null){
			return true;
		}
		return false;
	}
	
	@Override
	public int[] getWinningComb(){
		for(int i = 0; i< winning_comb.length; i++){
			int[] a = winning_comb[i];
			if(ttt_data[a[0]] == ttt_data[a[1]] && ttt_data[a[0]] == ttt_data[a[2]] && ttt_data[a[0]] != EMPTY){
				return a;
			}
		}
		return null;
	}
}
