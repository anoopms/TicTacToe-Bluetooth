package com.avantaj.android.ttt;

import java.util.Collection;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avantaj.android.btinterface.BTInterfaceModule;
import com.avantaj.android.tictactoe.TTTEngine;
import com.avantaj.android.tictactoe.TTTImageMaps;
import com.avantaj.android.tictactoe.TTTScore;
import com.avantaj.android.tictactoe.TTTTheme;
import com.avantaj.android.tictactoe.control.CellClickListener2;
import com.avantaj.android.tictactoe.control.NewGameListener;
import com.avantaj.android.tictactoe.control.ResetScoreListener;
import com.avantaj.android.tictactoe.control.SelectMarkListener2;
import com.avantaj.android.tictactoe.control.SettingsListener;
import com.avantaj.android.tictactoe.twop.TwoPClientModule;
import com.avantaj.android.tictactoe.twop.TwoPManager;
import com.avantaj.android.tictactoe.twop.TwoPServerModule;
import com.avantaj.android.tictactoe.twop.TwoPManager.GameType;
import com.blue.BluetoothChatService;
import com.blue.DeviceListActivity;



public class TicTacToeActivity extends Activity {

	private static final String TAG = "TicTacToeActivity";
    private static final boolean D = true;
	
	////Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;
    
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    
	private BluetoothChatService mChatService = null;
	
	private BluetoothAdapter mBluetoothAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(!initPlayerConfig()){
			finish();
		}
		setContentView(R.layout.main);
		initGame();
	}
	
	private void initGame(){
		
		 
        OnClickListener listener = new CellClickListener2();
          
        Collection<Integer> layoutIds = TTTImageMaps.getInstance().getLayoutIds();
        for(int id:layoutIds){
        	LinearLayout layout = (LinearLayout) findViewById(id);
            layout.setOnClickListener(listener);
        }
        
        Button button = (Button)findViewById(R.id.reset_button);
        button.setOnClickListener(new NewGameListener());

        SelectMarkListener2.TickMarkListener tickMarkListener = new SelectMarkListener2.TickMarkListener();
        SelectMarkListener2.CrossMarkListener crossMarkListener  = new SelectMarkListener2.CrossMarkListener();
        
        
        View selectTick = findViewById(R.id.select_mark_tick_image);
        selectTick.setOnClickListener(tickMarkListener);
        
        View selectCross = findViewById(R.id.select_mark_cross_image);
        selectCross.setOnClickListener(crossMarkListener);
        
        View resetScore = findViewById(R.id.reset_score_button);
        resetScore.setOnClickListener(new ResetScoreListener());
        
        View settingsSelection = findViewById(R.id.settings_selection);
        settingsSelection.setOnClickListener(new SettingsListener());
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(!startPlayerConfig()){
			finish();
		}
		
		
    	setTheme();
    	
	}
	
	
	private boolean initPlayerConfig(){
		
		int position = getIntent().getExtras().getInt("index");
		switch(position){
		case SelectPlayActivity.SELECT_PLAY_SINGLE:
			return init1P();
		case SelectPlayActivity.SELECT_PLAY_MULTI:
			return init2P();
		}
		return false;
	}
	
	private boolean init1P(){
		return true;
	}
	
	 private void setInterfaces(){
		 
		GameType type = TwoPManager.getGameType();
    	
		if(type == GameType.MULTIPLAYER_SERVER){
			TwoPServerModule.getInstance().setContext(this);
			TwoPServerModule.getInstance().setServerNetworkInterface(TwoPManager.getServerInterface());
		}else if(type == GameType.MULTIPLAYER_CLIENT){
			TwoPClientModule.getInstance().setContext(this);
	    	TwoPClientModule.getInstance().setClientNetworkInterface(TwoPManager.getClientInterface());
		}else{
			throw new IllegalArgumentException("Invalid interface type for multiplayer "+type);
		}
	}
	 
	 private void setTheme(){
    	TTTTheme theme = TTTTheme.getInstance();
    	TTTImageMaps.getInstance().restoreImages(this,TTTEngine.getInstance(),TTTScore.getInstance());
    	theme.setTheme(this);
	 }
	
	private boolean init2P(){
		
		 mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            return false;
        }
		return true;
	}
	
	private boolean startPlayerConfig(){
		
		int position = getIntent().getExtras().getInt("index");
		switch(position){
		case SelectPlayActivity.SELECT_PLAY_SINGLE:
			return start1P();
		case SelectPlayActivity.SELECT_PLAY_MULTI:
			return start2P();
		}
		return false;
	}

	private boolean start1P(){
		return true;
	}
	
	private boolean start2P(){
		if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
		return true;
	}

	private boolean startBTServer() {
		if (D) Log.e(TAG, "+ ON RESUME +");

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mChatService != null) {
			if (D) Log.w(TAG, "mChatService not null");
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (D) Log.w(TAG, "state="+BluetoothChatService.STATE_NONE);
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
			}
		}
		return true;
	}

	
    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
	
	
	 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
	
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        Intent serverIntent = null;
	        switch (item.getItemId()) {
	        case R.id.start_game:
	        	Log.e("SETTING GAME TYPE", "start game"+"GameType.MULTIPLAYER_SERVER");
	        	TwoPManager.setGameType(GameType.MULTIPLAYER_SERVER);
	        	setInterfaces();
	        	
	            return startBTServer();
	        case R.id.join_game:
	            // Launch the DeviceListActivity to see devices and do scan
	        	Log.e("SETTING GAME TYPE", "join game"+"GameType.MULTIPLAYER_CLIENT");
	        	TwoPManager.setGameType(GameType.MULTIPLAYER_CLIENT);
	        	setInterfaces();
	            serverIntent = new Intent(TicTacToeActivity.this, DeviceListActivity.class);
	            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
	            return true;
	        case R.id.discoverable:
	            // Ensure this device is discoverable by others
	            ensureDiscoverable();
	            return true;
	        }
	        return false;
	    }
	 
	 private void setupChat() {
	        Log.d(TAG, "setupChat()");

	        // Initialize the BluetoothChatService to perform bluetooth connections
	        BTInterfaceModule handler = BTInterfaceModule.getInstance();
	        mChatService = new BluetoothChatService(this, handler);
	        handler.setBTChatService(this, mChatService);
	        

	  }
	 
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        try {
        	Log.d("TTT", "Connecting");
			mChatService.connect(device, secure);
			Log.d("TTT", "Connected");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
    }
	
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if(D) Log.d(TAG, "onActivityResult " + resultCode);
	        switch (requestCode) {
	        case REQUEST_CONNECT_DEVICE_SECURE:
	            // When DeviceListActivity returns with a device to connect
	            if (resultCode == Activity.RESULT_OK) {
	                connectDevice(data, true);
	            }
	            break;
	        case REQUEST_CONNECT_DEVICE_INSECURE:
	            // When DeviceListActivity returns with a device to connect
	            if (resultCode == Activity.RESULT_OK) {
	                connectDevice(data, false);
	            }
	            break;
	        case REQUEST_ENABLE_BT:
	            // When the request to enable Bluetooth returns
	            if (resultCode == Activity.RESULT_OK) {
	                // Bluetooth is now enabled, so set up a chat session
	                setupChat();
	            } else {
	                // User did not enable Bluetooth or an error occurred
	                Log.d(TAG, "BT not enabled");
	                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
	                finish();
	            }
	        }
	    }
	  


}
