package com.leoz.blinkzee.splitter;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.leoz.blinkzee.R;
import com.leoz.blinkzee.list.CtlListFragment;

public class SplitterFragment extends Fragment implements OnTouchListener  {

	private static final String TAG = "SplitterFragment"; /// TODO: FIX ME
	
	private static final float SPLT_WEIGHT = 5;
	private static final float LIST_WEIGHT = 495;
	private static final float GRID_WEIGHT = 500;

	private static final float LIST_WEIGHT_MIN = 300;
	private static final float GRID_WEIGHT_MIN = 100;
	
	private static final float TOTAL_WEIGHT = SPLT_WEIGHT + LIST_WEIGHT + GRID_WEIGHT;
		
	private static final float LIST_WEIGHT_MAX = TOTAL_WEIGHT - SPLT_WEIGHT - GRID_WEIGHT_MIN;
	private static final float GRID_WEIGHT_MAX = TOTAL_WEIGHT - SPLT_WEIGHT - LIST_WEIGHT_MIN;
	
	private View splt_view = null;
	private View list_view = null;
	private View grid_view = null;
	
	private int pos_diff = 0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListView ();
		setGridView ();
		initLayouts ();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		splt_view = new Button (getActivity());
		splt_view.setBackgroundResource(R.drawable.tray_handle);
		splt_view.setDrawingCacheEnabled(true);
		splt_view.setOnTouchListener(this);
		return splt_view;
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			
			  Log.v(TAG, "onTouch : ACTION_DOWN : pos : " + splt_view.getRight() + " : " + event.getRawX()); /// TODO: FIX ME
			  
			  pos_diff = (int) (splt_view.getRight() - event.getRawX()); /// TODO: FIX ME
			  
			  splt_view.setBackgroundResource(R.drawable.tray_handle_pressed);
		}
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			
			  Log.v(TAG, "onTouch : ACTION_UP"); /// TODO: FIX ME
			  
			  pos_diff = 0; /// TODO: FIX ME
			  
			  splt_view.setBackgroundResource(R.drawable.tray_handle_normal);
		}
		else if (event.getAction() == MotionEvent.ACTION_MOVE) {			
			int old_right = splt_view.getRight();
			int new_right = (int) event.getRawX();
			int diff = new_right - old_right + pos_diff;
			
//			Log.v(TAG, "onTouch : ACTION_MOVE : " + diff + " :: "+ event.getX() + " :: " + event.getRawX()); /// TODO: FIX ME

			moveLayouts(diff);
		}
		return false;
	}
		
	private void initLayouts() {
				
		// Get layouts
		LinearLayout.LayoutParams splt_params = (LinearLayout.LayoutParams) splt_view.getLayoutParams();
		LinearLayout.LayoutParams list_params = (LinearLayout.LayoutParams) list_view.getLayoutParams();
		LinearLayout.LayoutParams grid_params = (LinearLayout.LayoutParams) grid_view.getLayoutParams();
		
		splt_params.width = 8;
				
		splt_params.weight = SPLT_WEIGHT;
		list_params.weight = LIST_WEIGHT;
		grid_params.weight = GRID_WEIGHT;
				
		// Set layouts
		splt_view.setLayoutParams(splt_params);
		list_view.setLayoutParams(list_params);
		grid_view.setLayoutParams(grid_params);				
	}
	
	private void moveLayouts(int diff) {
		
		// Get layouts
		LinearLayout.LayoutParams splt_params = (LinearLayout.LayoutParams) splt_view.getLayoutParams();
		LinearLayout.LayoutParams list_params = (LinearLayout.LayoutParams) list_view.getLayoutParams();
		LinearLayout.LayoutParams grid_params = (LinearLayout.LayoutParams) grid_view.getLayoutParams();
		
		int old_width = splt_view.getWidth();
		float unit = old_width / splt_params.weight;
		
		Log.v(TAG, "moveLayouts : unit: " + unit); /// TODO: FIX ME		
		
		float shift = diff / unit;
		
		float list_weight = list_params.weight + shift;
		float grid_weight = grid_params.weight - shift;
		
		// Check minimum weight
		if (list_weight < LIST_WEIGHT_MIN) {
			list_weight = LIST_WEIGHT_MIN;
			grid_weight = GRID_WEIGHT_MAX;
		}
		if (grid_weight < GRID_WEIGHT_MIN) {
			grid_weight = GRID_WEIGHT_MIN;
			list_weight = LIST_WEIGHT_MAX;
		}
		
		// Change layouts
		if (list_params.weight != list_weight &&
			grid_params.weight != grid_weight) {
			
			// Set weight
			list_params.weight = list_weight;
			grid_params.weight = grid_weight;
					
			// Set layouts
			list_view.setLayoutParams(list_params);
			grid_view.setLayoutParams(grid_params);					
		}
	}

	private void setListView() {
		if (list_view == null) {
		    CtlListFragment fragment = (CtlListFragment) getFragmentManager()
		            .findFragmentById(R.id.list_view_left);
		    
		    if (fragment != null) {
		    	list_view = fragment.getView();
		    }					
		}
	}
	
	private void setGridView() {
		if (grid_view == null) {
			CtlListFragment fragment = (CtlListFragment) getFragmentManager()
		            .findFragmentById(R.id.list_view_right);
		    
		    if (fragment != null) {
		    	grid_view = fragment.getView();
		    }					
		}
	}
}
