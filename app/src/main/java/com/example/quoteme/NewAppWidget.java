package com.example.quoteme;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import androidx.room.Room;

import java.util.Random;


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

// widget class handles widget layout creation and handles updates/interactions

// method to update the widget when called
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//get database instance
        MyDatabase myDatabase = MyDatabase.getInstance(context.getApplicationContext());


        //small array for testing
        //String[] arr = {"one","two","Three","Four","Five","Six"};

        // get array of quote id's
        int[] ids = myDatabase.dao().getIDs();

        // get number of quotes
        int count = myDatabase.dao().getCount();

        //get the length of the db
        int length = ids.length;
        //get random number for index
        int random = RandomNumber(0,length-1);



        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        //if there are no quotes, display message, else get a random quote
        if (count<1)
        {
            views.setTextViewText(R.id.quote_text, "No quotes to Show");
            views.setTextViewText(R.id.source_text, " ");

        }
        else {
            Quotes rand = myDatabase.dao().randomQuote(ids[random]);

            //set to a random quote
            views.setTextViewText(R.id.quote_text, rand.getQuote());
            views.setTextViewText(R.id.source_text, rand.getSource());
        }


        //create intent to update widget
        Intent intentUpdate = new Intent(context, NewAppWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        // get array of widget id's
        int[] idArray = new int[]{appWidgetId};


        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        // create pending update
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // set the click listener for the quote text view in the widget
        views.setOnClickPendingIntent(R.id.quote_text, pendingUpdate);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }




// on update execute update method above
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }





    // select random array string
    public static String randomText(String[] arr, int id){
        String Text = arr[id];
        return Text;
    }

    // generate randomnumber
    public static int RandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


}// end of widget class