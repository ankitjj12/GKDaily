package com.example.gkdaily.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.gkdaily.R;
import com.example.gkdaily.questionDB.DBAccess;
import com.example.gkdaily.questionDB.QuestionList;

import java.util.ArrayList;


public class QuizQuestionFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private int position_clicked;
    private TextView mQuestion;
    private TextView mAnswerA;
    private TextView mAnswerB;
    private TextView mAnswerC;
    private TextView mAnswerD;


    private TextView mQuestionNumber;
    private TextView mScore;


    public static String ANSWER_A = "A";
    public static String ANSWER_B = "B";
    public static String ANSWER_C = "C";
    public static String ANSWER_D = "D";
    public static String answerSelected="";
    public TextView selectedTextView;
    public static Boolean isRight;
    public static String correct_answer;
    public QuestionList currentQuestion;
    public int currentQuestionNumber;
    public static int MAXQUESTIONCOUNT = 10;
    public int currentScore = 0;
    public int colorIdA;
    public ColorDrawable colorTextA;
    public int colorIdB;
    public ColorDrawable colorTextB;
    public int colorIdC;
    public ColorDrawable colorTextC;
    public int colorIdD;
    public ColorDrawable colorTextD;




    public static String ANSWER_A_LABEL = "1. ";
    public static String ANSWER_B_LABEL = "2. ";
    public static String ANSWER_C_LABEL = "3. ";
    public static String ANSWER_D_LABEL = "4. ";


    //For onSaved Instance
    public static String SELECTED_ANSWER = "selected_answer";
    public static String CURRENT_QUESTION_NUMBER = "current_question_number";
    public static String CURRENT_SCORE = "current_score";
    public static String CURRENT_ANSWERA = "answer_A";
    public static String CURRENT_ANSWERB = "answer_B";
    public static String CURRENT_ANSWERC = "answer_C";
    public static String CURRENT_ANSWERD = "answer_D";
    public static String SAVE_BACKGROUNDCOLOR_A = "background_color_selected_A";
    public static String SAVE_BACKGROUNDCOLOR_B = "background_color_selected_B";
    public static String SAVE_BACKGROUNDCOLOR_C = "background_color_selected_C";
    public static String SAVE_BACKGROUNDCOLOR_D = "background_color_selected_D";


    public String answerA;
    public String answerB;
    public String answerC;
    public String answerD;

    public QuizQuestionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            if ((QuestionList) getArguments().getSerializable(QuizQuestionActivity.QUESTION_KEY) != null) {
                currentQuestion = (QuestionList) getArguments().getSerializable(QuizQuestionActivity.QUESTION_KEY);
                currentQuestionNumber = getArguments().getInt(QuizQuestionActivity.QUESTION_NUMBER);
                currentScore = getArguments().getInt(QuizQuestionActivity.CURRENT_SCORE);
                answerA = currentQuestion.getAnswerA();
                answerB = currentQuestion.getAnswerB();
                answerC = currentQuestion.getAnswerC();
                answerD = currentQuestion.getAnswerD();
            }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_quiz_question, container, false);


        if(savedInstanceState != null){
            answerSelected = savedInstanceState.getString(SELECTED_ANSWER);
            currentQuestionNumber = savedInstanceState.getInt(CURRENT_QUESTION_NUMBER);
            currentScore = savedInstanceState.getInt(CURRENT_SCORE);
            answerA = savedInstanceState.getString(CURRENT_ANSWERA);
            answerB = savedInstanceState.getString(CURRENT_ANSWERB);
            answerC = savedInstanceState.getString(CURRENT_ANSWERC);
            answerD = savedInstanceState.getString(CURRENT_ANSWERD);
        }


        mQuestion = rootView.findViewById(R.id.question);
        mAnswerA = rootView.findViewById(R.id.answerA);
        mAnswerB = rootView.findViewById(R.id.answerB);
        mAnswerC = rootView.findViewById(R.id.answerC);
        mAnswerD = rootView.findViewById(R.id.answerD);
        mQuestionNumber = rootView.findViewById(R.id.questionNumber);
        mScore = rootView.findViewById(R.id.score);

        mQuestion.setText(currentQuestion.getQuestion());
        mAnswerA.setText(ANSWER_A_LABEL + answerA);
        mAnswerB.setText(ANSWER_B_LABEL + answerB);
        mAnswerC.setText(ANSWER_C_LABEL + answerC);
        mAnswerD.setText(ANSWER_D_LABEL + answerD);

        mQuestionNumber.setText(currentQuestionNumber + "/"+MAXQUESTIONCOUNT);
        mScore.setText(String.valueOf(currentScore) + "/"+MAXQUESTIONCOUNT);

        if(savedInstanceState!=null){

            colorIdA = savedInstanceState.getInt(SAVE_BACKGROUNDCOLOR_A);
            colorIdB = savedInstanceState.getInt(SAVE_BACKGROUNDCOLOR_B);
            colorIdC = savedInstanceState.getInt(SAVE_BACKGROUNDCOLOR_C);
            colorIdD = savedInstanceState.getInt(SAVE_BACKGROUNDCOLOR_D);

            mAnswerA.setBackgroundResource(colorIdA);
            mAnswerB.setBackgroundResource(colorIdB);
            mAnswerC.setBackgroundResource(colorIdC);
            mAnswerD.setBackgroundResource(colorIdD);
            if(answerSelected.equals(ANSWER_A)){

                selectedTextView = mAnswerA;
            } else if(answerSelected.equals(ANSWER_B)){

                selectedTextView = mAnswerB;
            } else if(answerSelected.equals(ANSWER_C)){

                selectedTextView = mAnswerC;
            } else if(answerSelected.equals(ANSWER_D)){

                selectedTextView = mAnswerD;
            }

        }

        mAnswerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerA.setBackgroundResource(R.color.selectColor);
                mAnswerB.setBackgroundResource(R.color.unselectColor);
                mAnswerC.setBackgroundResource(R.color.unselectColor);
                mAnswerD.setBackgroundResource(R.color.unselectColor);
                setAnswer(ANSWER_A, mAnswerA);
            }
        });

        mAnswerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerA.setBackgroundResource(R.color.unselectColor);
                mAnswerB.setBackgroundResource(R.color.selectColor);
                mAnswerC.setBackgroundResource(R.color.unselectColor);
                mAnswerD.setBackgroundResource(R.color.unselectColor);
                setAnswer(ANSWER_B, mAnswerB);

            }
        });

        mAnswerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerA.setBackgroundResource(R.color.unselectColor);
                mAnswerB.setBackgroundResource(R.color.unselectColor);
                mAnswerC.setBackgroundResource(R.color.selectColor);
                mAnswerD.setBackgroundResource(R.color.unselectColor);
                setAnswer(ANSWER_C, mAnswerC);
            }
        });

        mAnswerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerA.setBackgroundResource(R.color.unselectColor);
                mAnswerB.setBackgroundResource(R.color.unselectColor);
                mAnswerC.setBackgroundResource(R.color.unselectColor);
                mAnswerD.setBackgroundResource(R.color.selectColor);
                setAnswer(ANSWER_D, mAnswerD);

            }
        });



        return rootView;
    }

    public void setAnswer (String answer, TextView selectedText){
        answerSelected = answer;
        selectedTextView = selectedText;
        setSelectedColorID();

    }

    public static String getAnswer(){
        return answerSelected;
    }

    public void isAnswerRight(Boolean isRight_activity, String correct_answer_activity, int currentScore){
        isRight = isRight_activity;
        correct_answer = correct_answer_activity;
        mScore.setText(String.valueOf(currentScore) + "/"+MAXQUESTIONCOUNT);
        if(isRight){
                selectedTextView.setBackgroundResource(R.color.correctAnswer);
                setSelectedColorID();

        } else {
            selectedTextView.setBackgroundResource(R.color.wrongAnswer);
            setSelectedColorID();
            setcorrectAnswerColor();
        }
    }

    public void setcorrectAnswerColor (){
        if(correct_answer.equals(ANSWER_A)){
            mAnswerA.setBackgroundResource(R.color.correctAnswer);
        } else if (correct_answer.equals(ANSWER_B)){
            mAnswerB.setBackgroundResource(R.color.correctAnswer);
        } else if (correct_answer.equals(ANSWER_C)){
            mAnswerC.setBackgroundResource(R.color.correctAnswer);
        } else if (correct_answer.equals(ANSWER_D)){
            mAnswerD.setBackgroundResource(R.color.correctAnswer);
        }
        setSelectedColorID();
    }

    public void setSelectedColorID(){
        colorTextA = (ColorDrawable) mAnswerA.getBackground();
        colorIdA = colorTextA.getColor();
        colorTextB = (ColorDrawable) mAnswerB.getBackground();
        colorIdB = colorTextB.getColor();
        colorTextC = (ColorDrawable) mAnswerC.getBackground();
        colorIdC = colorTextC.getColor();
        colorTextD = (ColorDrawable) mAnswerD.getBackground();
        colorIdD = colorTextD.getColor();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SELECTED_ANSWER, answerSelected);
        outState.putInt(CURRENT_QUESTION_NUMBER, currentQuestionNumber);
        outState.putInt(CURRENT_SCORE, currentScore);
        outState.putString(CURRENT_ANSWERA, answerA);
        outState.putString(CURRENT_ANSWERB, answerB);
        outState.putString(CURRENT_ANSWERC, answerC);
        outState.putString(CURRENT_ANSWERD, answerD);
        outState.putInt(SAVE_BACKGROUNDCOLOR_A, colorIdA);
        outState.putInt(SAVE_BACKGROUNDCOLOR_B, colorIdB);
        outState.putInt(SAVE_BACKGROUNDCOLOR_C, colorIdC);
        outState.putInt(SAVE_BACKGROUNDCOLOR_D, colorIdD);



    }

}
