package com.reitler.got.model.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "t_score_data",
        foreignKeys = {
                @ForeignKey(entity = PlayerEntity.class, parentColumns = "player_id", childColumns = "fk_player_id"),
                @ForeignKey(entity = MatchEntity.class, parentColumns = "match_id", childColumns = "fk_match_id")
        })
public class ScoreDataEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "score_id")
    private long id;
    @ColumnInfo(name = "fk_player_id", index = true)
    private long playerId;
    @ColumnInfo(name = "fk_match_id", index = true)
    private long matchId;
    @ColumnInfo(name = "player_order")
    private int order;

    private int score1 = 0;
    private int score2 = 0;
    private int score3 = 0;
    private int score4 = 0;
    private int score5 = 0;
    private int score6 = 0;
    private int score7 = 0;
    private int score8 = 0;
    private int score9 = 0;
    private int score10 = 0;
    private int score11 = 0;
    private int score12 = 0;

    public long getId(){
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPlayerId(long playerId){
        this.playerId = playerId;
    }

    public long getPlayerId(){
        return this.playerId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public int getScore3() {
        return score3;
    }

    public void setScore3(int score3) {
        this.score3 = score3;
    }

    public int getScore4() {
        return score4;
    }

    public void setScore4(int score4) {
        this.score4 = score4;
    }

    public int getScore5() {
        return score5;
    }

    public void setScore5(int score5) {
        this.score5 = score5;
    }

    public int getScore6() {
        return score6;
    }

    public void setScore6(int score6) {
        this.score6 = score6;
    }

    public int getScore7() {
        return score7;
    }

    public void setScore7(int score7) {
        this.score7 = score7;
    }

    public int getScore8() {
        return score8;
    }

    public void setScore8(int score8) {
        this.score8 = score8;
    }

    public int getScore9() {
        return score9;
    }

    public void setScore9(int score9) {
        this.score9 = score9;
    }

    public int getScore10() {
        return score10;
    }

    public void setScore10(int score10) {
        this.score10 = score10;
    }

    public int getScore11() {
        return score11;
    }

    public void setScore11(int score11) {
        this.score11 = score11;
    }

    public int getScore12() {
        return score12;
    }

    public void setScore12(int score12) {
        this.score12 = score12;
    }
}
