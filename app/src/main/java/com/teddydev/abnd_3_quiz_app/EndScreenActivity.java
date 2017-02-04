package com.teddydev.abnd_3_quiz_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EndScreenActivity extends AppCompatActivity {
    String userName;
    private int totalNumberOfCorrectQuestions;
    private int totalNumberOfQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        userName = getIntent().getStringExtra(Const.EXTRA_USER_NAME);
        totalNumberOfCorrectQuestions = getIntent().getIntExtra(Const.EXTRA_NUMBER_OF_CORRECT_QUESTIONS, 0);
        totalNumberOfQuestions = getIntent().getIntExtra(Const.EXTRA_NUMBER_OF_QUESTIONS_TOTAL, 0);


        TextView endScreenText = (TextView) findViewById(R.id.end_screen_textview);
        endScreenText.setText(getString(R.string.thanks_for_playing, userName, totalNumberOfCorrectQuestions, totalNumberOfQuestions));
    }
}
