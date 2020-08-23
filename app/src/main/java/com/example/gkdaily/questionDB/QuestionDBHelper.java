package com.example.gkdaily.questionDB;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class QuestionDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "GKDailyDB.db";
    private static final int DATABASE_VERSION = 1;



    public QuestionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
