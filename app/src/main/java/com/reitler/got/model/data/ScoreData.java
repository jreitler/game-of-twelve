package com.reitler.got.model.data;


public class ScoreData {

    public static final int SIZE = 12;
    public static final int LIMIT = 5;

    private int[] scores = new int[SIZE];

    public void score(int pos) {
        if (pos > (SIZE) || pos < 0) {
            return;
        }
        scores[pos - 1] = scores[pos - 1] + 1;
    }

    public int get(int pos) {
        if (pos > (SIZE) || pos < 0) {
            return -1;
        }
        return scores[pos - 1];
    }

    public int remainingScore(){
        int result = 0;
        for(int i = 0; i < SIZE; i++){
            result = result + (5 - scores[i]);
        }
        return result;
    }

}
