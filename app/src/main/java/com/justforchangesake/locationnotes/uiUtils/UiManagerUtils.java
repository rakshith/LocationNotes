package com.justforchangesake.locationnotes.uiUtils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.commonsware.cwac.richedit.RichEditText;
import com.justforchangesake.locationnotes.R;
import com.justforchangesake.locationnotes.notes.database.entities.Note;

/**
 * Created by sraksh on 1/25/2015.
 */
public class UiManagerUtils {

    public static void replaceFragment(ActionBarActivity activity, Fragment fragmentName){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentName.setRetainInstance(true);
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragmentName)
                .commit();
    }

    public static void openActivity(Fragment fragment, Class clazz){
        Intent openInent = new Intent(fragment.getActivity(), clazz);
        fragment.startActivity(openInent);
    }

    public static void openActivity(Context context, Class clazz){
        Intent openInent = new Intent(context, clazz);
        context.startActivity(openInent);
    }

    public static void openActivityForResult(Fragment fragment, Class clazz, int REQUES_CODE){
        Intent openInent = new Intent(fragment.getActivity(), clazz);
        fragment.startActivityForResult(openInent, REQUES_CODE);
    }

    public static void openActivityForResultWithData(Fragment fragment, Class clazz, int REQUES_CODE, Note note){
        Intent openIntent = new Intent(fragment.getActivity(), clazz);
        openIntent.putExtra("noteData", note);
        fragment.startActivityForResult(openIntent, REQUES_CODE);
    }

    public static void showToast(Context context, String message, int duration){
        Toast.makeText(context, message, duration).show();
    }

    public static String extractTextFromRichEditText(RichEditText editText){
        return editText.getText().toString();
    }

    public static String extractTextFromEditText(EditText editText){
        return editText.getText().toString();
    }
}
