package com.justforchangesake.locationnotes.notes;

import android.os.Bundle;

import com.justforchangesake.locationnotes.R;
import com.justforchangesake.locationnotes.ui.BaseActivity;
import com.justforchangesake.locationnotes.uiUtils.UiManagerUtils;

public class NotesContentActivity extends BaseActivity {

    private NoteContentFragment noteContentFragment;

    public static NotesContentActivity object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_content);

        object = this;

        mActionBarToolbar.setTitle("");
        mActionBarToolbar.setBackgroundColor(getResources().getColor(R.color.md_light_blue_500));

        noteContentFragment = new NoteContentFragment();
        noteContentFragment.setRetainInstance(true);
        UiManagerUtils.replaceFragment(this, new NoteContentFragment());
    }
}
