package com.art.fmblzf.aidl.module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/1/18.
 */
public class User implements Parcelable {

    public int userId;
    public String userName ;
    public int sex;

    public User(){}

    public User(int userId, String userName, int sex) {
        this.userId = userId;
        this.userName = userName;
        this.sex = sex;
    }

    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        sex = in.readInt();
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
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeInt(sex);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                '}';
    }
}
