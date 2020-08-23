package com.example.gkdaily.ui;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private Button mPrevious;
    private Button mSubmit;
    private Button mNext;



    public QuizQuestionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_quiz_question, container, false);

        ArrayList<QuestionList> questionListsArray_local;


        mQuestion = rootView.findViewById(R.id.question);
        mAnswerA = rootView.findViewById(R.id.answerA);
        mAnswerB = rootView.findViewById(R.id.answerB);
        mAnswerC = rootView.findViewById(R.id.answerC);
        mAnswerD = rootView.findViewById(R.id.answerD);

        new FetchQuestion().execute();



        return rootView;
    }


    public class FetchQuestion extends AsyncTask<String, Void, ArrayList<QuestionList>>{

        public ArrayList<QuestionList> questionLists_array;



        @Override
        protected ArrayList<QuestionList> doInBackground(String... strings) {

            ArrayList<QuestionList> questionLists = new ArrayList<>();
            DBAccess dbAccessQuestion = DBAccess.getInstance(getContext());
            dbAccessQuestion.openDB();

            questionLists = dbAccessQuestion.getAllQuestion(position_clicked);

            return questionLists;
        }

        @Override
        protected void onPostExecute(ArrayList<QuestionList> questionLists) {
            super.onPostExecute(questionLists);



        }
    }

    public void getPositionClicked(int position){
        position_clicked = position;

    }

}
