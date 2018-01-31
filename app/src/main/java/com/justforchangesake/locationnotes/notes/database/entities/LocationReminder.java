package com.justforchangesake.locationnotes.notes.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by sraksh on 1/31/2015.
 */
public class LocationReminder extends SugarRecord<LocationReminder> implements Parcelable{

    private String latitude;
    private String longitude;

    public LocationReminder(){
    }

    public LocationReminder(String latitude, String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    public LocationReminder(Parcel in){
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public LocationReminder createFromParcel(Parcel in)
        {
            return new LocationReminder(in);
        }

        public LocationReminder[] newArray(int size)
        {
            return new LocationReminder[size];
        }
    };
}
