package com.example.root.quotes;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import com.example.root.quotes.data_model.Sentence;

import java.util.List;

public class SentenceViewModel extends AndroidViewModel {

    private final SentenceRepository repository;
    private final LiveData<List<Sentence>> allSentences;

    public SentenceViewModel(@NonNull Application application){
        super(application);
        repository = SentenceRepository.getInstance(application);
        allSentences = repository.getAllSentences();
    }

   /* @Override
    protected void onCleared() {
        super.onCleared();
        ?
    }*/

    public void insertSentence(Sentence sentence){
        repository.insertSentence(sentence);
    }

    public void insertDefSentences(Context context){
        repository.insertDefSentences(context);
    }

    public void updateSentence(Sentence sentence){
        repository.updateSentence(sentence);
    }

    public void deleteSentence(Sentence... sentence){
        alarmChecking(sentence.length);
        repository.deleteSentence(sentence);
    }

    public void deleteDfltSentences() {
        alarmChecking(getDfltSentencesCount());
        repository.deleteDfltSentences();
    }

    public LiveData<List<Sentence>> getAllSentences(){
        return allSentences;
    }

    public Integer getSentencesCount(){
        return repository.getSentencesCount();
    }

    public Integer getDfltSentencesCount() {
        return repository.getDfltSentencesCount();
    }

    //public List<Integer> getAllSentencesIds() { return repository.getAllSentencesIds(); }

    private void alarmChecking(int sentencesCount)
    {
        if(sentencesCount == getSentencesCount()) // delete all sentences
        {
            // cancel alarm if any + disable random notif switch
            QuotesAlarmManager quotesAlarmManager =
                    new QuotesAlarmManager(getApplication().getApplicationContext());
            PendingIntent pendingIntent =
                    QuotesAlarmManager.getPendingIntent(getApplication().getApplicationContext()
                            , NotificationReceiver.class ,SettingsFragment.ALARM_INTENT_REQUEST_CODE
                            , PendingIntent.FLAG_UPDATE_CURRENT);

            if(pendingIntent != null)
            {
                quotesAlarmManager.cancelAlarmManager(pendingIntent);

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
                sharedPreferences.edit().putBoolean(SettingsFragment.RANDOM_NOTIFICATION_KEY, false).apply();
            }
        }
    }

    public Sentence getSentenceById(int id)
    {
        return repository.getSentenceById(id);
    }

    public Sentence getRandomSentence()
    {
        return repository.getRandomSentenceObj();
    }

    //public LiveData<List<Sentence>> getDefaultSentences(){ return DefaultSentences; }
}
