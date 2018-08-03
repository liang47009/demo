package com.yunfeng.demo.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * data
 * Created by xll on 2018/8/3.
 */
public class Data implements Parcelable {
    private int anInt;
    private long aLong;
    private boolean aBoolean;
    private float aFloat;
    private double aDouble;
    private String aString;

    public Data() {
        anInt = 1;
        aLong = 2;
        aBoolean = true;
        aFloat = 1.1f;
        aDouble = 1.11d;
        aString = "222";
    }

    protected Data(Parcel in) {
        anInt = in.readInt();
        aLong = in.readLong();
        aBoolean = in.readByte() != 0;
        aFloat = in.readFloat();
        aDouble = in.readDouble();
        aString = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(anInt);
        dest.writeLong(aLong);
        dest.writeByte((byte) (aBoolean ? 1 : 0));
        dest.writeFloat(aFloat);
        dest.writeDouble(aDouble);
        dest.writeString(aString);
    }

    @Override
    public String toString() {
        return "Data{" + "anInt=" + anInt + ", aLong=" + aLong + ", aBoolean=" + aBoolean + ", aFloat=" + aFloat + ", aDouble=" + aDouble + ", aString='" + aString + '\'' + '}';
    }
}
