package com.justforchangesake.locationnotes.notes.cards.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.justforchangesake.locationnotes.R;
import com.justforchangesake.locationnotes.uiUtils.ViewHolder;

import java.util.List;

/**
 * Created by sraksh on 2/1/2015.
 */
public class NoteCheckboxAdapter extends BaseAdapter{

    private List<String> noteList;
    private Context ctx;
    private LayoutInflater inflater;

    public NoteCheckboxAdapter(Context context, List<String> noteList){
        this.noteList = noteList;
        this.ctx = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public String getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater
                    .inflate(R.layout.card_note_checkbox_detail, parent, false);
        }

        TextView etNote = ViewHolder.get(convertView, R.id.etNote);
        etNote.setText(getItem(position));

        CheckBox cbNote = ViewHolder.get(convertView, R.id.cbNote);
        cbNote.setOnCheckedChangeListener(new NoteCheckChangeListener(etNote));

        final ImageButton btnRemove = ViewHolder.get(convertView, R.id.btnRemove);

        return convertView;
    }

    class NoteCheckChangeListener implements CompoundButton.OnCheckedChangeListener{
        TextView etNote;
        public NoteCheckChangeListener(TextView etNote){
            this.etNote = etNote;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                        etNote.setPaintFlags(etNote.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    etNote.setPaintFlags(0);
                }
        }
    }
}
