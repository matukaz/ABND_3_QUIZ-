package com.teddydev.abnd_3_quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teddydev.abnd_3_quiz_app.Model.MultipleAnswer;
import com.teddydev.abnd_3_quiz_app.Model.Questions;
import com.teddydev.abnd_3_quiz_app.Model.SingleAnswer;
import com.teddydev.abnd_3_quiz_app.Model.TextAnswer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matu on 30.01.2017.
 */
public class QuizActivity extends AppCompatActivity  {

    ArrayList<Questions> answerQuestionList = new ArrayList<>();
    private ArrayList<Integer> listOfAnswers = new ArrayList<>();
    private Questions question;
    private int checkedRadioButtonId;
    // First question
    private RadioGroup radioGroup;
    private int questionNr = 0;
    private Toast toast = null;
    private String userName;

    private int totalNumberOfCorrectQuestions = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra(Const.EXTRA_USER_NAME);
        createQuestions();
        nextQuestion();
    }


    private void showEndScreen() {
        Intent intent = new Intent(QuizActivity.this, EndScreenActivity.class);
        intent.putExtra(Const.EXTRA_USER_NAME, userName);
        intent.putExtra(Const.EXTRA_NUMBER_OF_CORRECT_QUESTIONS, totalNumberOfCorrectQuestions);
        intent.putExtra(Const.EXTRA_NUMBER_OF_QUESTIONS_TOTAL, questionNr);
        startActivity(intent);
    }

    public void gradeAnswer(View v) {
        showResult(isAnswerCorrect());
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
        if (isAnswerCorrect()) {
            totalNumberOfCorrectQuestions += 1;
        }
        nextQuestion();
    }

    private void nextQuestion() {
        if (answerQuestionList.size() > questionNr) {
            question = answerQuestionList.get(questionNr);

            // Could use polymorphism to generate() question, where each class knows how to generate itself.
            // Example: http://stackoverflow.com/questions/5579309/switch-instanceof
            if (question instanceof SingleAnswer) {
                generateSingleAnswerQuestion(question);
            } else if (question instanceof MultipleAnswer) {
                generateMultipleAnswerQuestion(question);
            } else if (question instanceof TextAnswer) {
                generateTextAnswerQuestion(question);
            }
            questionNr += 1;
        } else {
            showEndScreen();
        }
    }

    private void generateTextAnswerQuestion(Questions question) {
        setContentView(R.layout.text_answer_quiz_layout);

        TextView questionText = (TextView) findViewById(R.id.question_textview);
        questionText.setText(question.getQuestion());
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
        answerQuestionList.add(new SingleAnswer(4, "What year Android came out to public?", new ArrayList<>(Arrays.asList("2014", "2009", "2008","2007"))));
        answerQuestionList.add(new SingleAnswer(2, "What year Java was released?", new ArrayList<>(Arrays.asList("1999", "1995", "1991"))));
        answerQuestionList.add(new SingleAnswer(1, "When did c++ first appear", new ArrayList<>(Arrays.asList("1983", "1985", "1989"))));
        answerQuestionList.add(new MultipleAnswer(new ArrayList<>(Arrays.asList(2, 3)), "Which of these languages are FUNCTION languages", new ArrayList<>(Arrays.asList("Java", "Erlang", "F#"))));
        answerQuestionList.add(new TextAnswer("Nougat", "What was Android 7.1 version called? (Code name)"));
    }

    public boolean isAnswerCorrect() {
        boolean correctAnswer = false;

        if (question instanceof SingleAnswer) {
            correctAnswer = question.isCorrectAnswer(new ArrayList<>(Arrays.asList(checkedRadioButtonId)));
        } else if (question instanceof MultipleAnswer) {
            correctAnswer = question.isCorrectAnswer(listOfAnswers);
        } else if (question instanceof TextAnswer) {
            EditText personAnswer = (EditText) findViewById(R.id.answer_edittext);
            correctAnswer = question.isCorrectAnswer(personAnswer.getText().toString().trim());
        }
        return correctAnswer;
    }
}
