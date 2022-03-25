package com.example.root.quotes.data_model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sentence
{

    @PrimaryKey(autoGenerate = true)
    private int sentenceId;

    //@ColumnInfo (name = "sentence_item")
    private final String sentenceContent;

    //@ColumnInfo (name = "sentence_title")
    private final String sentenceTitle;

    //@ColumnInfo (name = "is_default_motivation")
    private final boolean isDefaultMotivation;


    public Sentence(String sentenceContent, String sentenceTitle, boolean isDefaultMotivation) {
        this.sentenceContent = sentenceContent;
        this.sentenceTitle = sentenceTitle;
        this.isDefaultMotivation = isDefaultMotivation;
    }

    public void setSentenceId(int sentenceId) {
        this.sentenceId = sentenceId;
    }

    public int getSentenceId() {
        return sentenceId;
    }

    public String getSentenceContent() {
        return sentenceContent;
    }

    public String getSentenceTitle() {
        return sentenceTitle;
    }

    public boolean getIsDefaultMotivation() {
        return isDefaultMotivation;
    }

    //--------------------parcelable functions-----------------------

    // reading data from parcel
    /*public Sentence(Parcel parcel) {
        sentenceContent = parcel.readString();
        sentenceTitle = parcel.readString();
        isDefaultMotivation = parcel.readByte() != 0; // true if byte != 0. And it is False by default.
    }

    public static final Creator<Sentence> CREATOR = new Creator<Sentence>() {
        @Override
        public Sentence createFromParcel(Parcel parcel) {
            return new Sentence(parcel);
        }

        @Override
        public Sentence[] newArray(int size) {
            return new Sentence[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    // writing data to parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // arrange of members in reading and writing operation must be same.
        parcel.writeString(sentenceContent);
        parcel.writeString(sentenceTitle);
        parcel.writeByte((byte) (isDefaultMotivation ? 1 : 0));
    }*/
}