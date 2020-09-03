package com.example.gkdaily.roomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GKDBDAO {

    @Query("SELECT * FROM GKDB WHERE TypeID = :type_id" )
    public LiveData<List<GKDBEntity>> getQuestion(int type_id);

}
