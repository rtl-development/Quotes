package com.example.root.quotes;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

public class MyApp extends Application {

    public static final String CHANNEL_ID = "Mot_Channel_ID";
    public static final String CHANNEL_NAME = "Mot_Channel";

    /*private static final String IMAGES_DIR = "Images";
    private static final String VIDEOS_DIR = "Videos";
    private static final String AUDIO_DIR = "Audio";

    private static File imagesDir, videosDir, audioDir;*/


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        //createMediaDirectories();
    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);

            // optionally:
            channel.setDescription("This is notifications channel of myMotivation");

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /*private void createMediaDirectories()
    {
        imagesDir =
                new File(getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE), IMAGES_DIR);
        videosDir =
                new File(getDir(Environment.DIRECTORY_MOVIES, Context.MODE_PRIVATE), VIDEOS_DIR);
        audioDir =
                new File(getDir(Environment.DIRECTORY_MUSIC, Context.MODE_PRIVATE), AUDIO_DIR);

        if(!imagesDir.exists())
            imagesDir.mkdir();

        if(!videosDir.exists())
            videosDir.mkdir();

        if(!audioDir.exists())
            audioDir.mkdir();

        // Log.d("TTTTTTTTTTTTTTTTTT", imagesDir.getAbsolutePath());
    }

    public static File getImagesDir()
    {
        return imagesDir;
    }

    public static File getVideosDir()
    {
        return videosDir;
    }

    public static File getAudioDir()
    {
        return audioDir;
    }*/
}