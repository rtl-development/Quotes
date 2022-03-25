package com.example.root.quotes;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import static com.example.root.quotes.AddEditSentenceActivity.DATA_ID;
import static com.example.root.quotes.AddEditSentenceActivity.DATA_CONTENT;
import static com.example.root.quotes.AddEditSentenceActivity.DATA_TITLE;
import static com.example.root.quotes.AddEditSentenceActivity.DATA_IS_DEFAULT;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.root.quotes.MyApp.CHANNEL_ID;

import com.example.root.quotes.data_model.Sentence;

public class NotificationReceiver extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 4;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        RandomQuote randomQuote = new RandomQuote(context);
        Sentence sentence = randomQuote.getRandomSentence();

        if(sentence != null)
        {
            int notificationIcon = randomQuote.getNotificationIcon();
            String notificationContent = sentence.getSentenceContent();
            String notificationTitle = sentence.getSentenceTitle();
            int notificationSentenceId = sentence.getSentenceId();
            boolean notificationSentenceIsDefault = sentence.getIsDefaultMotivation();

            SerializableSentence serializable = new SerializableSentence
                    (notificationContent, notificationTitle, notificationSentenceIsDefault);
            serializable.setId(notificationSentenceId);

            Intent notificationIntent = new Intent(context, ViewMotivation.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notificationIntent.putExtra(SentencesFragment.SERIALIZABLE_SENTENCE, serializable);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_IMMUTABLE);

            Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(notificationIcon)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationContent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationContent))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setContentIntent(pendingIntent)
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(NOTIFICATION_ID, notification);
        }

    }
}