package com.example.root.quotes;

import android.app.Application;
import android.content.Context;

import com.example.root.quotes.data_model.Sentence;

public class RandomQuote {

    private static final int SENTENCE_NOTIFICATION_ICON = R.drawable.ic_local_florist_black_24dp;

    private final Sentence randomSentence;
    private final int notificationIcon;

    public RandomQuote(Context context)
    {

        SentenceRepository sRepository =
                SentenceRepository.getInstance((Application) context.getApplicationContext());

        randomSentence = sRepository.getRandomSentenceObj();
        notificationIcon = SENTENCE_NOTIFICATION_ICON;
    }

    public Sentence getRandomSentence() {
        return randomSentence;
    }

    public int getNotificationIcon() {
        return notificationIcon;
    }
}