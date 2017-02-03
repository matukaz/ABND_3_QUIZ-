package com.teddydev.abnd_3_quiz_app.Model;

import java.util.ArrayList;

/**
 * Created by Matu on 03.02.2017.
 */

public class TextAnswer extends Questions {
    private String answer;
    private String question;

    public TextAnswer(String answer, String question) {
        this.answer = answer;
        this.question = question;
    }

    @Override
    public ArrayList<String> getQuestions() {
        return null;
    }

    @Override
    public boolean isCorrectAnswer(ArrayList<Integer> answers) {
        return false;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean isCorrectAnswer(String answer) {
        return answer != null && answer.equalsIgnoreCase(answer);
    }
}
