package com.avantaj.android.tictactoe.test;

import android.util.Log;

import com.avantaj.android.tictactoe.network.NWDataListener;
import com.avantaj.android.tictactoe.network.NWInterface;


public class NetworkServerDummy implements NWInterface {

	private static NetworkServerDummy instance = new NetworkServerDummy();
	
	private NWDataListener listener = null;
	
	public static NetworkServerDummy getInstance() {
		return instance;
	}
	
	private NetworkServerDummy(){
		
	}

	@Override
	public void send(byte[] data) {
		Log.d("SERVER INTERFACE", "Send "+data.length+"bytes");
		NetworkClientDummy.getInstance().receive(data);
		
	}
	
	public void receive(byte[] data) {
		Log.d("SERVER INTERFACE", "Receive "+data.length+"bytes");
		if(listener != null){
			listener.receive(data);
		}else{
			Log.d("SERVER INTERFACE", "BLOCKING ALL SERVER DATA");
		}
		
	}

	@Override
	public void registerDataListener(NWDataListener listener) {
		this.listener = listener;
		
	}


}
