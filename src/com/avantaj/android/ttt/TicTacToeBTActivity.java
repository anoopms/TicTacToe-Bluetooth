
package com.avantaj.android.ttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class TicTacToeBTActivity extends Activity {
    /** Called when the activity is first created. */
	
	Handler handler = new Handler(); 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launchmain);
        initialize();
    }
    
    private void initialize () {
		handler.postDelayed( new Runnable () {
		 
			public void run() {
				
				// Calling the next Activity.
				 Intent intent = new Intent(TicTacToeBTActivity.this, SelectPlayActivity.class);
			     startActivity(intent);
			     finish();

			}
			
		}, 3000);
	}
}