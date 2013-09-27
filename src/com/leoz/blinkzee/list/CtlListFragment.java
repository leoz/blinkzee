package com.leoz.blinkzee.list;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.leoz.blinkzee.R;
import com.leoz.bz.zbase.ZFile;
import com.leoz.bz.zfile.FileOpen;
import com.leoz.bz.zgrid.CtlGridAdapter;
import com.leoz.ext.flipview.AnimationFactory;
import com.leoz.ext.flipview.AnimationFactory.FlipDirection;

public class CtlListFragment extends Fragment {
	
	private CtlListAdapter mListAdapter;
	private CtlGridAdapter mGridAdapter;
	
	private View mMain = null;	
	private ViewFlipper mFlipper = null;
	private ListView mListView = null;
	private GridView mGridView = null;
	
	CtlListFragment mFragment = this;
	    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mMain = inflater.inflate(R.layout.list_view, container, false);
		setViews();
        return mMain;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		FileOpen.setActivity(activity);
	}

	public void setFragmentData(final ZFile f) {
		setListData(f);
		setGridData(f);
	}
	
	private void setListData(final ZFile f) {
		if (mListAdapter == null) {
			mListAdapter = new CtlListAdapter(getActivity(), f);
			mListView.setAdapter(mListAdapter);
        }
        else {
        	mListView.setAdapter(null);
        	mListAdapter.clearData();
        	mListAdapter.setData(f);
        	mListView.setAdapter(mListAdapter);
        }
	}	
	
	private void setGridData(final ZFile f) {
        if (mGridAdapter == null) {
        	mGridAdapter = new CtlGridAdapter(getActivity(), f);
        	mGridView.setAdapter(mGridAdapter);
        }
        else {
        	mGridView.setAdapter(null);
        	mGridAdapter.clearData();
        	mGridAdapter.setData(f);
        	mGridView.setAdapter(mGridAdapter);
        }
	}	
	
    public void updateListItem(String file, Bitmap b) {
        int childCount = mListView.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = mListView.getChildAt(i);
            ImageView iv = (ImageView) v.findViewById(R.id.row_icon);
            String name = (String) iv.getTag();
            if (name.equals(file)) {
            	iv.setImageBitmap(b);
            }
        }
    }
    
    public void updateGridItem(String file, Bitmap b) {
        int childCount = mGridView.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View v = mGridView.getChildAt(i);
            ImageView iv = (ImageView) v.findViewById(1002);
            String name = (String) iv.getTag();
            if (name.equals(file)) {
            	iv.setImageBitmap(b);
            }
        }
    }
    
    private void setViews() {
    	
    	// Set Flipper
		mFlipper = (ViewFlipper) mMain.findViewById(R.id.view_flipper);

		// Set List
		mListView = (ListView) mMain.findViewById(R.id.main_list);
		mListView.setOnItemClickListener(mListClickListener);		    	

		// Set Grid
		mGridView = (GridView) mMain.findViewById(R.id.main_grid);
		mGridView.setOnItemClickListener(mGridClickListener);		
    }
    
	private OnItemClickListener mListClickListener = new OnItemClickListener() {
		
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    	
			ZFile f = (ZFile) mListAdapter.getItem(position);
			FileOpen.fileSelected (f, mFragment.getId());
	    }
	};	
	
	private OnItemClickListener mGridClickListener = new OnItemClickListener() {
		
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    	
			ZFile f = (ZFile) mGridAdapter.getItem(position);
			FileOpen.fileSelected (f, mFragment.getId());
	    }
	};
	
	public void setAnimator(View handle) {
		final ViewAnimator viewAnimator = this.mFlipper;
		
		if (viewAnimator != null) {
	        viewAnimator.setOnClickListener(new OnClickListener() { 
				public void onClick(View v) { 
					AnimationFactory.flipTransition(viewAnimator, FlipDirection.LEFT_RIGHT);
				}
	        	
	        });			
	        handle.setOnClickListener(new OnClickListener() { 
				public void onClick(View v) { 
					AnimationFactory.flipTransition(viewAnimator, FlipDirection.LEFT_RIGHT);
				}
	        });		
		}						
	}
}


