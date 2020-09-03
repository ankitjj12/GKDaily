package com.example.gkdaily.roomDB;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "GKDB")
public class GKDBEntity implements Serializable, Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "QN")
    private int qn;

    @ColumnInfo(name = "TypeID")
    private int typeID;

    @ColumnInfo(name = "Question")
    private String question;

    @ColumnInfo(name = "AnswerA")
    private String answerA;

    @ColumnInfo(name = "AnswerB")
    private String answerB;

    @ColumnInfo(name = "AnswerC")
    private String answerC;

    @ColumnInfo(name = "AnswerD")
    private String answerD;

    @ColumnInfo(name = "Correct_Answer")
    private String correctAnswer;



    public GKDBEntity(int qn, int typeID, String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer) {
        this.qn = qn;
        this.typeID = typeID;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
    }

    public int getQn() {
        return qn;
    }

    public int getTypeID() {
        return typeID;
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
