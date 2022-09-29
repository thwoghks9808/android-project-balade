package com.bit.balade.RankingActivity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RankVO{
    @SerializedName("ranking")
    private List<RankData> ranking;

    public List<RankData> getRanking() {
        return ranking;
    }

    public void setRanking(List<RankData> ranking) {
        this.ranking = ranking;
    }

    public RankVO(List<RankData> ranking) {
        this.ranking = ranking;
    }
}
