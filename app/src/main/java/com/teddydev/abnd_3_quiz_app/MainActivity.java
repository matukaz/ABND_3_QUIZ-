package com.teddydev.abnd_3_quiz_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    boolean rightAnswer = false;
    ArrayList<SingleAnswer> questions = new ArrayList<>();
    private RelativeLayout nameLayout;
    private EditText nameEditText;
    private String userName;
    private MenuItem submitButton;
    private int checkedRadioButtonId;
    // First question
    private RadioGroup radioGroup;
    private int questionNr = 0;
    private Toast toast = null;
    private EditText.OnEditorActionListener onNameEditorActionListener = new EditText.OnEditorActionListener() {


        /**
         * Set userName after pressing done on keyboard
         * Set Visibility of nameLayout to gone.
         */
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                userName = nameEditText.getText().toString();
                nameLayout.setVisibility(View.GONE);
                Util.hideKeyboard(MainActivity.this);
                submitButton.setVisible(true);
                nextQuestion();
                return true;
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
        createQuestions();


        if (savedInstanceState != null) {
            //TODO so that layout won't reset from portrait to landscape
            /**   int nameLayoutVisiblility = savedInstanceState.getInt("name_layout", View.VISIBLE);
             @SuppressWarnings("ResourceType") nameLayout.setVisibility(nameLayoutVisiblility); */
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("name_layout", nameLayout.getVisibility());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                //TODO submit method
                gradeAnswer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        submitButton = menu.findItem(R.id.action_submit);
        return true;
    }

    private void gradeAnswer() {
        int correctAnswer = questions.get(questionNr - 1).getCorrectAnswer();

        if (correctAnswer == checkedRadioButtonId) {
            rightAnswer = true;
        } else {
            rightAnswer = false;
        }
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, "" + rightAnswer, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void nextQuestion(View view) {
        nextQuestion();
    }

    private void nextQuestion() {
        generateSingleAnswerQuestion(questionNr);
        questionNr += 1;
    }

    private void generateSingleAnswerQuestion(int whichQuestionFromArray) {
        setContentView(R.layout.single_answer_quiz_layout);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        TextView questionText = (TextView) findViewById(R.id.question_textview);
        questionText.setText(questions.get(whichQuestionFromArray).getQuestion());

        int ids = 1;
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        // create radio buttons
        for (String question : questions.get(whichQuestionFromArray).getQuestions()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(question);
            radioButton.setTag(ids);
            radioButton.setId(ids);
            radioGroup.addView(radioButton);
            ids += 1;
        }

        // This overrides the radiogroup onCheckListener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // If the radiobutton that has changed in check state is now checked...
                if (checkedRadioButton.isChecked()) {
                    checkedRadioButtonId = (int) checkedRadioButton.getTag();
                }
            }
        });
    }

    private void createQuestions() {
        questions.add(new SingleAnswer(1, "What year Android came out?", new ArrayList<>(Arrays.asList("2015", "2014", "2013"))));
        questions.add(new SingleAnswer(2, "What year Java came out?", new ArrayList<>(Arrays.asList("1999", "2014", "2013"))));
        questions.add(new SingleAnswer(3, "What year Java came out?", new ArrayList<>(Arrays.asList("1999", "2014", "2013"))));
    }

}
