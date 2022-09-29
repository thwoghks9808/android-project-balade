package com.bit.balade.RegisterActivity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class JoinVO implements Parcelable {
    @SerializedName("user_no")
    private String user_no;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_pwd")
    private String user_pwd;
    @SerializedName("user_email")
    private String user_email;

    public JoinVO(String user_id, String user_pwd, String user_email){
        this.user_id = user_id;
        this.user_pwd = user_pwd;
        this.user_email = user_email;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    protected JoinVO(Parcel in){
        user_no = in.readString();
        user_id = in.readString();
        user_pwd = in.readString();
        user_email = in.readString();
    }

    public static final Creator<JoinVO> CREATOR = new Creator<JoinVO>() {
        @Override
        public JoinVO createFromParcel(Parcel parcel) {
            return new JoinVO(parcel);
        }

        @Override
        public JoinVO[] newArray(int i) {
            return new JoinVO[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_no);
        parcel.writeString(user_id);
        parcel.writeString(user_pwd);
        parcel.writeString(user_email);
    }
}
