package com.bit.balade.GpsActivity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GpsVo implements Parcelable {
    @SerializedName("point_lng")
    private String point_lng;
    @SerializedName("point_lat")
    private String point_lat;
    @SerializedName("user_no")
    private String user_no;
    @SerializedName("distance")
    private String distance;


    public GpsVo(String point_lng, String point_lat, String user_no, String distance){
        this.point_lng = point_lng;
        this.point_lat = point_lat;
        this.user_no = user_no;
        this.distance = distance;
    }

    public String getPoint_lng() {
        return point_lng;
    }

    public void setPoint_lng(String point_lng) {
        this.point_lng = point_lng;
    }

    public String getPoint_lat() {
        return point_lat;
    }

    public void setPoint_lat(String point_lat) {
        this.point_lat = point_lat;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    protected  GpsVo(Parcel in){
        point_lng = in.readString();
        point_lat = in.readString();
        user_no = in.readString();
        distance = in.readString();

    }

    public static final Creator<GpsVo> CREATOR = new Creator<GpsVo>() {
        @Override
        public GpsVo createFromParcel(Parcel parcel) {
            return new GpsVo(parcel);
        }

        @Override
        public GpsVo[] newArray(int i) {
            return new GpsVo[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(point_lng);
        parcel.writeString(point_lat);
        parcel.writeString(user_no);
        parcel.writeString(distance);
    }
}



