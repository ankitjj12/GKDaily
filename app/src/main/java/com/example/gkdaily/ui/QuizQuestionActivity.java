package com.example.gkdaily.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gkdaily.R;
import com.example.gkdaily.roomDB.GKDBEntity;
import com.example.gkdaily.roomDB.QuizRoomDataBase;


import java.util.ArrayList;
import java.util.List;

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
    public List<GKDBEntity> questionLists_Array;
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
    public static String ANSWER = "answer";
    public static String ANSWER_CHOSEN = "answer_chosen";
    public static String BUTTON_BOOL_PREV = "prev_bool";
    public static String BUTTON_BOOL_NEXT = "next_bool";
    public static String BUTTON_BOOL_SUBMIT = "submit_bool";

    //save Button state
    public boolean buttonPrev = false;
    public boolean buttonSubmit = true;
    public boolean buttonNext = false;


    public int currentScore = 0;


    private QuizRoomDataBase mDb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);


        getSupportActionBar().setTitle(typeName_clicked);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {

            quizQuestionFragment = (QuizQuestionFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myFragment");
            maxQuestionAttempt = savedInstanceState.getInt(MAXQUESTIONATTEMPT);
            questionNumber = savedInstanceState.getInt(QUESTIONNUMBER);
            currentScore = savedInstanceState.getInt(CURRENTSCORE);
            questionLists_Array = savedInstanceState.getParcelableArrayList("arrayList");
            buttonNext = savedInstanceState.getBoolean(BUTTON_BOOL_NEXT);
            buttonPrev = savedInstanceState.getBoolean(BUTTON_BOOL_PREV);
            buttonSubmit = savedInstanceState.getBoolean(BUTTON_BOOL_SUBMIT);


        }

        mDb = QuizRoomDataBase.getDataBase(getApplicationContext());

        position_clicked = (getIntent().getIntExtra(MainActivity.QUESTION_TYPE_POSITION, 0) + 1);
        typeName_clicked = getIntent().getStringExtra(MainActivity.QUESTION_TYPE_CLICKED);

        QuizQuestionViewModelFactory factory = new QuizQuestionViewModelFactory(mDb, position_clicked);


        final LoadQuestionsViewModel loadQuestionsViewModel = ViewModelProviders.of(this, factory).get(LoadQuestionsViewModel.class);
        loadQuestionsViewModel.getQuestions().observe(this, new Observer<List<GKDBEntity>>() {
            @Override
            public void onChanged(List<GKDBEntity> gkdbEntities) {
                loadQuestionsViewModel.getQuestions().removeObserver(this);
                questionLists_Array = gkdbEntities;
                if (savedInstanceState == null) {
                    showNextQuestionFragment(questionLists_Array.get(0));
                }
            }
        });

        fragmentManager = getSupportFragmentManager();


        mButtonPrev = findViewById(R.id.previous);
        mButtonSubmit = findViewById(R.id.submit);
        mButtonNext = findViewById(R.id.next);
        mButtonPrev.setEnabled(false);
        mButtonNext.setEnabled(false);

        if (savedInstanceState != null) {
            mButtonNext.setEnabled(buttonNext);
            mButtonPrev.setEnabled(buttonPrev);
            mButtonSubmit.setEnabled(buttonSubmit);
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
                        buttonNext = false;
                        buttonPrev = false;
                        buttonSubmit = true;
                    } else {
                        mButtonNext.setEnabled(true);
                        mButtonPrev.setEnabled(true);
                        mButtonSubmit.setEnabled(false);
                        buttonNext = true;
                        buttonPrev = true;
                        buttonSubmit = false;
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
                buttonNext = true;
                buttonPrev = true;
                buttonSubmit = false;
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
                    buttonNext = true;
                    buttonPrev = true;
                    buttonSubmit = false;

                    if (answer.equals(answer_chosen)) {
                        currentScore++;

                        quizQuestionFragment.isAnswerRight(true, answer, currentScore);

                    } else {

                        quizQuestionFragment.isAnswerRight(false, answer, currentScore);
                    }
                }


            }
        });


    }


    public void showNextQuestionFragment(GKDBEntity question) {

        quizQuestionFragment = new QuizQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION_KEY, question);
        bundle.putInt(QUESTION_NUMBER, questionNumber + 1);
        bundle.putInt(CURRENT_SCORE, currentScore);

        quizQuestionFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.quiz_ques_ans_container, quizQuestionFragment).commit();


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "myFragment", quizQuestionFragment);
        outState.putInt(MAXQUESTIONATTEMPT, maxQuestionAttempt);
        outState.putInt(QUESTIONNUMBER, questionNumber);
        outState.putInt(CURRENTSCORE, currentScore);
        outState.putString(ANSWER, answer);
        outState.putParcelableArrayList("arrayList", new ArrayList<GKDBEntity>(questionLists_Array));
        outState.putBoolean(BUTTON_BOOL_PREV, buttonPrev);
        outState.putBoolean(BUTTON_BOOL_NEXT, buttonNext);
        outState.putBoolean(BUTTON_BOOL_SUBMIT, buttonSubmit);
    }

}
