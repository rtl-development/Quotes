package com.example.root.quotes.data_model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyMotivationDao
{

    // INSERT --------------------------------------------
    // must avoid duplicate:
    // https://www.google.com/search?q=Handling+Duplicates+in+room+database+android&ie=utf-8&oe=utf-8&client=firefox-b-ab
    // https://www.tutorialspoint.com/mysql/mysql-handling-duplicates.htm

    @Insert
    void insertSentence(Sentence sentence);

    @Insert
    void insertDefSentences(List<Sentence> sentences);


    // QUERY --------------------------------------------

    @Query("SELECT sentenceId FROM Sentence")
    List<Integer> getAllSentencesIds();

    @Query("SELECT * FROM Sentence ORDER BY sentenceId DESC")
    LiveData<List<Sentence>> getAllSentences();

    @Query("SELECT sentenceContent, sentenceTitle, sentenceId, isDefaultMotivation FROM Sentence ORDER BY RANDOM() LIMIT 1")
    Sentence getRandomSentence();

    @Query("SELECT COUNT(sentenceContent) FROM Sentence")
    Integer getSentencesCount();

    @Query("SELECT COUNT(sentenceContent) FROM Sentence WHERE isDefaultMotivation = 1")
    Integer getDfltSentencesCount();

    @Query("SELECT sentenceContent, sentenceTitle, sentenceId, isDefaultMotivation FROM Sentence WHERE sentenceId = :id")
    LiveData<Sentence> getLiveSentence(int id);

    @Query("DELETE FROM Sentence")
    void deleteAllSentences();

    @Query("DELETE FROM Sentence WHERE isDefaultMotivation = 1")
    void deleteDfltSentences();

    @Query("SELECT sentenceContent, sentenceTitle, sentenceId, isDefaultMotivation FROM Sentence WHERE sentenceId = :id")
    Sentence getSentenceById(int id);


    // DELETE --------------------------------------------
    @Delete
    void deleteSentence(Sentence... sentence);


    // UPDATE --------------------------------------------
    @Update
    void updateSentence(Sentence sentence);
}
