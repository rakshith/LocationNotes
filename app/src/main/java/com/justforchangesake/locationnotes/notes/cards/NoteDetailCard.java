package com.justforchangesake.locationnotes.notes.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.justforchangesake.locationnotes.R;
import com.justforchangesake.locationnotes.utils.DateManupulateUtils;

import it.gmariotti.cardslib.library.internal.Card;

/**
     * This class provides a simple card as Google Play
     *
     * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
     */
    public class NoteDetailCard extends Card {

        protected TextView mTitle;
        protected TextView mContent;
        protected TextView mCreatedDate;

        protected String title;
        protected String content;
        protected String createdDate;

        public NoteDetailCard(Context context) {
            this(context, R.layout.card_note_detail);
        }

        public NoteDetailCard(Context context, int innerLayout) {
            super(context, innerLayout);
            //init();
        }

        public void init() {

            setSwipeable(true);

            setOnSwipeListener(new OnSwipeListener() {
                    @Override
                    public void onSwipe(Card card) {
                        Toast.makeText(getContext(), "Removed card=" + title, Toast.LENGTH_SHORT).show();
                    }
            });


            setOnUndoSwipeListListener(new OnUndoSwipeListListener() {
                @Override
                public void onUndoSwipe(Card card) {
                    Toast.makeText(getContext(), "Undo card=" + title, Toast.LENGTH_SHORT).show();
                }
            });

            setOnUndoHideSwipeListListener(new OnUndoHideSwipeListListener() {
                @Override
                public void onUndoHideSwipe(Card card) {
                    Toast.makeText(getContext(), "Hide undo card=" + title, Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            //Retrieve elements
            mTitle = (TextView) parent.findViewById(R.id.card_note_detail_title);
            mContent = (TextView) parent.findViewById(R.id.card_note_detail_detail);
            mCreatedDate = (TextView) parent.findViewById(R.id.card_note_created_date_detail);

            if (mTitle != null)
                mTitle.setText(title);

            if (mContent != null)
                mContent.setText(content);

            if(mCreatedDate != null){
                String[] date = DateManupulateUtils.getDayMonthArray(createdDate);
                mCreatedDate.setText(date[1]+", "+date[0]);
            }
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
        return content;
    }

        public void setContent(String content) {
        this.content = content;
    }

        public String getCreatedDate() {
        return createdDate;
    }

        public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}