package com.example.gkdaily.ui;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gkdaily.R;
import com.example.gkdaily.questionDB.DBAccess;
import com.example.gkdaily.questionDB.QuestionType;

import java.util.ArrayList;


public class QuestionTypeListFragment extends Fragment {

    public RecyclerView recyclerViewQuestionType;
    //private OnFragmentInteractionListener mListener;
    public static int GRID_COLUMN = 2;
    QuestionTypeListAdapter questionTypeListAdapter;

    public QuestionTypeListFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_question_type_list, container, false);

        recyclerViewQuestionType = rootView.findViewById(R.id.questionType_recycler_view);
        recyclerViewQuestionType.setLayoutManager(new GridLayoutManager(getContext(), GRID_COLUMN));

        new FetchQuestionType().execute();

        return rootView;
    }


  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    } */


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class FetchQuestionType extends AsyncTask<String, Void, ArrayList<QuestionType>> {

        @Override
        protected ArrayList<QuestionType> doInBackground(String... strings) {

            ArrayList<QuestionType> arrayListQT = new ArrayList<>();

            DBAccess dbAccess = DBAccess.getInstance(getContext());
            dbAccess.openDB();
            arrayListQT = dbAccess.getAllTopic();

            dbAccess.closeDB();

            return arrayListQT;
        }

        @Override
        protected void onPostExecute(ArrayList<QuestionType> questionTypes) {
            super.onPostExecute(questionTypes);
            questionTypeListAdapter = new QuestionTypeListAdapter(getContext(), questionTypes);
            recyclerViewQuestionType.setAdapter(questionTypeListAdapter);

        }
    }
}
