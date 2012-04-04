package com.avantaj.android.tictactoe;

import java.util.Collection;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.avantaj.android.tictactoe.control.CellClickListener2;
import com.avantaj.android.tictactoe.control.NewGameListener;
import com.avantaj.android.tictactoe.control.ResetScoreListener;
import com.avantaj.android.tictactoe.control.SelectMarkListener2;
import com.avantaj.android.tictactoe.control.SettingsListener;
import com.avantaj.android.tictactoe.twop.TwoPClientModule;
import com.avantaj.android.tictactoe.twop.TwoPManager;
import com.avantaj.android.tictactoe.twop.TwoPServerModule;
import com.avantaj.android.ttt.R;


/**
 * @deprecated
 * @author ms
 *
 */
public class TicTacToeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	Log.d("ON CREATE", "On Create Called");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
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
    
    private void setTheme(){
    	TTTTheme theme = TTTTheme.getInstance();
    	TTTImageMaps.getInstance().restoreImages(this,TTTEngine.getInstance(),TTTScore.getInstance());
    	theme.setTheme(this);
    }
    
    @Override
    protected void onStart() {
    	Log.d("TTT ACTIVITY", "START");
    	super.onStart();
    	
    	setInterfaces();
    	
    	setTheme();
         TTTImageMaps.getInstance().updateScore(TTTScore.getInstance(), this);
    }
    
    private void setInterfaces(){
    	TwoPClientModule.getInstance().setContext(this);
    	TwoPServerModule.getInstance().setContext(this);
    	TwoPClientModule.getInstance().setClientNetworkInterface(TwoPManager.getClientInterface());
    	TwoPServerModule.getInstance().setServerNetworkInterface(TwoPManager.getServerInterface());
    }
    
    @Override
    protected void onResume() {
    	Log.d("TTT ACTIVITY", "ON RESUME");
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	Log.d("TTT ACTIVITY", "ON PAUSE");
    	super.onPause();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	Log.d("TTT ACTIVITY", "ON STOP");
//    	TTTEngine.getInstance().reset_tttData();
    }
}
