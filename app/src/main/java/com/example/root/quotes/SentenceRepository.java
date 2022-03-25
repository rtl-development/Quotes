package com.example.root.quotes;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.root.quotes.data_model.MyMotivationDB;
import com.example.root.quotes.data_model.MyMotivationDao;
import com.example.root.quotes.data_model.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SentenceRepository {

    private final MyMotivationDao dao;
    private final LiveData<List<Sentence>> allSentences;

    // volatile: all the write will happen on dbOperationInstance before any read of the variable.
    private static volatile SentenceRepository sentenceRepository;

    private SentenceRepository(Application application) {
        //Prevent form the reflection api.
        if (sentenceRepository != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");

        MyMotivationDB motivationDB = MyMotivationDB.getMyMotivationDB(application);

        dao = motivationDB.myMotivationDao();

        allSentences = dao.getAllSentences();
    }

    public static SentenceRepository getInstance(Application application) {
        //Double check locking pattern:
        if (sentenceRepository == null)
            synchronized (SentenceRepository.class) //synchronized to be safe thread
            {
                if (sentenceRepository == null)
                    sentenceRepository = new SentenceRepository(application);
            }
        return sentenceRepository;
    }

    public void insertSentence(Sentence sentence) {
        new ConcurrencyTasks().insertSentence(dao,sentence);
    }

    public void insertDefSentences(Context context)
    {
        new ConcurrencyTasks().insertDefSentences(dao, context);
    }

    public void updateSentence(Sentence sentence) {
        new ConcurrencyTasks().updateSentence(dao,sentence);
    }

    public void deleteSentence(Sentence... sentence) {
        new ConcurrencyTasks().deleteSentence(dao, sentence);
    }

    public void deleteDfltSentences() {
        new ConcurrencyTasks().deleteDfltSentences(dao);
    }

    public LiveData<List<Sentence>> getAllSentences() {
        return allSentences;
    }

    public Integer getSentencesCount() {
        return (new ConcurrencyTasks().getSentencesCount(dao));
    }

    public Integer getDfltSentencesCount() {
        return (new ConcurrencyTasks().getDfltSentencesCount(dao));
    }

    public List<Integer> getAllSentencesIds() {
        return (new ConcurrencyTasks().getAllSentencesIds(dao));
    }

    public Sentence getRandomSentenceObj() {
        return (new ConcurrencyTasks().getRandomSentenceObj(dao));
    }

    public Sentence getSentenceById(int id)
    {
        return (new ConcurrencyTasks().getSentenceById(id, dao));
    }

    //---------------------------- Concurrency Tasks ----------------------------

    private static class ConcurrencyTasks
    {
        private final ExecutorService executorService =
                Executors.newSingleThreadExecutor();
        // Handler handler = new Handler(Looper.getMainLooper());

        public void insertSentence(MyMotivationDao motDao, Sentence s)
        {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    // Background work here
                    motDao.insertSentence(s);

                /*handler.post(() -> {
                   // UI Thread work here
                });*/
                }
            });
        }

        public void insertDefSentences(MyMotivationDao motDao, Context context)
        {
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    List<Sentence> sentences = new ArrayList<>();

                    String[] titlesArray = context.getResources().getStringArray(R.array.m_sentences_titles);
                    String[] contentsArray = context.getResources().getStringArray(R.array.m_sentences);

                    int i = 0;

                    while (i < contentsArray.length)
                    {
                        sentences.add(i, new Sentence(contentsArray[i], titlesArray[i], true));
                        i++;
                    }

                    motDao.insertDefSentences(sentences);
                }
            });
        }

        public void updateSentence(MyMotivationDao motDao, Sentence s)
        {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    motDao.updateSentence(s);
                }
            });
        }

        public void deleteSentence(MyMotivationDao motDao, Sentence... s)
        {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    motDao.deleteSentence(s);
                }
            });
        }

        public void deleteDfltSentences(MyMotivationDao motDao)
        {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    motDao.deleteDfltSentences();
                }
            });
        }

        public Integer getSentencesCount(MyMotivationDao motDao)
        {
            Future<Integer> result;
            result = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() {
                    return motDao.getSentencesCount();
                }
            });

            try {
                return result.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }

        public Integer getDfltSentencesCount(MyMotivationDao motDao)
        {
            Future<Integer> result = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() {
                    return motDao.getDfltSentencesCount();
                }
            });

            try
            {
                return result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            } catch (ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }

        public List<Integer> getAllSentencesIds(MyMotivationDao motDao)
        {
            Future<List<Integer>> result =
                    executorService.submit(new Callable<List<Integer>>() {
                @Override
                public List<Integer> call() {
                    return new ArrayList<>(motDao.getAllSentencesIds());
                }
            });

            try
            {
                return result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            } catch (ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }

        public Sentence getRandomSentenceObj(MyMotivationDao motDao)
        {
            Future<Sentence> result =
                    executorService.submit(new Callable<Sentence>() {
                @Override
                public Sentence call()
                {
                    if(motDao.getSentencesCount() > 0)
                        return motDao.getRandomSentence();

                    return null;
                }
            });

            try
            {
                return result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            } catch (ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }

        private Sentence getSentenceById(int id, MyMotivationDao motDao)
        {
            Future<Sentence> result = executorService.submit(new Callable<Sentence>() {
                @Override
                public Sentence call() {
                    return motDao.getSentenceById(id);
                }
            });

            try
            {
                return result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            } catch (ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
