package com.example.gkdaily.questionDB;

import java.util.ArrayList;

public class QuestionList {

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



}
