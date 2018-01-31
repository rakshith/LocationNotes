package com.justforchangesake.locationnotes.settings;

import android.os.Bundle;

import com.justforchangesake.locationnotes.R;
import com.justforchangesake.locationnotes.ui.BaseActivity;
import com.justforchangesake.locationnotes.uiUtils.UiManagerUtils;

public class SettingsActivity extends BaseActivity {

    private SettingsFragment notebooksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebooks);

        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        notebooksFragment = new SettingsFragment();
        UiManagerUtils.replaceFragment(this, notebooksFragment);
    }
}
