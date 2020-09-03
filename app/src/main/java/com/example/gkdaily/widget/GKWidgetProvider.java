package com.example.gkdaily.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.gkdaily.R;
import com.example.gkdaily.ui.MainActivity;
import com.example.gkdaily.ui.QuestionTypeListFragment;

/**
 * Implementation of App Widget functionality.
 */
public class GKWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_UPDATE_WIDGET = "com.example.gkdaily.action.update_question_widget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);

        RemoteViews views;

        views = getListView(context, appWidgetId);

       /* if(width < 300){
            views = bakingImageOnly(context);
        } else {

        }*/



        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static RemoteViews bakingImageOnly(Context context){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.gkwidget_provider);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.book_widget_image, pendingIntent);

        return views;

    }

    private static RemoteViews getListView(Context context, int appWidgetId){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.listview_widget);

        Intent intent = new Intent(context, ListViewWidgetService.class);

        views.setRemoteAdapter(R.id.listview_questionType_widget, intent);


       // views.setTextViewText(R.id.questionType_name_text, recipe_name_shared_pref);


        //Intent intent = new Intent(context, ListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        Intent openAppIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntentListView = PendingIntent.getActivity(context, 0, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.listview_questionType_widget, pendingIntentListView);

        views.setEmptyView(R.id.listview_questionType_widget, R.id.book_widget_image);
        return views;
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        this.updateAppWidget(context, appWidgetManager, appWidgetId);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ACTION_UPDATE_WIDGET)){
            Bundle appExtra = intent.getExtras();
            AppWidgetManager appWidgetManager_GAW = AppWidgetManager.getInstance(context);

            int [] appWidgetIds = appExtra.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_ID);

            if(appWidgetIds!=null && appWidgetIds.length > 0) {
                appWidgetManager_GAW.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listview_questionType_widget);


                SharedPreferences sharedPreferences = context.getSharedPreferences(QuestionTypeListFragment.SHARED_PREF_WIDGET_QUESTION_LIST, Context.MODE_PRIVATE);

                this.onUpdate(context, appWidgetManager_GAW, appWidgetIds);
            }

        }
        super.onReceive(context, intent);
    }

}

