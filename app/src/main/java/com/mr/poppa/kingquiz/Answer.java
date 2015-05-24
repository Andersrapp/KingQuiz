package com.mr.poppa.kingquiz;

/**
 * Created by Benjamin and Anders on 4/16/2015.
 */
public class Answer {
    private boolean correct;
    private int numberOfGuesses;

    public Answer(boolean correct, int numberOfGuesses) {
        this.correct = correct;
        this.numberOfGuesses = numberOfGuesses;
    }

    public Answer() {}

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public void setNumberOfGuesses(int numberOfGuesses) {
        this.numberOfGuesses = numberOfGuesses;
    }
    public void guess() {
        numberOfGuesses++;
    }
}