package com.leoz.blinkzee.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.leoz.blinkzee.R;
import com.leoz.blinkzee.list.CtlListFragment;
import com.leoz.bz.zbase.ZFile;
import com.leoz.bz.zclient.ZCltConnector;
import com.leoz.bz.zclient.ZCltService;
import com.leoz.bz.zclient.ZCltSettings;
import com.leoz.bz.zfile.FileOpen;

public class ZMainActivity extends Activity
implements FileOpen.OnDirSelectedListener,
           FileOpen.OnImageSelectedListener {

	private static final String TAG = "[z::main] ZMainActivity"; /// TODO: FIX ME
	
	private static CtlListFragment mListLeft = null;
	private static CtlListFragment mListRight = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	// Set title - begin
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        ZCltService.INSTANCE.init(this);
        ZCltService.INSTANCE.addReceiver(new ZMainReceiver());
        
        ZCltConnector.INSTANCE.start();
	    
    	// Set title - end
		setContentView(R.layout.main_view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.main_title);
		
		// Must be last!
		setData();
		
		Log.v(TAG, "onCreate"); /// FIX ME
    }

	@Override
	protected void onDestroy() {
		
    	Log.v(TAG, "onDestroy"); /// TODO: FIX ME
    	
    	ZCltConnector.INSTANCE.stop();
		
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		
    	Log.v(TAG, "onBackPressed"); /// TODO: FIX ME
    	
		super.onBackPressed();	    	
	}

	public void onDirSelected(ZFile f, int context) {	 
	    
		TextView v = null;
		
		if (mListLeft != null && mListLeft.getId() == context) {
			v = (TextView)this.findViewById(R.id.title_left);
			v.setText(f.getAbsolutePath());
			mListLeft.setFragmentData(f);
	    }
		
		if (mListRight != null && mListRight.getId() == context) {
			v = (TextView)this.findViewById(R.id.title_right);
			v.setText(f.getAbsolutePath());
			mListRight.setFragmentData(f);
	    }
	}

	public void onImageSelected(ZFile f) {
		
    	Log.v(TAG, "onImageSelected"); /// TODO: FIX ME
    	
		Intent i = new Intent();
		i.setClassName("com.leoz.bz.zview", "com.leoz.bz.zview.ZViewActivity");
		i.setData(f.getUri());
		startActivity(i);
	}
	
	
	private void setData() {
		mListLeft = (CtlListFragment) getFragmentManager().findFragmentById(R.id.list_view_left);
		mListRight = (CtlListFragment) getFragmentManager().findFragmentById(R.id.list_view_right);
	    
	    ZMainReceiver.setList(mListLeft);
	    ZMainReceiver.setGrid(mListRight);
	    
		if (mListLeft != null) {
			mListLeft.setAnimator(this.findViewById(R.id.title_left));			
		}				
	    
		if (mListRight != null) {
			mListRight.setAnimator(this.findViewById(R.id.title_right));			
		}
		
		if (mListLeft != null) {
			onDirSelected(ZCltSettings.INSTANCE.getAppDir(), mListLeft.getId());
		}				
		
		if (mListRight != null) {
			onDirSelected(ZCltSettings.INSTANCE.getAppDir(), mListRight.getId());
		}				
	}
}
