package com.example.gkdaily.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.gkdaily.R;
import com.example.gkdaily.ui.QuestionTypeListFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListViewWidgetService extends RemoteViewsService {

    public static String POSITION = "position";
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewFactory(this.getApplicationContext(), intent);
    }

    class ListViewFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private ArrayList<String> questionTypeList = new ArrayList<>();


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

            SharedPreferences sharedPreferences_ingredient_list = getSharedPreferences(QuestionTypeListFragment.SHARED_PREF_WIDGET_QUESTION_LIST, MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences_ingredient_list.getString(QuestionTypeListFragment.QUESTION_LIST_SP, null);
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            questionTypeList = gson.fromJson(json, type);



        }

        public ListViewFactory(Context context, Intent intent) {
            mContext = context;

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return questionTypeList==null?0:questionTypeList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.question_type_list_widget);
            remoteViews.setTextViewText(R.id.questionType_name_text, questionTypeList.get(position));
            int size = questionTypeList.size();
            Bundle extras = new Bundle();
            extras.putInt(POSITION, position);
            Intent fillIntent = new Intent();
            fillIntent.putExtras(extras);
            remoteViews.setOnClickFillInIntent(R.id.questionType_name_text, fillIntent);


            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
