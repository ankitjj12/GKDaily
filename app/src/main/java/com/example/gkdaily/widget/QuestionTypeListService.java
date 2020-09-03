package com.example.gkdaily.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.gkdaily.R;

public class QuestionTypeListService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public static final String ACTION_UPDATE_QUIZ_WIDGET = "com.example.gkdaily.action.update_quiz_widget";



    public QuestionTypeListService(String name) {
        super(name);
    }

    public static void startActionUpdateBakeWidget(Context context){
        Intent intent = new Intent(context, QuestionTypeListService.class);
        intent.setAction(ACTION_UPDATE_QUIZ_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(intent!= null){

            final String intentAction = intent.getAction();
            if(ACTION_UPDATE_QUIZ_WIDGET.equals(intentAction)){
                updateWidgetQuestionType();
            }

        }

    }

    private void updateWidgetQuestionType() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetId = appWidgetManager.getAppWidgetIds(new ComponentName(this, GKWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listview_questionType_widget);

    }


}
