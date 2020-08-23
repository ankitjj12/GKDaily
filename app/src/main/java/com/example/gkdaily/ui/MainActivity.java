package com.example.gkdaily.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.gkdaily.R;
import com.example.gkdaily.questionDB.DBAccess;
import com.example.gkdaily.questionDB.QuestionType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements QuestionTypeListAdapter.OnclickPassType{

    public RecyclerView recyclerView;
    public int position_clicked;
    public String typeName_clicked;
    public static final String QUESTION_TYPE_CLICKED = "question_type_clicked";
    public static final String  QUESTION_TYPE_POSITION = "question_position_clicked";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public void questionTypeClicked(int position, String typeName) {
        position_clicked = position;
        typeName_clicked = typeName;

        Intent intent_position = new Intent(this, QuizQuestionActivity.class);
        intent_position.putExtra(QUESTION_TYPE_CLICKED, typeName_clicked);
        intent_position.putExtra(QUESTION_TYPE_POSITION, position_clicked);
        startActivity(intent_position);

    }
}
