package com.teddydev.abnd_3_quiz_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teddydev.abnd_3_quiz_app.Model.MultipleAnswer;
import com.teddydev.abnd_3_quiz_app.Model.Questions;
import com.teddydev.abnd_3_quiz_app.Model.SingleAnswer;
import com.teddydev.abnd_3_quiz_app.Util.Util;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ArrayList<Questions> answerQuestionList = new ArrayList<>();
    private ArrayList<Integer> listOfAnswers = new ArrayList<>();
    private Questions question;
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
                if (nameEditText.getText().toString().trim().equalsIgnoreCase("")) {
                    nameEditText.setError("This field can not be blank");
                    Util.showKeyboard(MainActivity.this);
                } else {
                    userName = nameEditText.getText().toString();
                    nameLayout.setVisibility(View.GONE);
                    Util.hideKeyboard(MainActivity.this);
                    submitButton.setVisible(true);
                    nextQuestion();
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
        boolean correctAnswer = false;

        if (question instanceof SingleAnswer) {
            correctAnswer = question.isCorrectAnswer(new ArrayList<>(Arrays.asList(checkedRadioButtonId)));
        } else if (question instanceof MultipleAnswer) {
            correctAnswer = question.isCorrectAnswer(listOfAnswers);
        }
        showResult(correctAnswer);
    }

    private void showResult(boolean correctAnswer) {
        String showResultMessage = "";
        if (toast != null) {
            toast.cancel();
        }
        if (correctAnswer) {
            showResultMessage += getString(R.string.answer_correct);

        } else {
            showResultMessage += getString(R.string.answer_incorrect);
        }
        toast = Toast.makeText(this, showResultMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void nextQuestion(View view) {
        nextQuestion();
    }

    private void nextQuestion() {
        if (answerQuestionList.size() > questionNr) {
            question = answerQuestionList.get(questionNr);

            if (question instanceof SingleAnswer) {
                generateSingleAnswerQuestion(question);
            } else if (question instanceof MultipleAnswer) {
                generateMultipleAnswerQuestion(question);
            }
            questionNr += 1;
        } else {
            showEndScreen();
        }
    }

    private void showEndScreen() {
        setContentView(R.layout.end_layout);
        TextView endScreenText = (TextView) findViewById(R.id.end_screen_textview);
        endScreenText.setText(getString(R.string.thanks_for_playing, userName));
        submitButton.setVisible(false);
    }


    private void generateMultipleAnswerQuestion(Questions question) {
        listOfAnswers.clear();
        if (answerQuestionList != null) {
            setContentView(R.layout.multiple_answer_quiz_layout);
            LinearLayout checkBoxGroupLayout = (LinearLayout) findViewById(R.id.checkboxgroup);
            TextView questionText = (TextView) findViewById(R.id.question_textview);
            questionText.setText(question.getQuestion());

            int ids = 1;
            for (String questions : question.getQuestions()) {
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(questions);
                checkBox.setTag(ids);
                checkBoxGroupLayout.addView(checkBox);
                ids += 1;
                checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            listOfAnswers.add((int) compoundButton.getTag());
                        } else {
                            listOfAnswers.remove(compoundButton.getTag());
                        }
                    }
                });
            }
        }
    }

    private void generateSingleAnswerQuestion(Questions question) {
        if (answerQuestionList != null) {
            setContentView(R.layout.single_answer_quiz_layout);
            radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
            TextView questionText = (TextView) findViewById(R.id.question_textview);
            questionText.setText(question.getQuestion());

            int ids = 1;
            radioGroup.setOrientation(RadioGroup.VERTICAL);
            // create radio buttons
            for (String questions : question.getQuestions()) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(questions);
                radioButton.setTag(ids);
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
    }

    private void createQuestions() {
        answerQuestionList.add(new SingleAnswer(1, "What year Android came out?", new ArrayList<>(Arrays.asList("2015", "2014", "2013"))));
        answerQuestionList.add(new SingleAnswer(2, "What year Java came out?", new ArrayList<>(Arrays.asList("1999", "2014", "2013"))));
        answerQuestionList.add(new SingleAnswer(3, "What year Java came out WUT IS WRONG WITH U????", new ArrayList<>(Arrays.asList("1999", "2014", "2013"))));
        answerQuestionList.add(new MultipleAnswer(new ArrayList<>(Arrays.asList(1, 2)), "TEST QUESTION", new ArrayList<>(Arrays.asList("1999", "2014", "2013"))));

    }
}
