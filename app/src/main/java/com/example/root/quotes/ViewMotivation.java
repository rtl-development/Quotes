package com.example.root.quotes;

import android.appwidget.AppWidgetManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.root.quotes.data_model.Sentence;

public class ViewMotivation extends AppCompatActivity {

    private String dataContent;
    private String dataTitle;
    private boolean isDataDefault;
    private int dataId;
    private SerializableSentence serializable;
    private SentenceViewModel sentenceViewModel;
    private boolean isWidgetIntent;
    //private Intent intentSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_motivation);

        if (findViewById(R.id.view_motivation_fragment) != null)
            if (savedInstanceState != null)
            {
                dataId = savedInstanceState.getInt(AddEditSentenceActivity.DATA_ID);
                dataTitle = savedInstanceState.getString(AddEditSentenceActivity.DATA_TITLE);
                dataContent = savedInstanceState.getString(AddEditSentenceActivity.DATA_CONTENT);
                isDataDefault = savedInstanceState.getBoolean(AddEditSentenceActivity.DATA_IS_DEFAULT);

                return;
            }

        // getIntent() will be from either: (SentencesFragment) or (Notification) or (Widget)
        Intent intent = getIntent();

        if (intent != null)
        {
            serializable = (SerializableSentence) intent.getSerializableExtra(SentencesFragment.SERIALIZABLE_SENTENCE);

            if(serializable != null)
            {
                dataId = serializable.getId();
                dataTitle = serializable.getTitle();
                dataContent = serializable.getContent();
                isDataDefault = serializable.getIsDefault();
            }

            // if true then it is intent from widget
            //isWidgetIntent = intent.hasExtra(QuoteWidget.WIDGET_ID);

            // I will need it only when the intent != null
            sentenceViewModel = new ViewModelProvider(this).get(SentenceViewModel.class);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.view_motivation_fragment, new ViewSentenceFragment()).commit();
    }

    ActivityResultLauncher<Intent> startActivityForResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                    , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null)
                        updateCurrentSentence(result.getData());
                }
            });

    private void openEditSentenceActivity()
    {
        Intent intent = new Intent(this, AddEditSentenceActivity.class);
        intent.putExtra(SentencesFragment.SERIALIZABLE_SENTENCE, serializable);
        intent.putExtra(AddEditSentenceActivity.DATA_ID, dataId);
        startActivityForResult.launch(intent);
    }

    private void updateCurrentSentence(Intent data)
    {
        if(data != null)
        {
            serializable = (SerializableSentence)
                    data.getSerializableExtra(SentencesFragment.SERIALIZABLE_SENTENCE);

            if(serializable == null)
                return;

            dataId = serializable.getId();
            dataContent = serializable.getContent();
            dataTitle = serializable.getTitle();
            isDataDefault = serializable.getIsDefault();

            Sentence sentence = new Sentence(dataContent, dataTitle, isDataDefault);
            sentence.setSentenceId(dataId);

            sentenceViewModel.updateSentence(sentence);

            updateFragmentContents(sentence);

           /*if(isWidgetIntent)
            {
                Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(SentencesFragment.SERIALIZABLE_SENTENCE, serializable);
                intent.putExtra(QuoteWidget.WIDGET_ID,
                        getIntent().getIntExtra(QuoteWidget.WIDGET_ID, -1));
                sendBroadcast(intent);
            }*/

            Toast.makeText(this, R.string.sentence_updated_msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFragmentContents(Sentence sentence)
    {
        ViewSentenceFragment viewSentenceFragment = (ViewSentenceFragment) getSupportFragmentManager()
                .findFragmentById(R.id.view_motivation_fragment);

        if(viewSentenceFragment != null)
            viewSentenceFragment.setContent(sentence);
    }

    private void shareContent()
    {
        Intent shareIntent = new Intent();
        shareIntent.setType("text/plain");
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, dataContent);

        startActivity(Intent.createChooser(shareIntent, null));
    }

    private void deleteSentence() {
        Sentence sentence = new Sentence(dataContent, dataTitle, isDataDefault);
        sentence.setSentenceId(dataId);

        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_alert_title)
                .setMessage(R.string.delete_sentence_item)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        sentenceViewModel.deleteSentence(sentence);
                        ViewMotivation.this.finish();
                    }
                })
                .setNegativeButton(R.string.cancel_delete, null)
                .show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putInt(AddEditSentenceActivity.DATA_ID, dataId);
        outState.putString(AddEditSentenceActivity.DATA_TITLE, dataTitle);
        outState.putString(AddEditSentenceActivity.DATA_CONTENT, dataContent);
        outState.putBoolean(AddEditSentenceActivity.DATA_IS_DEFAULT, isDataDefault);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_share_delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.share) {
            shareContent();
            return true;
        } else if (item.getItemId() == R.id.edit_sentence_item) {
            openEditSentenceActivity();
            return true;
        } else // delete item
        {
            deleteSentence();
            //finish();
            return true;
        }
    }

}