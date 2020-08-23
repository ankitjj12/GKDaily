package com.example.gkdaily.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Button;

import com.example.gkdaily.R;

public class QuizQuestionActivity extends AppCompatActivity {

    public int position_clicked;
    public String typeName_clicked;
    Button mButtonPrev;
    Button mButtonSubmit;
    Button mButtonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        mButtonPrev = findViewById(R.id.previous);
        mButtonSubmit = findViewById(R.id.submit);
        mButtonNext = findViewById(R.id.next);

        position_clicked = (getIntent().getIntExtra(MainActivity.QUESTION_TYPE_POSITION, 0) + 1);
        typeName_clicked = getIntent().getStringExtra(MainActivity.QUESTION_TYPE_CLICKED);
        setTitle(typeName_clicked);


        QuizQuestionFragment quizQuestionFragment = new QuizQuestionFragment();
        quizQuestionFragment.getPositionClicked(position_clicked);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.quiz_ques_ans_container, quizQuestionFragment).commit();




    }
}
