package com.example.gkdaily.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.gkdaily.roomDB.QuizRoomDataBase;

public class QuizQuestionViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // COMPLETED (2) Add two member variables. One for the database and one for the taskId
    private final QuizRoomDataBase mDb;
    private final int mTypeId;

    // COMPLETED (3) Initialize the member variables in the constructor with the parameters received
    public QuizQuestionViewModelFactory(QuizRoomDataBase database, int typeId) {
        mDb = database;
        mTypeId = typeId;
    }

    // COMPLETED (4) Uncomment the following method
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new LoadQuestionsViewModel(mDb, mTypeId);
    }

}
