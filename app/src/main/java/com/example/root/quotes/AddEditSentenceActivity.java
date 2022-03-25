package com.example.root.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditSentenceActivity extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private int id;
    SerializableSentence serializable;

    public static final String DATA_ID = "DATA_ID";
    public static final String DATA_TITLE = "DATA_TITLE";
    public static final String DATA_CONTENT = "DATA_CONTENT";
    public static final String DATA_IS_DEFAULT = "DATA_IS_DEFAULT";

    //public static final String OPERATION_IS_EDIT = "OPERATION_IS_EDIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(SettingSharedPreferences.getInstance(this).getCurrentTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sentence);

        titleEditText = findViewById(R.id.add_sentence_title);
        contentEditText = findViewById(R.id.add_sentence_content);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel_black_24dp);

        // either from sentenceFragment(add) or viewMotivation(edit)
        Intent intent = getIntent();
        id = intent.getIntExtra(DATA_ID, -1);

        serializable = (SerializableSentence)
                intent.getSerializableExtra(SentencesFragment.SERIALIZABLE_SENTENCE);

        if(intent.hasExtra(DATA_ID)) // it is edit
        {
            setTitle(R.string.edit_sentence);
            titleEditText.setText(serializable.getTitle());
            contentEditText.setText(serializable.getContent());
        }
        else
            setTitle(R.string.add_sentence);
    }

    public void saveSentence()
    {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        if(title.trim().isEmpty() || content.trim().isEmpty())
        {
            Toast.makeText(this, R.string.empty_sentence_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isDefault;
        if(serializable == null)
            isDefault = false;
        else
            isDefault = serializable.getIsDefault();

        SerializableSentence sSerializable =
                new SerializableSentence(content, title, isDefault);

        if(id != -1) // already has id ==> it is edit not add new sentence
            sSerializable.setId(id);

        Intent data = new Intent();
        data.putExtra(SentencesFragment.SERIALIZABLE_SENTENCE, sSerializable);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.done_cancel_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.done_action) {
            saveSentence();
            return true;
        }
        else {
            finish();
            return super.onOptionsItemSelected(item);
        }
    }
}