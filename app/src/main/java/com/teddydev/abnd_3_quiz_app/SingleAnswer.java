package com.teddydev.abnd_3_quiz_app;

import java.util.ArrayList;

public class SingleAnswer {

    private int correctAnswer;
    private String question;
    private ArrayList<String> questions;

    public SingleAnswer(int correctAnswer, String question, ArrayList<String> questions) {
        this.correctAnswer = correctAnswer;
        this.question = question;
        this.questions = questions;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SingleAnswer{" +
                ", correctAnswer=" + correctAnswer +
                ", questions=" + questions +
                '}';
    }
}
