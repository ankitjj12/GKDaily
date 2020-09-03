package com.example.gkdaily.roomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TypeDBEntity.class, GKDBEntity.class}, version = 1, exportSchema = false)
public abstract class QuizRoomDataBase extends RoomDatabase {

    public static String DATABASE = "quizDataBase";
    private static final Object LOCK = new Object();
    private static QuizRoomDataBase INSTANCE;
    public abstract GKDBDAO gkdbdao();
    public abstract TypeDAO typeDAO();

    public static QuizRoomDataBase getDataBase(Context context){

        if(INSTANCE == null){
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        QuizRoomDataBase.class, QuizRoomDataBase.DATABASE)
                        .createFromAsset("databases/GKDailyDB.db")
                        .build();
            }
        }

        return INSTANCE;
    }

}
