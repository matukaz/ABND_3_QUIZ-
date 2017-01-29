package com.teddydev.abnd_3_quiz_app.Model;

import java.util.ArrayList;

/**
 * Created by Matu on 29.01.2017.
 */
public class MultipleAnswer implements Questions {

    private ArrayList<Integer> correctAnswersList;
    private String question;
    private ArrayList<String> questions;

    public MultipleAnswer(ArrayList<Integer> correctAnswersList, String question, ArrayList<String> questions) {
        this.correctAnswersList = correctAnswersList;
        this.question = question;
        this.questions = questions;
    }

    public ArrayList<Integer> getCorrectAnswersList() {
        return correctAnswersList;
    }

    public void setCorrectAnswersList(ArrayList<Integer> correctAnswersList) {
        this.correctAnswersList = correctAnswersList;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "MultipleAnswer{" +
                "correctAnswersList=" + correctAnswersList +
                ", question='" + question + '\'' +
                ", questions=" + questions +
                '}';
    }

    @Override
    public boolean isCorrectAnswer(ArrayList<Integer> answers) {
        int numberOfCorrectAnswers = 0;

        if (answers.size() != correctAnswersList.size()) {
            return false;
        }

        for (int answer : answers) {
            for (int correctAnswer : correctAnswersList) {
                if (answer == correctAnswer) {
                    numberOfCorrectAnswers += 1;
                }
            }
        }
        return numberOfCorrectAnswers == correctAnswersList.size();
    }
}
