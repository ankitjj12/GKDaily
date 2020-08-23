package com.example.gkdaily.questionDB;

public class QuestionType {

    private int id;
    private String topic;
    public static String TYPE_TABLE_COLUMN_ID = "ID";
    public static String TYPE_TABLE_COLUMN_TOPIC = "TOPIC";

    public QuestionType(int id, String topic) {
        this.id = id;
        this.topic = topic;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }
}
