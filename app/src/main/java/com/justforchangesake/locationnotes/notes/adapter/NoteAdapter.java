package com.justforchangesake.locationnotes.notes.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.justforchangesake.locationnotes.R;
import com.justforchangesake.locationnotes.uiUtils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sraksh on 1/30/2015.
 */
public class NoteAdapter extends ArrayAdapter<String> {

    private Context ctx;

    private List<String> notes = new ArrayList<String>();

    private NoteAdapter noteAdapter;

    public NoteAdapter(Context context, List<String> notes){
        super(context,R.layout.layout_note_todo_list_item, notes);
        this.ctx = context;
        noteAdapter = this;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public String getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.layout_note_todo_list_item, parent, false);
        }

        EditText etNote = ViewHolder.get(convertView, R.id.etNote);
        etNote.requestFocus(View.FOCUS_UP);
        etNote.addTextChangedListener(new NoteTextWatcher(position));
        etNote.setText(getItem(position));

        CheckBox cbNote = ViewHolder.get(convertView, R.id.cbNote);
        cbNote.setOnCheckedChangeListener(new NoteCheckChangeListener(etNote));

        final ImageButton btnRemove = ViewHolder.get(convertView, R.id.btnRemove);

        return convertView;
    }

    class NoteCheckChangeListener implements CompoundButton.OnCheckedChangeListener{
        TextView etNote;
        String noteContent;
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


   class NoteTextWatcher implements TextWatcher{

           int position;
           public NoteTextWatcher(int position){
               this.position = position;
           }

           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               if(s.length() == 0){
                  if(notes.size() > position){
                      notes.remove(position);
                      notifyDataSetChanged();
                  }
               }
           }
   }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
}
