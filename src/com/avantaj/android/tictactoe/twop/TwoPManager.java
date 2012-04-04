package com.avantaj.android.tictactoe.twop;

import android.util.Log;

import com.avantaj.android.btinterface.BTInterfaceModule;
import com.avantaj.android.tictactoe.network.NWInterface;
import com.avantaj.android.tictactoe.test.NetworkClientDummy;
import com.avantaj.android.tictactoe.test.NetworkServerDummy;

public class TwoPManager {
	
	public enum GameType{
		SINGLE_PLAYER,
		MULTIPLAYER_SERVER,
		MULTIPLAYER_CLIENT
	}
	
	
	private static GameType type = GameType.SINGLE_PLAYER;
	
	public static GameType getGameType(){
		return type;
	}
	
	public static void setGameType(GameType type) {
		Log.e("Setting game type", type.ordinal()+"");
		TwoPManager.type = type;
	}
	
	public static NWInterface getServerInterface(){
		
		switch (type) {
		case SINGLE_PLAYER:
			return NetworkServerDummy.getInstance();
		case MULTIPLAYER_CLIENT:
			return BTInterfaceModule.getInstance();
		case MULTIPLAYER_SERVER:
			return BTInterfaceModule.getInstance();
		}
		return null;
	}
	
	public static NWInterface getClientInterface(){
		switch (type) {
		case SINGLE_PLAYER:
			return NetworkClientDummy.getInstance();
		case MULTIPLAYER_CLIENT:
			return BTInterfaceModule.getInstance();
		case MULTIPLAYER_SERVER:
			return BTInterfaceModule.getInstance();
		}
		return null;
	}

}
