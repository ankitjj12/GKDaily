package com.example.gkdaily.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.gkdaily.roomDB.GKDBDAO;
import com.example.gkdaily.roomDB.GKDBEntity;
import com.example.gkdaily.roomDB.QuizRoomDataBase;
import com.example.gkdaily.roomDB.TypeDAO;
import com.example.gkdaily.roomDB.TypeDBEntity;

import java.util.List;

public class QuesListViewModel extends AndroidViewModel {

    QuizRoomDataBase qDB;
    GKDBDAO gkdbDao;
    TypeDAO typeDao;

    private LiveData<List<GKDBEntity>> mQuestionList;
    private LiveData<List<TypeDBEntity>> mTypeList;

    public QuesListViewModel(@NonNull Application application) {
        super(application);
        qDB = QuizRoomDataBase.getDataBase(application);
        gkdbDao = qDB.gkdbdao();
        typeDao = qDB.typeDAO();
        mTypeList = typeDao.getAllType();

    }

    LiveData<List<TypeDBEntity>> getAllType(){
        return mTypeList;
    }


}
