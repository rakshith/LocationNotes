package com.justforchangesake.locationnotes.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sraksh on 1/31/2015.
 */
public class NoteManipulator {

    public static String buildNoteString(List<String> noteList){
        String noteString = null;

            StringBuilder noteStringBuilder = new StringBuilder();
            for(String note:noteList){
                noteStringBuilder.append(note);
                noteStringBuilder.append("\n");
            }
        noteString = noteStringBuilder.toString();
        return noteString;
    }

    public static List<String> buildNoteListFromString(String noteDescription){
        List<String> notes = new ArrayList<String>();

        String[] noteDetailArray;
        if(noteDescription != null && !noteDescription.isEmpty()) {
            noteDetailArray = noteDescription.split("\n");
            Collections.addAll(notes, noteDetailArray);
        }
        return notes;
    }
}
