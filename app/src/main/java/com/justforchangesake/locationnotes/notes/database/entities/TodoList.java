package com.justforchangesake.locationnotes.notes.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by sraksh on 2/4/2015.
 */
public class TodoList extends SugarRecord<TodoList> implements Parcelable {

    private String content;
    private boolean strikeThrough;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStrikeThrough() {
        return strikeThrough;
    }

    public void setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeValue(this.strikeThrough);
    }

    public TodoList(Parcel in){
        this.content = in.readString();
        this.strikeThrough = (Boolean)in.readValue(ClassLoader.getSystemClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TodoList createFromParcel(Parcel in)
        {
            return new TodoList(in);
        }

        public TodoList[] newArray(int size)
        {
            return new TodoList[size];
        }
    };
}
