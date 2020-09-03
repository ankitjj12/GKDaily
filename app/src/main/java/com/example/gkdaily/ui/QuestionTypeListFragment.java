package com.example.gkdaily.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gkdaily.R;
import com.example.gkdaily.roomDB.QuizRoomDataBase;
import com.example.gkdaily.roomDB.TypeDBEntity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class QuestionTypeListFragment extends Fragment {

    public RecyclerView recyclerViewQuestionType;
    //private OnFragmentInteractionListener mListener;
    public static int GRID_COLUMN = 2;
    QuestionTypeListAdapter questionTypeListAdapter;

    public static final String SHARED_PREF_WIDGET_QUESTION_LIST = "widget_question_pref";
    public static final String QUESTION_LIST_SP = "question_type_list";

    public QuesListViewModel quesListViewModel;

    private String questionType;
    private ArrayList<String> questionTypeArrayList = new ArrayList<>();

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
        quesListViewModel = ViewModelProviders.of(this).get(QuesListViewModel.class);
        quesListViewModel.getAllType().observe(this, new Observer<List<TypeDBEntity>>() {
            @Override
            public void onChanged(List<TypeDBEntity> typeDBEntities) {
                    if(typeDBEntities!=null){
                        questionTypeListAdapter = new QuestionTypeListAdapter(getContext(), typeDBEntities);
                        recyclerViewQuestionType.setAdapter(questionTypeListAdapter);
                        updateWidgetListService(getContext(), typeDBEntities);

                    }
            }
        });



        return rootView;
    }



    public void updateWidgetListService(Context context, List<TypeDBEntity> typeDBEntitiesWidget){

        for (int i=0; i < typeDBEntitiesWidget.size(); i++){
            questionType = typeDBEntitiesWidget.get(i).getTopic();
            questionTypeArrayList.add(questionType);
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_WIDGET_QUESTION_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_widget = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(questionTypeArrayList);
        editor_widget.putString(QUESTION_LIST_SP, json);
        editor_widget.apply();


    }

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


}
