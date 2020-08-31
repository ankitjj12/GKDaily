package com.example.gkdaily.questionDB;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionList implements Serializable, Parcelable {

    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correctAnswer;


    public static String GKBD_TABLE_COLUMN_QUESTION = "Question";
    public static String GKBD_TABLE_COLUMN_TYPEID = "TypeID";
    public static String GKBD_TABLE_COLUMN_ANSWERA = "AnswerA";
    public static String GKBD_TABLE_COLUMN_ANSWERB = "AnswerB";
    public static String GKBD_TABLE_COLUMN_ANSWERC = "AnswerC";
    public static String GKBD_TABLE_COLUMN_ANSWERD = "AnswerD";
    public static String GKBD_TABLE_COLUMN_CORRECTANSWER = "Correct_Answer";



    public QuestionList(String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
    }


    public String getQuestion() {
        return question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
