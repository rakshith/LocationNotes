package com.justforchangesake.locationnotes.drawermenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.justforchangesake.locationnotes.R;

import java.util.ArrayList;

/**
 * Created by sraksh on 1/24/2015.
 */
public class DrawerAdapter extends BaseAdapter {

    /**
     * LayoutInflater instance for inflating the requested layout in the list view
     */
    private LayoutInflater mInflater;

    private ArrayList<String> mDataSet;

    /**
     * Default constructor
     */
    public DrawerAdapter(Context context, ArrayList<String> dataSet) {

        mInflater = LayoutInflater.from(context);
        mDataSet = dataSet;

    }

    public int getCount() {
        return mDataSet.size();
    }

    public Object getItem(int index) {
        return mDataSet.get(index);
    }

    public long getItemId(int index) {
        return index;
    }

    public View getView(int position, View recycledView, ViewGroup parent) {
        ViewHolder holder;

        if (recycledView == null) {

            holder = new ViewHolder();
            recycledView = mInflater.inflate(R.layout.item_drawer, parent, false);
            holder.title = (TextView) recycledView.findViewById(R.id.title);

            recycledView.setTag(holder);

        } else {
            holder = (ViewHolder) recycledView.getTag();
        }

        holder.title.setText(mDataSet.get(position));

        return recycledView;
    }

    private static class ViewHolder {
        TextView title;
    }
}
