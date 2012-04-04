package com.avantaj.android.tictactoe;

public class TTTScore implements TTTScoreInterface{
	
	
	private static final TTTScore instance = new TTTScore();
	private int tickScore = 0;
	private int crossScore = 0;
	
	private TTTScore(){	
	}
	
	public static TTTScore getInstance() {
		return instance;
	}
	
	public void incrementScore(int symId){
		if(symId == TTTEngineInterface.TICK){
			incrementTickScore();
		}else{
			incrementCrossScore();
		}
	}
	
	public void incrementTickScore() {
		this.tickScore++;
	}
	
	public void incrementCrossScore() {
		this.crossScore++;
	}
	
	@Override
	public int getTickScore() {
		return tickScore;
	}
	
	@Override
	public int getCrossScore() {
		return crossScore;
	}
	
	public void reset(){
		tickScore = 0; 
		crossScore = 0;
	}

}
