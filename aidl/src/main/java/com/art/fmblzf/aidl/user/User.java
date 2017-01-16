package com.art.fmblzf.aidl.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/1/12.
 */
public class User implements Parcelable {

    public int age;
    public String name;

    private User(Parcel in) {
        age = in.readInt();
        name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
    }
}
