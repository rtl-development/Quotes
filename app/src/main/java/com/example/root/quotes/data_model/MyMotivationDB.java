package com.example.root.quotes.data_model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Sentence.class}, version = 1)

public abstract class MyMotivationDB extends RoomDatabase
{
    private static MyMotivationDB instance;

    public abstract MyMotivationDao myMotivationDao();


    public static synchronized MyMotivationDB getMyMotivationDB(Context context)
    {

        if(instance == null)
        {
            instance = buildDB(context);
        }

        return instance;
    }

    public static void destroyDBInstance()
    {
        instance = null;
    }

    /*==================temp code ======================

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyMotivationDao sentenceDao;

        private PopulateDbAsyncTask(MyMotivationDB db) {
            sentenceDao = db.myMotivationDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sentenceDao.insertSentence(new Sentence("Content1", "Title1", false));
            sentenceDao.insertSentence(new Sentence("Content2", "Title2", false));
            sentenceDao.insertSentence(new Sentence("Content3", "Title3", false));
            return null;
        }
    }

    //======================================== */

    public static MyMotivationDB buildDB(Context context){

        return Room.databaseBuilder(context.getApplicationContext(),
                MyMotivationDB.class,"myMotivationDB")
                .fallbackToDestructiveMigration()
                //.addCallback(roomCallback)
                .build();

        /* return Room.databaseBuilder(context.getApplicationContext(),
                MyMotivationDB.class,"myPlannerDB").addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadScheduledExecutor().execute(new Runnable(){
                    @Override
                    public void run(){
                        getMyMotivationDB(context).myMotivationDao().insertDefaultMotivation(new Sentence().defaultSentences(context));
                        getMyMotivationDB(context).myMotivationDao().insertDefaultMotivation(new Sentence().defaultVideos(context));
                    }
                });
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);

                Executors.newSingleThreadScheduledExecutor().execute(new Runnable(){
                    @Override
                    public void run(){
                        if(getMyMotivationDB(context).myMotivationDao().getDefaultMotivationCount(true,'s') == 0)
                            getMyMotivationDB(context).myMotivationDao().insertDefaultMotivation(new Sentence().defaultSentences(context));
                        if(getMyMotivationDB(context).myMotivationDao().getDefaultMotivationCount(true,'v') == 0)
                            getMyMotivationDB(context).myMotivationDao().insertDefaultMotivation(new Sentence().defaultVideos(context));
                    }
                });
            }
        })
                .build(); */
    }


}
