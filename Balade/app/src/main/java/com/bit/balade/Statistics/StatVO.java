package com.bit.balade.Statistics;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StatVO implements Parcelable {
    @SerializedName("time")
    private String time;
    @SerializedName("distance")
    private String distance;
    @SerializedName("code")
    private String code;
    @SerializedName("user_no")
    private String user_no;

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StatVO(String time, String distance, String code, String user_no){
        this.time = time;
        this.distance = distance;
        this.code = code;
        this.user_no = user_no;


    }


    protected StatVO(Parcel in){
        time = in.readString();
        distance = in.readString();
        code = in.readString();
        user_no = in.readString();
    }

    public static final Creator<StatVO> CREATOR = new Creator<StatVO>() {
        @Override
        public StatVO createFromParcel(Parcel parcel) {
            return new StatVO(parcel);
        }

        @Override
        public StatVO[] newArray(int i) {
            return new StatVO[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(time);
        parcel.writeString(distance);
        parcel.writeString(user_no);
        parcel.writeString(code);
    }
}



