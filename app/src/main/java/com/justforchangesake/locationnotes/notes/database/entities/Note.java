package com.justforchangesake.locationnotes.notes.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by sraksh on 1/26/2015.
 */
public class Note extends SugarRecord<Note> implements Parcelable{

    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    private String createDate;
    private String editDate;
    //private byte[] image;
    private LocationReminder tag;
    private TimeReminder timeReminder;
    private boolean isNoteCheckbox;

    public Note(){
        this.timeReminder = new TimeReminder();
        this.tag = new LocationReminder();
    }

    public Note(String title, String description){
        this.title = title;
        this.description = description;
    }

    public TimeReminder getTimeReminder() {
        return timeReminder;
    }

    public void setTimeReminder(TimeReminder timeReminder) {
        this.timeReminder = timeReminder;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

/*
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
*/

    public String getCreateDate() {
        return createDate;
    }

    public boolean isNoteCheckbox() {
        return isNoteCheckbox;
    }

    public void setNoteCheckbox(boolean isNoteCheckbox) {
        this.isNoteCheckbox = isNoteCheckbox;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public LocationReminder getTag() {
        if(this.tag == null){
            return new LocationReminder();
        }
        return tag;
    }

    public void setTag(LocationReminder tag) {
        this.tag = tag;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeValue(this.isNoteCheckbox);
        dest.writeString(this.createDate);
        dest.writeValue(this.tag);
        dest.writeString(this.editDate);
        dest.writeValue(this.timeReminder);
        //dest.writeByteArray(this.image);
        if(getId() != null)
        dest.writeLong(getId());

    }

    public Note(Parcel in){
        this.title = in.readString();
        this.description = in.readString();
        this.isNoteCheckbox = (Boolean)in.readValue(Boolean.class.getClassLoader());
        this.createDate = in.readString();
        this.tag = (LocationReminder)in.readValue(LocationReminder.class.getClassLoader());
        this.editDate = in.readString();
        this.timeReminder = (TimeReminder)in.readValue(TimeReminder.class.getClassLoader());
        /*if(this.image != null)
            in.readByteArray(this.image);*/
        this.setId(in.readLong());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Note createFromParcel(Parcel in)
        {
            return new Note(in);
        }

        public Note[] newArray(int size)
        {
            return new Note[size];
        }
    };

}
