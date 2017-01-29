package com.teddydev.abnd_3_quiz_app.Model;

import java.util.ArrayList;

/**
 * Created by Matu on 29.01.2017.
 */

public interface Questions {
    ArrayList<String> getQuestions();

    String getQuestion();

    boolean isCorrectAnswer(ArrayList<Integer> answers);
}
