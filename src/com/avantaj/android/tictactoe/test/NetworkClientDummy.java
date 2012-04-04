package com.avantaj.android.tictactoe.test;

import android.util.Log;

import com.avantaj.android.tictactoe.network.NWDataListener;
import com.avantaj.android.tictactoe.network.NWInterface;


public class NetworkClientDummy implements NWInterface {

	private static NetworkClientDummy instance = new NetworkClientDummy();
	
	private NWDataListener listener = null;
	
	public static NetworkClientDummy getInstance() {
		return instance;
	}
	
	private NetworkClientDummy(){
		
	}

	@Override
	public void send(byte[] data) {
		Log.d("CLIENT INTERFACE", "Send "+data.length+"bytes");
		NetworkServerDummy.getInstance().receive(data);
		
	}
	
	public void receive(byte[] data) {
		Log.d("CLIENT INTERFACE", "Received "+data.length+"bytes");
		if(listener != null){
			listener.receive(data);
		}else{
			Log.d("CLIENT INTERFACE", "BLOCKING ALL CLIENT DATA");
		}
		
	}

	@Override
	public void registerDataListener(NWDataListener listener) {
//		NOT Needed for single player. 
//		this.listener = listener;
		
		
	}


}
