package com.example.gkdaily.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gkdaily.R;
import com.example.gkdaily.questionDB.DBAccess;
import com.example.gkdaily.questionDB.QuestionList;

import java.util.ArrayList;

public class QuizQuestionActivity extends AppCompatActivity {

    public int position_clicked;
    public String typeName_clicked;
    public int questionNumber = 0;
    public int maxQuestionAttempt = 0;
    Button mButtonPrev;
    Button mButtonSubmit;
    Button mButtonNext;
    FragmentManager fragmentManager;
    public QuizQuestionFragment quizQuestionFragment;
    public ArrayList<QuestionList> questionLists_Array = new ArrayList<>();
    public String answer;
    public String answer_chosen;

    public static String QUESTION_KEY = "question";
    public static String QUESTION_NUMBER = "question_number";
    public static String CURRENT_SCORE = "current_score";
    public static String FINAL_SCORE = "final_score";

    //SavedInstance

    public static String MAXQUESTIONATTEMPT = "maxQuestionAttempt";
    public static String QUESTIONNUMBER = "questionNumber";
    public static String CURRENTSCORE = "current_score";
    public static String ANSWER;
    public static String ANSWER_CHOSEN;


    public int currentScore = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        if (savedInstanceState != null) {

            quizQuestionFragment = (QuizQuestionFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myFragment");
            maxQuestionAttempt = savedInstanceState.getInt(MAXQUESTIONATTEMPT);
            questionNumber = savedInstanceState.getInt(QUESTIONNUMBER);
            currentScore = savedInstanceState.getInt(CURRENTSCORE);
            questionLists_Array = savedInstanceState.getParcelableArrayList("arrayList");

        }

        mButtonPrev = findViewById(R.id.previous);
        mButtonSubmit = findViewById(R.id.submit);
        mButtonNext = findViewById(R.id.next);
        mButtonPrev.setEnabled(false);
        mButtonNext.setEnabled(false);

        position_clicked = (getIntent().getIntExtra(MainActivity.QUESTION_TYPE_POSITION, 0) + 1);
        typeName_clicked = getIntent().getStringExtra(MainActivity.QUESTION_TYPE_CLICKED);

        getSupportActionBar().setTitle(typeName_clicked);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //QuizQuestionFragment quizQuestionFragment = new QuizQuestionFragment();
        // quizQuestionFragment.getPositionClicked(position_clicked);
        fragmentManager = getSupportFragmentManager();

        //fragmentManager.beginTransaction().add(R.id.quiz_ques_ans_container, quizQuestionFragment).commit();
        if (savedInstanceState == null) {

            new FetchQuestion().execute();
        }

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (questionLists_Array.size() == 0 || questionNumber == questionLists_Array.size()) {
                    return;
                } else {
                    questionNumber++;


                    if ((maxQuestionAttempt == questionNumber) && (questionNumber < 10)) {
                        mButtonNext.setEnabled(false);
                        mButtonPrev.setEnabled(false);
                        mButtonSubmit.setEnabled(true);
                    } else {
                        mButtonNext.setEnabled(true);
                        mButtonPrev.setEnabled(true);
                        mButtonSubmit.setEnabled(false);
                    }
                    if (questionNumber < 10) {
                        showNextQuestionFragment(questionLists_Array.get(questionNumber));
                    } else {
                        Intent finalScoreIntent = new Intent(QuizQuestionActivity.this, FinalScoreActivity.class);
                        finalScoreIntent.putExtra(FINAL_SCORE, currentScore);
                        startActivity(finalScoreIntent);
                    }
                }
            }
        });

        mButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonNext.setEnabled(true);
                mButtonPrev.setEnabled(true);
                mButtonSubmit.setEnabled(false);
                if (questionLists_Array.size() == 0 || questionNumber == 0) {
                    return;
                } else {
                    questionNumber--;
                    showNextQuestionFragment(questionLists_Array.get(questionNumber));

                }
            }
        });

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                answer = questionLists_Array.get(questionNumber).getCorrectAnswer();

                answer_chosen = QuizQuestionFragment.getAnswer();
                    if (answer_chosen == null) {
                        Toast.makeText(v.getContext(), "Please Select the Answer", Toast.LENGTH_SHORT).show();
                    } else {

                        maxQuestionAttempt++;
                        mButtonSubmit.setEnabled(false);
                        mButtonNext.setEnabled(true);
                        mButtonPrev.setEnabled(true);

                        if (answer.equals(answer_chosen)) {
                            Toast.makeText(v.getContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
                            currentScore++;

                            quizQuestionFragment.isAnswerRight(true, answer, currentScore);

                        } else {
                            Toast.makeText(v.getContext(), "Wrong Answer", Toast.LENGTH_SHORT).show();

                            quizQuestionFragment.isAnswerRight(false, answer, currentScore);
                        }
                    }


            }
        });


    }


    public void showNextQuestionFragment(QuestionList question) {

        quizQuestionFragment = new QuizQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION_KEY, question);
        bundle.putInt(QUESTION_NUMBER, questionNumber + 1);
        bundle.putInt(CURRENT_SCORE, currentScore);

        quizQuestionFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.quiz_ques_ans_container, quizQuestionFragment).commit();
        Toast.makeText(getApplicationContext(), "QuestionNumber" + maxQuestionAttempt, Toast.LENGTH_LONG).show();


    }

    public class FetchQuestion extends AsyncTask<String, Void, ArrayList<QuestionList>> {

        public ArrayList<QuestionList> questionLists_array;


        @Override
        protected ArrayList<QuestionList> doInBackground(String... strings) {

            ArrayList<QuestionList> questionLists = new ArrayList<>();
            DBAccess dbAccessQuestion = DBAccess.getInstance(getApplicationContext());
            dbAccessQuestion.openDB();
            questionLists = dbAccessQuestion.getAllQuestion(position_clicked);

            return questionLists;
        }

        @Override
        protected void onPostExecute(final ArrayList<QuestionList> questionLists) {
            super.onPostExecute(questionLists);
            mButtonNext.setEnabled(false);
            mButtonPrev.setEnabled(false);
            showNextQuestionFragment(questionLists.get(0));
            // questionNumber++;

            questionLists_Array = questionLists;

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "myFragment", quizQuestionFragment);
        outState.putInt(MAXQUESTIONATTEMPT, maxQuestionAttempt);
        outState.putInt(QUESTIONNUMBER, questionNumber);
        outState.putInt(CURRENTSCORE, currentScore);
        outState.putString(ANSWER, answer);
        outState.putParcelableArrayList("arrayList", questionLists_Array);

    }

}
