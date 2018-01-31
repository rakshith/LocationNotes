package com.justforchangesake.locationnotes.notes;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import com.commonsware.cwac.richedit.RichEditText;
import com.justforchangesake.locationnotes.R;
import com.justforchangesake.locationnotes.fragment.BaseFragment;
import com.justforchangesake.locationnotes.notes.adapter.NoteAdapter;
import com.justforchangesake.locationnotes.notes.database.entities.Note;
import com.justforchangesake.locationnotes.notes.providers.NoteFileContentProvider;
import com.justforchangesake.locationnotes.uiUtils.DatePickerFragment;
import com.justforchangesake.locationnotes.uiUtils.TimePickerFragment;
import com.justforchangesake.locationnotes.uiUtils.UiManagerUtils;
import com.justforchangesake.locationnotes.utils.DateManupulateUtils;
import com.justforchangesake.locationnotes.utils.NoteManipulator;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.FOCUS_DOWN;
import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteContentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoteContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteContentFragment extends BaseFragment implements TextWatcher {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static Note note = new Note();

    private Handler mHandler;

    private static EditText noteTitle;
    private static RichEditText noteDetail;
    private static ListView noteListView;
    private static NoteAdapter noteAdapter;

    private Toolbar toolbar;
    private EditText etAddNote;
    private ImageButton ibRemindMeClock;
    private TextView tvTomorrow;
    public static TextView tvMorning;
    public static TextView tvEditedNote;
    private List<String> noteList = new ArrayList<String>();

    // Wait for Fragment animation and then detach fragment
    private static final int FRAGMENT_DETACH_DELAY = 250;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView ivNoteImage;

    private Uri mCapturedImageURI;
    private String imgPath;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteContentFragment newInstance(String param1, String param2) {
        NoteContentFragment fragment = new NoteContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NoteContentFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_content, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandler = new Handler();

        ivNoteImage = (ImageView)getActivity().findViewById(R.id.ivNoteImage);

        noteTitle = (EditText)getActivity().findViewById(R.id.etNoteTitle);
        noteTitle.addTextChangedListener(this);
        noteDetail = (RichEditText)getActivity().findViewById(R.id.etNoteConent);
        noteDetail.enableActionModes(true);
        noteDetail.addTextChangedListener(this);
        noteListView = (ListView)getActivity().findViewById(R.id.noteListView);

        tvMorning = (TextView)getActivity().findViewById(R.id.tvMorning);
        tvMorning.setOnClickListener(new MorningClickListener());

        tvTomorrow = (TextView)getActivity().findViewById(R.id.tvTomorrow);
        tvTomorrow.setOnClickListener(new TomorrowClickListener());

        ibRemindMeClock = (ImageButton)getActivity().findViewById(R.id.ibRemindMeClock);
        ibRemindMeClock.setOnClickListener(new ReminderClickListener());

        tvEditedNote = (TextView)getActivity().findViewById(R.id.tvEditedNote);

        View noteAddview = ((LayoutInflater)NotesContentActivity.object.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_note_add_item, null);

        etAddNote = (EditText)noteAddview.findViewById(R.id.etAddNote);
        etAddNote.requestFocus(FOCUS_DOWN);
        etAddNote.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                WrapperListAdapter wrapperListAdapter = (WrapperListAdapter)noteListView.getAdapter();

                ListAdapter adapter = null;

                if(wrapperListAdapter.getWrappedAdapter() instanceof NoteAdapter){
                    adapter = wrapperListAdapter.getWrappedAdapter();
                    ((NoteAdapter)adapter).getNotes().add(v.getText().toString());
                    noteList = new ArrayList<String>(((NoteAdapter)adapter).getNotes());
                }else{
                    adapter = wrapperListAdapter.getWrappedAdapter();
                }

                ((NoteAdapter)adapter).notifyDataSetChanged();
                etAddNote.setText("");
                return false;
            }
        });
        noteListView.addFooterView(noteAddview);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_done_white_24dp);
        toolbar.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_400));
        toolbar.inflateMenu(R.menu.menu_notes_content);

        toolbar.setOnMenuItemClickListener(new ToolbarMenuItemListener());
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(note == null) {
                    note = new Note(UiManagerUtils.extractTextFromEditText(noteTitle), UiManagerUtils.extractTextFromEditText(noteDetail));
                }else{
                    note.setTitle(UiManagerUtils.extractTextFromEditText(noteTitle));
                    note.setDescription(UiManagerUtils.extractTextFromEditText(noteDetail));
                }
                Intent returnIntent = new Intent();

                if(noteList != null && !noteList.isEmpty()){
                    note.setDescription(NoteManipulator.buildNoteString(noteList));
                }
                returnIntent.putExtra("noteData", note);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
                getActivity().onBackPressed();
            }
        });

        note = getActivity().getIntent().getParcelableExtra("noteData");

        if(note != null){
            noteTitle.setText(note.getTitle());
            noteDetail.setText(note.getDescription());
            String[] date = DateManupulateUtils.getDayMonthArray(note.getEditDate());
            tvEditedNote.setText("Edited "+date[1]+", "+date[0]);
            if(note.isNoteCheckbox()){
                MenuItem menuItem = toolbar.getMenu().findItem(R.id.action_show_checkboxes);
                toggleCheckBoxes(menuItem);
            }
        }else{
            note = new Note();
        }
    }

    private boolean checkNoteContent(){
        boolean flag = false;

        if(UiManagerUtils.extractTextFromEditText(noteTitle).isEmpty() && UiManagerUtils.extractTextFromEditText(noteDetail).isEmpty()){
            flag = true;
        }

        return flag;
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void convertTextToList(){
                String noteDetailString = UiManagerUtils.extractTextFromEditText(noteDetail);
                noteList = new ArrayList<String>();
                noteList = NoteManipulator.buildNoteListFromString(noteDetailString);
                noteAdapter = new NoteAdapter(NotesContentActivity.object, noteList);
                noteListView.setAdapter(noteAdapter);
    }

    private void convertListToText(){
        String noteDetailString = NoteManipulator.buildNoteString(noteList);
        noteDetail.setText(noteDetailString);
    }

    private void toggleCheckBoxes(MenuItem menuItem){
        if(menuItem.getTitle().toString().contains("Show")){
            menuItem.setTitle("Hide checkboxes");
            noteDetail.setVisibility(GONE);
            noteListView.setVisibility(VISIBLE);
            convertTextToList();
            if(note != null)

                note.setNoteCheckbox(true);
        }else{
            menuItem.setTitle("Show checkboxes");
            convertListToText();
            noteDetail.setVisibility(VISIBLE);
            noteListView.setVisibility(GONE);
            if(note != null)
                note.setNoteCheckbox(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        note.setEditDate(DateManupulateUtils.getFormatDateTime(new Date()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    class ToolbarMenuItemListener implements Toolbar.OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.action_show_checkboxes:
                    toggleCheckBoxes(menuItem);
                    return true;
                case R.id.action_snap_pic:
                    takePicture();
                    makeToolBarTransparent();
                    return true;
            }
            return false;
        }
    }

    private void makeToolBarTransparent(){
        int visibility = ivNoteImage.getVisibility();

        switch(visibility){
            case View.VISIBLE:
                    toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                break;
        }

    };

    private void takePicture(){
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, NoteFileContentProvider.CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            File out = new File(NotesContentActivity.object.getFilesDir(), "newImage.jpg");

            if(!out.exists()){
                UiManagerUtils.showToast(NotesContentActivity.object, "Error capturing file", 2000);
                return;
            }
            /*DisplayMetrics displayMetrics = new DisplayMetrics();
            NotesContentActivity.object.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int heightPixels = displayMetrics.heightPixels;
            int widthPixels = displayMetrics.widthPixels;*/

            Bitmap imageBitmap =  new BitmapDrawable(NotesContentActivity.object.getResources(),out.getAbsolutePath()).getBitmap();
            int nh = imageBitmap.getHeight()/2;
            Bitmap scaledImg = Bitmap.createScaledBitmap(imageBitmap, 1000, nh, true);
            ivNoteImage.setVisibility(View.VISIBLE);
            ivNoteImage.setImageBitmap(scaledImg);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// CREATE A MATRIX FOR THE MANIPULATION

        Matrix matrix = new Matrix();

// RESIZE THE BIT MAP

        matrix.postScale(scaleWidth, scaleHeight);

// RECREATE THE NEW BITMAP

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }

    class TomorrowClickListener implements OnClickListener, PopupMenu.OnMenuItemClickListener {

        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(NotesContentActivity.object, view);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.popup_menu_tomorrow);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.item_today:
                    setTodayReminder();
                    return true;
                case R.id.item_tomorrow:
                    setTomorrowReminder();
                    return true;
                case R.id.item_next_Sunday:
                    setNextSunday(new LocalDateTime());
                    return true;
                case R.id.item_pick_date:
                    pickDate();
                    return true;
            }
            return false;
        }
    }

    private void pickDate() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(NotesContentActivity.object.getSupportFragmentManager(), "datePicker");
    }

    private void setTodayReminder() {
        tvTomorrow.setText("Today");
        checkTime();
        DateManupulateUtils.getDateWithouTime();
    }

    private void checkTime(){
        LocalTime localTime = DateManupulateUtils.getTimeWithoutDate();


    }

    private LocalDateTime setNextSunday(LocalDateTime d) {
        tvTomorrow.setText("Next Sunday");
        setMorningReminder();
        return DateManupulateUtils.calcNextSunday(d);
    }

    private void setTomorrowReminder() {
        tvTomorrow.setText("Tomorrow");
        DateManupulateUtils.getFullDateWithTime().plusDays(1);
    }

    class MorningClickListener implements OnClickListener, PopupMenu.OnMenuItemClickListener {

        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(NotesContentActivity.object, view);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.popup_menu_morning);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.item_morning:
                    setMorningReminder();
                    return true;
                case R.id.item_afternoon:
                    setAfternoonReminder();
                    return true;
                case R.id.item_next_evening:
                    setEveningReminder();
                    return true;
                case R.id.item_next_night:
                    setNightReminder();
                    return true;
                case R.id.item_pick_time:
                    pickTime();
                    return true;
            }
            return false;
        }
    }

    private void pickTime() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(NotesContentActivity.object.getSupportFragmentManager(), "timePicker");
    }

    private void setNightReminder() {
            tvMorning.setText("8:00 PM");
    }

    private void setEveningReminder() {
            tvMorning.setText("5:00 PM");
    }

    private void setAfternoonReminder() {
            tvMorning.setText("1:00 AM");
    }

    private void setMorningReminder() {
        LocalTime morningTime = new LocalTime(9,0);
        note.getTimeReminder().setMorning(new Time(morningTime.getHourOfDay(), morningTime.getMinuteOfHour(), morningTime.getSecondOfMinute()));
        tvMorning.setText("9:00 AM");
    }


    class ReminderClickListener implements OnClickListener, PopupMenu.OnMenuItemClickListener {

        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(NotesContentActivity.object, view);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.popup_reminder);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch(menuItem.getItemId()) {
                case R.id.item_time_reminder:
                    return true;
                case R.id.item_location_reminder:
                    return true;
            }
                return false;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
