package com.avantaj.android.btinterface;

import com.avantaj.android.tictactoe.network.NWDataListener;
import com.avantaj.android.tictactoe.network.NWInterface;
import com.avantaj.android.ttt.R;
import com.avantaj.android.ttt.TicTacToeActivity;
import com.blue.BluetoothChatService;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class BTInterfaceModule extends Handler implements NWInterface{

	private static final String TAG = "BTInterfaceModule";
	private static final boolean D = true;
	
	  // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    private Context ctxt = null;
    private BluetoothChatService btservice = null;
    
    private static BTInterfaceModule instance = new BTInterfaceModule();
    
    private NWDataListener listener = null;
    
    public static  BTInterfaceModule getInstance() {
		return instance;
	}
    
    private BTInterfaceModule(){
    }
    
    public void setBTChatService(Context ctxt, BluetoothChatService btservice){
    	this.ctxt = ctxt;
    	this.btservice = btservice;
    }
	
	@Override
	public void send(byte[] data) {
		 if (btservice.getState() != BluetoothChatService.STATE_CONNECTED) {
	            Toast.makeText(ctxt, R.string.not_connected, Toast.LENGTH_SHORT).show();
	            return;
	        }
		 // Check that there's actually something to send
	        if (data.length > 0) {
	            // Get the message bytes and tell the BluetoothChatService to write
	        	if(D)Log.d("SENING MESSAGE", getHexString(data));
	        	btservice.write(data);
	        }
	}

	@Override
	public void registerDataListener(NWDataListener listener) {
		this.listener = listener;
		
	}
	
	private void processRead(byte[] data){
		if(data.length == 0){
			if(D) Log.d("processRead","LENGTH 0");
		}
		if(listener != null){
			if(D) Log.d("CALL LISTENER", getHexString(data));
			listener.receive(data);
		}
	}

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
        case MESSAGE_STATE_CHANGE:
            if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
            break;
        case MESSAGE_WRITE:
            byte[] writeBuf = (byte[]) msg.obj;
            // construct a string from the buffer
            if(D)Log.d("SENT_MESSAGE", getHexString(writeBuf));

            break;
        case MESSAGE_READ:
            byte[] readBuf = (byte[]) msg.obj;
            // construct a string from the valid bytes in the buffer
            if(D)Log.d("GOT_MESSAGE", getHexString(readBuf));
            processRead(readBuf);
            break;
        case MESSAGE_DEVICE_NAME:
            // save the connected device's name
        	
            String mConnectedDeviceName = msg.getData().getString(TicTacToeActivity.DEVICE_NAME);
            Toast.makeText(ctxt, "Connected to "
                           + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
            break;
        case MESSAGE_TOAST:
            Toast.makeText(ctxt, msg.getData().getString(TicTacToeActivity.TOAST),
                           Toast.LENGTH_SHORT).show();
            break;
        }
    }
    
    public String getHexString(byte[] vals){
    	String s ="";
    	for(byte c: vals){
    		s+=Integer.toHexString(c)+" ";
    	}
    	return s;
    }


}
