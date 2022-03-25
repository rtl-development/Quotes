package com.example.root.quotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.quotes.data_model.Sentence;

import java.util.ArrayList;
import java.util.List;

public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.SentenceHolder>
        implements Filterable {

    // assign it to ArrayList to not be null initially
    private List<Sentence> sentences = new ArrayList<>();
    private List<Sentence> filteredSentences;
    private ActionMode actionMode;
    private AppCompatActivity actionModeContext;
    private Context context;

    private List<Integer> selectedIds = new ArrayList<>();

    //int sentenceIcon = R.drawable.ic_local_florist_black_24dp;

    public SentenceAdapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public SentenceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sentence_item, parent, false);

        return new SentenceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SentenceHolder holder, int position) {
        Sentence currentSentence = sentences.get(position);
        holder.textViewTitle.setText(currentSentence.getSentenceTitle());
        holder.textViewContent.setText(currentSentence.getSentenceContent());
        holder.imageView.setImageResource(R.drawable.ic_local_florist_black_24dp);

        //-----------
        int id = sentences.get(position).getSentenceId();
        if(selectedIds.contains(id))
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.activated_rv_item));
        else
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        //-----------
    }

    //-----------
    public void setSelectedIds(List<Integer> ids)
    {
        selectedIds = ids;
        notifyDataSetChanged();
    }

    public void setSelectAll()
    {
        int i = 0;
        selectedIds.clear();

        while(i<sentences.size())
        {
            selectedIds.add(sentences.get(i).getSentenceId());
            i++;
        }
        notifyDataSetChanged();
    }

    public void unSelectAll()
    {
        selectedIds.clear();
        notifyDataSetChanged();
    }
    //-----------

    @Override
    public int getItemCount() {
        return sentences.size();
    }

    public Sentence getSentenceAt(int position) {
        return sentences.get(position);
    }

    public List<Sentence> getSentences()
    {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
        filteredSentences = new ArrayList<>(sentences);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return sentencesFilter;
    }

    private Filter sentencesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Sentence> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0)
                filteredList.addAll(filteredSentences);
            else
                {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    for(Sentence sentence : filteredSentences)
                    {
                        if(sentence.getSentenceContent().toLowerCase().contains(filterPattern))
                            filteredList.add(sentence);
                    }
                }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults)
        {
            sentences.clear();
            sentences.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class SentenceHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewContent;
        private ImageView imageView;

        public SentenceHolder(final View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.sentence_title_item);
            textViewContent = itemView.findViewById(R.id.sentence_content_item);
            imageView = itemView.findViewById(R.id.sentence_icon_item);
        }
    }
}