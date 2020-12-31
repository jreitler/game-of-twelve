package com.reitler.got.model;

import com.reitler.got.model.data.entity.ScoreDataEntity;
import com.reitler.got.model.match.ScoreData;

public class ScoreDataUtil {

    private ScoreDataUtil(){
        //
    }

    public static int getRemainingScore(ScoreDataEntity entity){
        int result = Constants.ABSOLUTE_SCORE;
        result -= entity.getScore1();
        result -= entity.getScore2() * 2;
        result -= entity.getScore3() * 3;
        result -= entity.getScore4() * 4;
        result -= entity.getScore5() * 5;
        result -= entity.getScore6() * 6;
        result -= entity.getScore7() * 7;
        result -= entity.getScore8() * 8;
        result -= entity.getScore9() * 9;
        result -= entity.getScore10() * 10;
        result -= entity.getScore11() * 11;
        result -= entity.getScore12() * 12;

        return result;
    }

    public static int getRemainingScore(ScoreData data) {
        return getRemainingScore(data.getEntity());
    }

    public static boolean isCompleted(ScoreData scoreData, int number){
        return scoreData.get(number) == Constants.TARGET;
    }
}
