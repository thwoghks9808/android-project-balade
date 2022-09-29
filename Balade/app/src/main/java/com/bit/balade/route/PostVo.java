package com.bit.balade.route;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostVo implements Parcelable {
    @SerializedName("title")
    private String title;
    @SerializedName("tag")
    private String tag;
    @SerializedName("content")
    private String content;
    @SerializedName("user_no")
    private String user_no;
    @SerializedName("time")
    private String time;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("no")
    private String no;
    @SerializedName("boardlist")
    private List<RouteData> boardlist;
    @SerializedName("reply")
    private List<BoardData> reply;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<RouteData> getBoardlist() {
        return boardlist;
    }

    public void setBoardlist(List<RouteData> boardlist) {
        this.boardlist = boardlist;
    }

    public List<BoardData> getReply() {
        return reply;
    }

    public void setReply(List<BoardData> reply) {
        this.reply = reply;
    }

    public PostVo(String tag, String title, String content, String user_no, String time, String user_id,
                  String no, List<RouteData> boardlist, List<BoardData> reply){
        this.tag = tag;
        this.title = title;
        this.content = content;
        this.user_no = user_no;
        this.time = time;
        this.user_id = user_id;
        this.no = no;
        this.boardlist = boardlist;
        this.reply = reply;
    }

    protected PostVo(Parcel in) {
        tag = in.readString();
        title = in.readString();
        content = in.readString();
        user_no = in.readString();
        time = in.readString();
        user_id = in.readString();
        no = in.readString();
    }

    public static final Creator<PostVo> CREATOR = new Creator<PostVo>() {
        @Override
        public PostVo createFromParcel(Parcel in) {
            return new PostVo(in);
        }

        @Override
        public PostVo[] newArray(int size) {
            return new PostVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tag);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(user_no);
        parcel.writeString(time);
        parcel.writeString(user_id);
        parcel.writeString(no);
    }
}
