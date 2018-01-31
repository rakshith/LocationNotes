package com.justforchangesake.locationnotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.justforchangesake.locationnotes.drawermenu.NavigationDrawerFragment;
import com.justforchangesake.locationnotes.notes.NotesFragment;
import com.justforchangesake.locationnotes.settings.SettingsActivity;
import com.justforchangesake.locationnotes.ui.BaseActivity;
import com.justforchangesake.locationnotes.uiUtils.UiManagerUtils;


public class MainActivity extends BaseActivity implements NavigationDrawerFragment
        .NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private NotesFragment notesFragment;
    private Handler mHandler;

    // delay to launch nav drawer item, to allow close animation to play
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;
    public static MainActivity object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        object = this;
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mHandler = new Handler();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mActionBarToolbar);

        // Load NotesFragment when the app starts
        notesFragment = new NotesFragment();
        UiManagerUtils.replaceFragment(this, notesFragment);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mNavigationDrawerFragment.getDrawerToggle().syncState();
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        notesFragment.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        notesFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNavigationDrawerItemSelected(final int itemId) {

        // launch the target Activity after a short delay, to allow the close animation to play
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNavDrawerItem(itemId);
            }
        }, NAVDRAWER_LAUNCH_DELAY);

    }

    private void goToNavDrawerItem(int item){
        switch (item) {
            case 0:
                UiManagerUtils.replaceFragment(this, new NotesFragment());
                break;
            case 1:
                UiManagerUtils.openActivity(this, SettingsActivity.class);
                break;
        }
    }
}