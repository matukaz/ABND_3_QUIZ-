package com.teddydev.abnd_3_quiz_app.Model;

import java.util.ArrayList;

/**
 * Created by Matu on 29.01.2017.
 */

public abstract class Questions {
    public abstract ArrayList<String> getQuestions();

    public abstract String getQuestion();

    public abstract boolean isCorrectAnswer(ArrayList<Integer> answers);

    public abstract boolean isCorrectAnswer(String answer);
}
