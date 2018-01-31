package com.justforchangesake.locationnotes.notes.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.sql.Time;

/**
 * Created by sraksh on 2/7/2015.
 */
public class TimeReminder extends SugarRecord<TimeReminder> implements Parcelable{
    private Time morning;
    private Time afternoon;
    private Time evening;
    private Time night;

    public TimeReminder(){

    }

    public Time getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(Time afternoon) {
        this.afternoon = afternoon;
    }

    public Time getMorning() {
        return morning;
    }

    public void setMorning(Time morning) {
        this.morning = morning;
    }

    public Time getEvening() {
        return evening;
    }

    public void setEvening(Time evening) {
        this.evening = evening;
    }

    public Time getNight() {
        return night;
    }

    public void setNight(Time night) {
        this.night = night;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.morning);
        dest.writeValue(this.afternoon);
        dest.writeValue(this.evening);
        dest.writeValue(this.night);
        if(getId() != null)
            dest.writeLong(getId());
    }

    public TimeReminder(Parcel in){
        this.morning = (Time)in.readValue(Time.class.getClassLoader());
        this.afternoon = (Time)in.readValue(Time.class.getClassLoader());
        this.evening = (Time)in.readValue(Time.class.getClassLoader());
        this.night = (Time)in.readValue(Time.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TimeReminder createFromParcel(Parcel in)
        {
            return new TimeReminder(in);
        }

        public TimeReminder[] newArray(int size)
        {
            return new TimeReminder[size];
        }
    };

}
