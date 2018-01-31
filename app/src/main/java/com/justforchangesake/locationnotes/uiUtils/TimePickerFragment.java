package com.justforchangesake.locationnotes.uiUtils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.justforchangesake.locationnotes.notes.NoteContentFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
                            implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        int mHour=hourOfDay;
        int mMin=minute;

        String AM_PM ;
        if(hourOfDay < 12) {
            AM_PM = "AM";

        } else {
            AM_PM = "PM";
            if(hourOfDay == 12){
                mHour = hourOfDay;
            }else
                mHour=mHour-12;
        }

        NoteContentFragment.tvMorning.setText(mHour + ":" + mMin + " "+AM_PM);
    }
}