package com.example.gkdaily.questionDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBAccess {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static DBAccess instance;
    public static final int MAX = 10;



    Cursor questionTypecursor = null;
    Cursor questionListCursor = null;

    private DBAccess(Context context){

        this.sqLiteOpenHelper = new QuestionDBHelper(context);

    }

    //singleton class
    public static DBAccess getInstance(Context context){

        if(instance == null){
            instance = new DBAccess(context);
        }

        return instance;
    }

    //open Database

    public void openDB(){
        this.sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    //close Database

    public void closeDB(){
        if(sqLiteDatabase!=null) {
            this.sqLiteDatabase.close();
        }
    }

    public ArrayList<QuestionType> getAllTopic(){

        ArrayList<QuestionType> questionTypes_array = new ArrayList<>();

        questionTypecursor = sqLiteDatabase.rawQuery("SELECT * from TYPE;", null);


        if(questionTypecursor.moveToFirst()) {

            while (!questionTypecursor.isAfterLast()) {

                QuestionType questionType = new QuestionType(questionTypecursor.getInt(questionTypecursor.getColumnIndex(QuestionType.TYPE_TABLE_COLUMN_ID)),
                        questionTypecursor.getString(questionTypecursor.getColumnIndex(QuestionType.TYPE_TABLE_COLUMN_TOPIC)));

                questionTypes_array.add(questionType);
                questionTypecursor.moveToNext();

            }

        }
        questionTypecursor.close();
        sqLiteDatabase.close();

        return questionTypes_array;

    }

    public ArrayList<QuestionList> getAllQuestion(int typeId){

        ArrayList<QuestionList> questionLists_array = new ArrayList<>();
        questionLists_array.clear();

        questionListCursor = sqLiteDatabase.rawQuery(String.format("SELECT * from GKDB WHERE TypeID = %d ORDER BY RANDOM()", typeId), null);

        if(questionListCursor.moveToFirst()){

            while (!questionListCursor.isAfterLast()){

               QuestionList questionList = new QuestionList(questionListCursor.getString(questionListCursor.getColumnIndex(QuestionList.GKBD_TABLE_COLUMN_QUESTION)),
                       questionListCursor.getString(questionListCursor.getColumnIndex(QuestionList.GKBD_TABLE_COLUMN_ANSWERA)),
                       questionListCursor.getString(questionListCursor.getColumnIndex(QuestionList.GKBD_TABLE_COLUMN_ANSWERB)),
                       questionListCursor.getString(questionListCursor.getColumnIndex(QuestionList.GKBD_TABLE_COLUMN_ANSWERC)),
                       questionListCursor.getString(questionListCursor.getColumnIndex(QuestionList.GKBD_TABLE_COLUMN_ANSWERD)),
                       questionListCursor.getString(questionListCursor.getColumnIndex(QuestionList.GKBD_TABLE_COLUMN_CORRECTANSWER))

               );

                questionLists_array.add(questionList);
                questionListCursor.moveToNext();

            }

        }

        return  questionLists_array;

    }



}
