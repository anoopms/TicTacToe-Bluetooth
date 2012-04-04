package com.avantaj.android.tictactoe.model;

import android.util.Log;

import com.avantaj.android.tictactoe.TTTEngineInterface;
import com.avantaj.android.tictactoe.TTTScoreInterface;


public class TTTUtils {
	
	public enum DATA_TYPE{
		SERVER_TO_CLIENT_FULL_DATA, 
		CLIENT_TO_SERVER_CELLCLICK,
		CLIENT_TO_SERVER_SYMBOL_CHANGE;
	}
	
	/*9 ttt_data, 1 Next item to draw, 3 Winning combo, 2 score, rest padding, total 20 */
	public static byte[] getServerToClientFulldata(TTTEngineInterface engine, TTTScoreInterface score){
		
		byte[] ret = new byte[20];
		int[] tttdata  = engine.getTTTData();
		int i=0;
		ret[i] = (byte)DATA_TYPE.SERVER_TO_CLIENT_FULL_DATA.ordinal();
		i++;
		for(int j = 0;j<tttdata.length; j++, i++){
			ret[i] = (byte)tttdata[j];
		}
		ret[i] = (byte)engine.getSymbolToDrawId();
		i++;
		int[] winningComb = engine.getWinningComb();
		if(winningComb == null){
			winningComb = new int[]{-1,-1,-1};
		}
		for(int j = 0;j<winningComb.length; j++, i++){
			ret[i] = (byte)winningComb[j];
		}
		ret[i] = (byte)score.getTickScore();
		i++;
		ret[i] = (byte)score.getCrossScore();
		return ret;
		
	}
	
	public static byte[] getClientToServerCellClickData(int cellid){
		byte[] ret = null;
		if(cellid<TTTEngineInterface.TOTAL_ITEMS_IN_TTT){
			ret = new byte[]{(byte)DATA_TYPE.CLIENT_TO_SERVER_CELLCLICK.ordinal(), (byte)cellid};
		}else{
			ret = new byte[]{(byte)DATA_TYPE.CLIENT_TO_SERVER_SYMBOL_CHANGE.ordinal(), (byte)cellid};
		}
		
		Log.d("TEST_OUT_LEN", ""+ret.length);
		for(int i = 0; i < ret.length; i++){
			Log.d(">>"+i, ""+ret[i]);
		}
		return ret;
	}
	
	
	public static TTTClientDummyEngine getClientDummyEngine( byte[] indata){
		return new TTTClientDummyEngine(indata);
	}
	
	public static class TTTClientDummyEngine implements TTTEngineInterface, TTTScoreInterface{
		
		private int[] ttt_data = new int[9];
		private int symToDraw = TTTEngineInterface.EMPTY;
		private int[] winningCombo = new int[3];
		private int tickScore = 0;
		private int crossScore = 0;
		
		private TTTClientDummyEngine( byte[] indata){
			int i = 0;
			if(indata[0] != DATA_TYPE.SERVER_TO_CLIENT_FULL_DATA.ordinal()){
				throw new IllegalArgumentException("Header info "+indata[0]+" not valid");
			}
			i++;
			for(int j = 0;j < ttt_data.length; j++, i++){
				ttt_data[j] = indata[i];
			}
			symToDraw = indata[i];
			i++;
			for(int j = 0;j < winningCombo.length; j++, i++){
				winningCombo[j] = indata[i];
			}
			
			tickScore = indata[i];
			i++;
			crossScore = indata[i];
			
		}

		@Override
		public int[] getTTTData() {
			return ttt_data;
		}

		@Override
		public boolean isGameStarted() {
			for (int i: ttt_data){
				if(i != 0){
					return true;
				}
			}
			return false;
		}

		@Override
		public int getSymbolToDrawId() {
			return symToDraw;
		}

		@Override
		public int getLastDrawnSymbol() {
			if(this.symToDraw == TTTEngineInterface.CROSS){
				return TTTEngineInterface.TICK;
			}else{
				return TTTEngineInterface.CROSS;
			}
		}

		@Override
		public int[] getWinningComb() {
			if(winningCombo[0] == -1){
				return null;
			}
			return winningCombo;
		}

		@Override
		public int getTickScore() {
			return tickScore;
		}

		@Override
		public int getCrossScore() {
			return crossScore;
		}
		
	}
}
