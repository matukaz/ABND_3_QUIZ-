package com.teddydev.abnd_3_quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teddydev.abnd_3_quiz_app.Util.Util;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout nameLayout;
    private EditText nameEditText;
    private String userName;
    private EditText.OnEditorActionListener onNameEditorActionListener = new EditText.OnEditorActionListener() {

        /**
         * Set userName after pressing done on keyboard
         * Set Visibility of nameLayout to gone.
         */
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (nameEditText.getText().toString().trim().equalsIgnoreCase("")) {
                    nameEditText.setError("This field can not be blank");
                    Util.showKeyboard(MainActivity.this);
                } else {
                    userName = nameEditText.getText().toString();
                    nameLayout.setVisibility(View.GONE);
                    Util.hideKeyboard(MainActivity.this);
                    startQuiz();
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Name layouts
        nameLayout = (RelativeLayout) findViewById(R.id.name_layout);
        nameEditText = (EditText) findViewById(R.id.name_edittext);
        nameEditText.setOnEditorActionListener(onNameEditorActionListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startQuiz() {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra(Const.EXTRA_USER_NAME, userName);
        startActivity(intent);
    }
}
