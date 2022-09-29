package com.bit.balade.route;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RouteData implements Parcelable {
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("tag")
    private String tag;
    @SerializedName("title")
    private String title;
    @SerializedName("time")
    private String time;
    @SerializedName("no")
    private String no;
    @SerializedName("content")
    private String content;

    public RouteData(String user_id, String tag,  String title, String time, String no,
                     String content) {
        this.user_id = user_id;
        this.tag = tag;
        this.title = title;
        this.time = time;
        this.no = no;
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    protected RouteData(Parcel in){
        user_id = in.readString();
        tag = in.readString();
        title = in.readString();
        time = in.readString();
        no = in.readString();
        content = in.readString();
    }

    public static final Creator<RouteData> CREATOR = new Creator<RouteData>() {
        @Override
        public RouteData createFromParcel(Parcel in) {
            return new RouteData(in);
        }

        @Override
        public RouteData[] newArray(int size) {
            return new RouteData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_id);
        parcel.writeString(tag);
        parcel.writeString(title);
        parcel.writeString(time);
        parcel.writeString(no);
        parcel.writeString(content);
    }

}
