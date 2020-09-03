package com.example.gkdaily.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.gkdaily.roomDB.GKDBEntity;
import com.example.gkdaily.roomDB.QuizRoomDataBase;

import java.util.List;

public class LoadQuestionsViewModel extends ViewModel {


    private LiveData<List<GKDBEntity>> questionListViewModel;


    public LoadQuestionsViewModel(QuizRoomDataBase database, int typeId) {


        questionListViewModel = database.gkdbdao().getQuestion(typeId);
    }


    public LiveData<List<GKDBEntity>> getQuestions() {
        return questionListViewModel;
    }

}
