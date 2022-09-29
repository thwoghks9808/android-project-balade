package com.bit.balade.RankingActivity;

public class RankData {
    private String user_id, distance, time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public RankData(String user_id, String distance, String time){
        this.user_id = user_id;
        this.distance = distance;
        this.time = time;
    }
}
