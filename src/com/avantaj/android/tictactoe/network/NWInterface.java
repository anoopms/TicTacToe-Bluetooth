package com.avantaj.android.tictactoe.network;

public interface NWInterface {
	
	public void send(byte[] data);
	
	public void registerDataListener(NWDataListener listener);
	
	
}
