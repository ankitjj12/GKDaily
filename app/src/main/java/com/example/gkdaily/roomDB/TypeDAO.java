package com.example.gkdaily.roomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TypeDAO {

@Query("SELECT * FROM TYPE")
public LiveData<List<TypeDBEntity>> getAllType();


}
