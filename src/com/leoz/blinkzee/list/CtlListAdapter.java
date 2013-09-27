package com.leoz.blinkzee.list;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leoz.blinkzee.R;
import com.leoz.bz.zbase.ZFile;
import com.leoz.bz.zfile.CtlBaseAdapter;
import com.leoz.bz.zfile.FileInfo;

public class CtlListAdapter extends CtlBaseAdapter {
	
    public CtlListAdapter(Context context, ZFile f) {
    	super(context, f);
    }
        
    public View getView(int position, View v, ViewGroup parent) {
    	
    	ListViewHolder holder;
    	
        if (v == null) {
        	
        	v = LayoutInflater.from(mContext).inflate(R.layout.list_row, null);
        	
            holder = new ListViewHolder();
            holder.icon = (ImageView) v.findViewById(R.id.row_icon);
            holder.name = (TextView) v.findViewById(R.id.row_name);
            holder.date = (TextView) v.findViewById(R.id.row_date);
            holder.type = (TextView) v.findViewById(R.id.row_type);
            holder.size = (TextView) v.findViewById(R.id.row_size);
            
            v.setTag(holder);
        } else {
            holder = (ListViewHolder) v.getTag();
        }
        
        // Set icon
        holder.icon.setTag(mFiles[position].getAbsolutePath());
        setViewBitmap(position, holder.icon);
        // Set name
        holder.name.setText(mFiles[position].getName());
        // Set date
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		Date lastModified = new Date(mFiles[position].lastModified());
		String date = sdf.format(lastModified);
		holder.date.setText(date);
        // Set type
        holder.type.setText(FileInfo.INSTANCE.getFileTypeFullText(mFiles[position]));
        // Set size
        holder.size.setText(FileInfo.INSTANCE.getFileSize(mFiles[position]));
        
        return v;
    }
    
	static class ListViewHolder {
        ImageView icon;
        TextView name;
        TextView date;
        TextView type;
        TextView size;
    }
}
