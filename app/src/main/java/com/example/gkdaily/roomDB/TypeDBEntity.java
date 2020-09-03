package com.example.gkdaily.roomDB;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "TYPE")
public class TypeDBEntity {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "TOPIC")
    private String topic;


    public TypeDBEntity(int id, String topic) {
        this.id = id;
        this.topic = topic;
    }


    public int getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }
}
