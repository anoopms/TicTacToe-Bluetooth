package com.avantaj.android.tictactoe;

public interface TTTEngineInterface {
	
	
	/* Dont give values above 127. Coz the data are passed as +ve bytes */
	public static final int EMPTY = 0;
	public static final int TICK = 1;
	public static final int CROSS = 2;
	public static final int TOTAL_ITEMS_IN_TTT = 9;
	public static final int CELL_CHOOSE_ID_TICK =100;
	public static final int CELL_CHOOSE_ID_CROSS =101;
	
	public enum ClickSource{
		SERVER,
		CLIENT
	}
	
	
	public int[] getTTTData();
	
	public boolean isGameStarted();
	
	public int getSymbolToDrawId();
	
	public int getLastDrawnSymbol();
	
	public int[] getWinningComb();
	

}
