package com.reitler.got.model.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "t_match")
public class MatchEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "match_id")
    private long matchId;

    @ColumnInfo(name = "end_date")
    private Date endDate;

    @ColumnInfo(name = "start_date")
    private Date startDate;

    public void setMatchId(long matchId){
        this.matchId = matchId;
    }

    public long getMatchId(){
        return matchId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
