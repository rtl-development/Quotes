package com.example.root.quotes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.root.quotes.data_model.Sentence;

public class QuoteWidget extends AppWidgetProvider
{
    protected static final String WIDGET_ID = "WIDGET_ID";
    private Sentence sentence; // = null;
    //must add listener be to notified when sentences goes empty .. to show "empty msg"

    /*
     * Note: If the device is asleep when it is time for an update
     * (as defined by updatePeriodMillis), then the device will wake up in order to
     * perform the update. If you don't update more than once per hour,
     * this probably won't cause significant problems for the battery life.
     * If, however, you need to update more frequently and/or you do not need to
     *  update while the device is asleep, then you can instead perform updates
     * based on an alarm that will not wake the device. To do so, set an alarm with
     * an Intent that your AppWidgetProvider receives, using the AlarmManager.
     * Set the alarm type to either ELAPSED_REALTIME or RTC,
     * which will only deliver the alarm when the device is awake.
     * Then set updatePeriodMillis to zero ("0").
     * */

    /* if there is no sentences yet ==> do not create widget
       if sentences were deleted ==> delete already created widgets
    */

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    protected void updateAppWidget(Context context, AppWidgetManager appWidgetManager
            , int appWidgetId)
    {
        if(getWidgetSentence(context) != null)
        {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.quote_widget);
            remoteViews.setTextViewText(R.id.widget_content, sentence.getSentenceContent());

            SerializableSentence serializable =
                    new SerializableSentence(sentence.getSentenceContent(),
                            sentence.getSentenceTitle(), sentence.getIsDefaultMotivation());
            serializable.setId(sentence.getSentenceId());

            Intent intent = new Intent(context, ViewMotivation.class);
            intent.putExtra(SentencesFragment.SERIALIZABLE_SENTENCE, serializable);
            intent.putExtra(WIDGET_ID, appWidgetId);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    appWidgetId, intent, PendingIntent.FLAG_IMMUTABLE);

            remoteViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    private Sentence getWidgetSentence(Context context)
    {
            RandomQuote randomQuote = new RandomQuote(context);
            sentence = randomQuote.getRandomSentence(); //it will be null when there are no sentences
            return sentence;
    }

    /*@Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE"))
        {
            if(intent.hasExtra(SentencesFragment.SERIALIZABLE_SENTENCE))
            {
                int[] widgetId = {intent.getIntExtra(WIDGET_ID, -1)};

                SerializableSentence serializable =
                        (SerializableSentence) intent.getSerializableExtra
                                (SentencesFragment.SERIALIZABLE_SENTENCE);

                sentence = new Sentence(serializable.getContent(), serializable.getTitle()
                        , serializable.getIsDefault());
                sentence.setSentenceId(serializable.getId());

                onUpdate(context, AppWidgetManager.getInstance(context), widgetId);
            }
        }
        super.onReceive(context, intent);
    }*/
}