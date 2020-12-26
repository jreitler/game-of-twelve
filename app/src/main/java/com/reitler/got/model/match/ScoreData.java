package com.reitler.got.model.match;


import com.reitler.got.model.data.entity.ScoreDataEntity;

public class ScoreData {

    public static final int SIZE = 12;
    public static final int LIMIT = 5;

    private ScoreDataEntity entity;

    public ScoreData(ScoreDataEntity entity) {
        this.entity = entity;
    }

    public void score(int pos) {
        switch (pos) {
            case 1:
                entity.setScore1(entity.getScore1() + 1);
                break;
            case 2:
                entity.setScore2(entity.getScore2() + 1);
                break;
            case 3:
                entity.setScore3(entity.getScore3() + 1);
                break;
            case 4:
                entity.setScore4(entity.getScore4() + 1);
                break;
            case 5:
                entity.setScore5(entity.getScore5() + 1);
                break;
            case 6:
                entity.setScore6(entity.getScore6() + 1);
                break;
            case 7:
                entity.setScore7(entity.getScore7() + 1);
                break;
            case 8:
                entity.setScore8(entity.getScore8() + 1);
                break;
            case 9:
                entity.setScore9(entity.getScore9() + 1);
                break;
            case 10:
                entity.setScore10(entity.getScore10() + 1);
                break;
            case 11:
                entity.setScore11(entity.getScore11() + 1);
                break;
            case 12:
                entity.setScore12(entity.getScore12() + 1);
                break;
            default:
                // error?
                break;
        }
    }

    public void decrease(int pos){
        switch (pos) {
            case 1:
                entity.setScore1(entity.getScore1() - 1);
                break;
            case 2:
                entity.setScore2(entity.getScore2() - 1);
                break;
            case 3:
                entity.setScore3(entity.getScore3() - 1);
                break;
            case 4:
                entity.setScore4(entity.getScore4() - 1);
                break;
            case 5:
                entity.setScore5(entity.getScore5() - 1);
                break;
            case 6:
                entity.setScore6(entity.getScore6() - 1);
                break;
            case 7:
                entity.setScore7(entity.getScore7() - 1);
                break;
            case 8:
                entity.setScore8(entity.getScore8() - 1);
                break;
            case 9:
                entity.setScore9(entity.getScore9() - 1);
                break;
            case 10:
                entity.setScore10(entity.getScore10() - 1);
                break;
            case 11:
                entity.setScore11(entity.getScore11() - 1);
                break;
            case 12:
                entity.setScore12(entity.getScore12() - 1);
                break;
            default:
                // error?
                break;
        }
    }

    public int get(int pos) {
        switch (pos) {
            case 1:
                return entity.getScore1();
            case 2:
                return entity.getScore2();
            case 3:
                return entity.getScore3();
            case 4:
                return entity.getScore4();
            case 5:
                return entity.getScore5();
            case 6:
                return entity.getScore6();
            case 7:
                return entity.getScore7();
            case 8:
                return entity.getScore8();
            case 9:
                return entity.getScore9();
            case 10:
                return entity.getScore10();
            case 11:
                return entity.getScore11();
            case 12:
                return entity.getScore12();
            default:
                return -1;
        }
    }

    public int remainingScore() {
        int result = 0;
        for (int i = 1; i <= SIZE; i++) {
            result = result + i * (LIMIT - get(i));
        }
        return result;
    }

    public ScoreDataEntity getEntity() {
        return this.entity;
    }
}
