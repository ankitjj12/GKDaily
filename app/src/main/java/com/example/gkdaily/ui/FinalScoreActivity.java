package com.example.gkdaily.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gkdaily.R;

public class FinalScoreActivity extends AppCompatActivity {

    public int final_score;
    private TextView mFinalScore;
    private ImageView mTrophyImage;
    private Button mPlayAgain;
    public static String FINAL_TITLE = "QUIZ RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        mFinalScore = findViewById(R.id.final_score);
        mTrophyImage = findViewById(R.id.trophy_image);
        mPlayAgain = findViewById(R.id.play_again);

        getSupportActionBar().setTitle(FINAL_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final_score = getIntent().getIntExtra(QuizQuestionActivity.FINAL_SCORE, 0);
        mFinalScore.setText(String.valueOf(final_score)+"/"+QuizQuestionFragment.MAXQUESTIONCOUNT);
        if(final_score == 10){

            mTrophyImage.setImageResource(R.drawable.gold);
        } else if(final_score > 7){
            mTrophyImage.setImageResource(R.drawable.silver_medal);

        } else if(final_score > 4){
            mTrophyImage.setImageResource(R.drawable.clapping_hands);
        } else {
            mTrophyImage.setImageResource(R.drawable.less_score);
        }

        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_mainActivity = new Intent(FinalScoreActivity.this, MainActivity.class);
                startActivity(intent_mainActivity);
            }
        });

    }
}
