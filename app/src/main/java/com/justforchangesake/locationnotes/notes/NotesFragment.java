package com.justforchangesake.locationnotes.notes;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.justforchangesake.locationnotes.MainActivity;
import com.justforchangesake.locationnotes.R;
import com.justforchangesake.locationnotes.fragment.BaseFragment;
import com.justforchangesake.locationnotes.notes.cards.NoteCheckBoxCard;
import com.justforchangesake.locationnotes.notes.cards.NoteDetailCard;
import com.justforchangesake.locationnotes.notes.database.entities.Note;
import com.justforchangesake.locationnotes.settings.SettingsActivity;
import com.justforchangesake.locationnotes.uiUtils.UiManagerUtils;
import com.justforchangesake.locationnotes.utils.AppStartConfig;
import com.justforchangesake.locationnotes.utils.DateManupulateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.listener.UndoBarController;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends BaseFragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private CardArrayAdapter mCardArrayAdapter;
    private UndoBarController mUndoBarController;
    private CardListView mListView;
    private String[] mCardIds;
    private static final String BUNDLE_IDS="BUNDLE_IDS";
    private static final String BUNDLE_IDS_UNDO="BUNDLE_IDS_UNDO";

    private static final int SAVE_NOTE_CONTENT = 1;

    private FloatingActionButton fabTextNoteBtn;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        switch (AppStartConfig.checkAppStart(getActivity())) {
            case NORMAL:
                // We don't want to get on the user's nerves
                break;
            case FIRST_TIME_VERSION:
                // TODO show what's new
                break;
            case FIRST_TIME:
                // TODO show a tutorial
                Note welcomeNote = new Note(getResources().getString(R.string.welcome_note_title), getResources().getString(R.string.welcome_note_description));
                welcomeNote.setCreateDate(DateManupulateUtils.getFormatDateTime(new Date()));
                welcomeNote.save();
                break;
            default:
                break;
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView = (CardListView) getActivity().findViewById(R.id.carddemo_list_gplaycard);
        fabTextNoteBtn = (FloatingActionButton) getActivity().findViewById(R.id.fabTextNoteBtn);
        fabTextNoteBtn.setOnClickListener(this);
        initCards();

        if (savedInstanceState!=null){
            mCardArrayAdapter.getUndoBarController().onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.fabTextNoteBtn:
                    UiManagerUtils.openActivityForResult(this, NotesContentActivity.class, SAVE_NOTE_CONTENT);
                    break;
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch ( requestCode ) {
            case SAVE_NOTE_CONTENT:
                if (resultCode == Activity.RESULT_OK) {
                    Note note = data.getExtras().getParcelable("noteData");
                    if(note != null){
                        if(note.getId() == 0) note.setId(null);
                        if(note.getTitle() != null && !note.getTitle().trim().isEmpty()) {
                            note.setCreateDate(DateManupulateUtils.getFormatDateTime(new Date()));
                            note.save();
                        }
                        loadNotes();
                    }else{
                        UiManagerUtils.showToast(getActivity().getBaseContext(),"Note not found", 2000);
                    }
                }
            break;
        }
    }

    private void initCards() {
        loadNotes();
    }

    private void loadNotes(){
        ArrayList<Card> cards = new ArrayList<Card>();

        List<Note> notesList = Note.listAll(Note.class);

        for (final Note note : notesList){


            if(note.isNoteCheckbox()) {
                NoteCheckBoxCard noteCheckBoxCard = new NoteCheckBoxCard(this.getActivity());
                noteCheckBoxCard.setTitle(note.getTitle());
                noteCheckBoxCard.setDescription(note.getDescription());
                noteCheckBoxCard.setId("" + note.getId());
                noteCheckBoxCard.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        UiManagerUtils.openActivityForResultWithData(NotesFragment.this, NotesContentActivity.class, SAVE_NOTE_CONTENT, note);
                    }
                });
                cards.add(noteCheckBoxCard);
            }else{
                NoteDetailCard noteDetailCard = new NoteDetailCard(this.getActivity());
                noteDetailCard.setTitle(note.getTitle());
                noteDetailCard.setContent(note.getDescription());
                noteDetailCard.setCreatedDate(note.getCreateDate());
                noteDetailCard.setId("" + note.getId());
                noteDetailCard.init();
                noteDetailCard.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        UiManagerUtils.openActivityForResultWithData(NotesFragment.this, NotesContentActivity.class, SAVE_NOTE_CONTENT, note);
                    }
                });
                cards.add(noteDetailCard);
            }
        }

        mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        mCardArrayAdapter.setUndoBarUIElements(new UndoBarController.DefaultUndoBarUIElements(){

            @Override
            public SwipeDirectionEnabled isEnabledUndoBarSwipeAction() {
                return SwipeDirectionEnabled.TOPBOTTOM;
            }

            @Override
            public AnimationType getAnimationType() {
                return AnimationType.TOPBOTTOM;
            }
        });

        //Enable undo controller!
        mCardArrayAdapter.setEnableUndo(true);

        if (mListView!=null){
            mListView.setAdapter(mCardArrayAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                UiManagerUtils.openActivity(MainActivity.object, NoteSearchResultsActivity.class);
                return true;
            case R.id.action_settings:
                UiManagerUtils.openActivity(MainActivity.object, SettingsActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

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


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){

        }
    }
}
