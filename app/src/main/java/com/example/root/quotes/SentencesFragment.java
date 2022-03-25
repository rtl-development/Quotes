package com.example.root.quotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.quotes.data_model.Sentence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.root.quotes.SettingsFragment.ADD_DEFAULT_QUOTES_KEY;

public class SentencesFragment extends Fragment {

    protected static final String SERIALIZABLE_SENTENCE = "SentenceParcelable";
    private boolean isMultiSelect = false;
    private int selectAllState = 0;

    private ActionMode actionMode;
    private ArrayList<Integer> selectedIds;
    private ArrayList<Sentence> selectedSentences;
    private SentenceViewModel sentenceViewModel;
    private SentenceAdapter sentenceAdapter;
    private Button defaultSentenceBtn;
    private TextView noSentences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sentences, container, false);

        sentenceAdapter = new SentenceAdapter(getActivity());

        RecyclerView recyclerView = view.findViewById(R.id.sentences_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true); // sizes of all items are fixed
        recyclerView.setAdapter(sentenceAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration
                (requireActivity(), DividerItemDecoration.VERTICAL));

        recyclerView.addOnItemTouchListener(new MyRVOnItemTouchListener(getActivity(),
                recyclerView, new MyRVOnItemTouchListener.OnRVItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect)
                    multiSelect(position);
                else {
                    openViewSentenceActivity(getActivity(), position);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if (!isMultiSelect) {
                    selectedIds = new ArrayList<>();
                    selectedSentences = new ArrayList<>();
                    isMultiSelect = true;

                    if (actionMode == null)
                        actionMode = ((AppCompatActivity) requireActivity()).startSupportActionMode(actionModeCallback);
                }

                multiSelect(position);
            }
        }));

        sentenceViewModel = new ViewModelProvider(requireActivity()).get(SentenceViewModel.class);
        sentenceViewModel.getAllSentences().observe(getViewLifecycleOwner(), sentenceObserver);

        FloatingActionButton fabAddSentence = view.findViewById(R.id.fab_add_sentence);
        fabAddSentence.setOnClickListener(fabListener);

        defaultSentenceBtn = view.findViewById(R.id.add_def_sentences_btn);
        noSentences = view.findViewById(R.id.no_sentences);

        //int sentencesCount = sentenceViewModel.getSentencesCount();

        if (sentenceViewModel.getSentencesCount() == 0)
            setDfltBtnVisibility(View.VISIBLE);
        else
            setDfltBtnVisibility(View.GONE);

        return view;
    }

    View.OnClickListener fabListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AddEditSentenceActivity.class);
            activityForResult.launch(intent);
        }
    };

    ActivityResultLauncher<Intent> activityForResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                    , new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK)
                                addingTheNewSentence(result.getData());
                        }
                    });

    private void openViewSentenceActivity(Context context, int position)
    {
        Sentence sentence = sentenceAdapter.getSentenceAt(position);

        Intent data = new Intent(context, ViewMotivation.class);

        SerializableSentence serializable = new SerializableSentence
                (sentence.getSentenceContent(), sentence.getSentenceTitle(),
                sentence.getIsDefaultMotivation());
        serializable.setId(sentence.getSentenceId());

        data.putExtra(SERIALIZABLE_SENTENCE, serializable);

        startActivity(data);
    }

    private void multiSelect(int position) {
        Sentence sentence = sentenceAdapter.getSentenceAt(position);

        if (sentence != null)
            if (actionMode != null)
                if (selectedIds.contains(sentence.getSentenceId())) {
                    selectedIds.remove(Integer.valueOf(sentence.getSentenceId()));
                    selectedSentences.remove(sentence);
                } else {
                    selectedIds.add(sentence.getSentenceId());
                    selectedSentences.add(sentence);
                }

        if (actionMode != null) {
            if (selectedIds.size() > 0)
                actionMode.setTitle(String.valueOf(selectedIds.size()));
            else {
                actionMode.setTitle("");
                actionMode.finish();
            }
        }

        sentenceAdapter.setSelectedIds(selectedIds);
    }

    private void deleteSelectedSentences(final ActionMode mode) {
        new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.delete_alert_title)
                .setMessage(R.string.delete_sentence_item)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (selectedSentences.size() == 1)
                            sentenceViewModel.deleteSentence((selectedSentences.get(0)));
                        else
                            sentenceViewModel.deleteSentence(selectedSentences.toArray(new Sentence[selectedSentences.size()]));

                        mode.finish();
                        Toast.makeText(getActivity(), R.string.delete_sentence_msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel_delete, null)
                .show();
    }


    public ActionMode.Callback actionModeCallback =
            new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.selectall_delete_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.delete) {
                        if (selectedIds.size() >= 1) {
                            deleteSelectedSentences(mode);
                            return true;
                        }

                        return true;

                    } else if (itemId == R.id.select_all) {
                        if (sentenceViewModel.getSentencesCount() != 0) {
                            if (selectAllState == 0 || selectAllState == 2) {
                                selectAllState = 1;
                                sentenceAdapter.setSelectAll();
                                actionMode.setTitle(String.valueOf(selectedIds.size()));
                                selectedSentences.addAll(sentenceAdapter.getSentences());
                                return true;
                            } else if (selectAllState == 1) {
                                selectAllState = 2;
                                sentenceAdapter.unSelectAll();
                                actionMode.setTitle("");
                                selectedSentences.clear();
                                mode.finish();
                                return true;
                            }
                        }

                        return false;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    actionMode = null;
                    isMultiSelect = false;
                    selectedIds = new ArrayList<>();
                    sentenceAdapter.setSelectedIds(new ArrayList<>());
                }
            };


    private void addingTheNewSentence(Intent data)
    {
        if (data != null)
        {
            SerializableSentence serializable =
                    (SerializableSentence) data.getSerializableExtra(SERIALIZABLE_SENTENCE);

            Sentence sentence = new Sentence(serializable.getContent(),
                    serializable.getTitle(), serializable.getIsDefault());

            sentenceViewModel.insertSentence(sentence);
            Toast.makeText(getActivity(), R.string.sentence_saved_msg, Toast.LENGTH_SHORT).show();
        }
    }

    protected SentenceAdapter getSentenceAdapter() {
        return sentenceAdapter;
    }

    private final Observer<List<Sentence>> sentenceObserver = new Observer<List<Sentence>>() {
        @Override
        public void onChanged(@Nullable List<Sentence> sentences) {
            sentenceAdapter.setSentences(sentences);

            if (sentenceViewModel.getDfltSentencesCount() == 0) {
                SharedPreferences.Editor editor =
                        PreferenceManager.getDefaultSharedPreferences(requireActivity()).edit();
                editor.putBoolean(ADD_DEFAULT_QUOTES_KEY, false);
                editor.apply();
            }
        }
    };


    private void setDfltBtnVisibility(int visibility)
    {
        if (visibility == View.VISIBLE) {
            noSentences.setVisibility(View.VISIBLE);
            defaultSentenceBtn.setVisibility(View.VISIBLE);
            defaultSentenceBtn.setOnClickListener(btnListener);
        } else {
            noSentences.setVisibility(View.GONE);
            defaultSentenceBtn.setVisibility(View.GONE);
            defaultSentenceBtn.setOnClickListener(null);
        }
    }

    private final View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sentenceViewModel.insertDefSentences(getActivity());

            SharedPreferences.Editor editor =
                    PreferenceManager.getDefaultSharedPreferences(requireActivity()).edit();
            editor.putBoolean(ADD_DEFAULT_QUOTES_KEY, true);
            editor.apply();

            setDfltBtnVisibility(View.GONE);
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        if (sentenceViewModel.getSentencesCount() == 0)
            setDfltBtnVisibility(View.VISIBLE);
        else
            setDfltBtnVisibility(View.GONE);
    }
}