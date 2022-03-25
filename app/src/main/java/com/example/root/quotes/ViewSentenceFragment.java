package com.example.root.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.root.quotes.data_model.Sentence;

public class ViewSentenceFragment extends Fragment
{
    TextView sentenceTextView;
    TextView titleTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_sentence_fragment, container, false);

        sentenceTextView = view.findViewById(R.id.sentence_content_text_view);
        titleTextView = view.findViewById(R.id.sentence_title_text_view);

        // either from onclick sentence item or notification or widget
        Intent intent = requireActivity().getIntent();

        if(intent != null)
        {
            SerializableSentence serializable = (SerializableSentence)
                    intent.getSerializableExtra(SentencesFragment.SERIALIZABLE_SENTENCE);

            Sentence s = new Sentence(serializable.getContent(), serializable.getTitle(), serializable.getIsDefault());
            s.setSentenceId(serializable.getId());

            setContent(s);

            // this intent came from notification
            if(intent.getFlags() == Intent.FLAG_ACTIVITY_CLEAR_TOP)
            {
                // dismiss notification msg
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireActivity());
                notificationManager.cancel(NotificationReceiver.NOTIFICATION_ID);
            }
        }

        return view;
    }

    protected void setContent(Sentence sentence)
    {
        if(sentence != null)
        {
            sentenceTextView.setText(sentence.getSentenceContent());
            titleTextView.setText(sentence.getSentenceTitle());
        }
    }

    /*protected void updateTextViews(String content, String title)
    {
        sentenceTextView.setText(content);
        titleTextView.setText(title);
    }*/
}