package com.justforchangesake.locationnotes.notes.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.justforchangesake.locationnotes.R;
import com.justforchangesake.locationnotes.notes.cards.adapter.NoteCheckboxAdapter;
import com.justforchangesake.locationnotes.utils.NoteManipulator;
import com.linearlistview.LinearListView;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by sraksh on 2/1/2015.
 */
public class NoteCheckBoxCard extends Card{

    protected TextView noteTitle;
    protected LinearListView cbNoteList;

    protected String title;
    protected String description;

    protected Context ctx;

    public NoteCheckBoxCard(Context context) {
        this(context, R.layout.card_note_checkbox);
    }

    public NoteCheckBoxCard(Context context, int innerLayout) {
        super(context, innerLayout);
        this.ctx = context;
        //init();
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
         noteTitle = (TextView) parent.findViewById(R.id.noteTitle);
         cbNoteList = (LinearListView) parent.findViewById(R.id.cbNoteList);
         loadRecords();
    }

    private void loadRecords() {
        noteTitle.setText(title);
        List<String> noteList = NoteManipulator.buildNoteListFromString(description);
        NoteCheckboxAdapter noteCheckboxAdapter = new NoteCheckboxAdapter(ctx, noteList);
        cbNoteList.setAdapter(noteCheckboxAdapter);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
