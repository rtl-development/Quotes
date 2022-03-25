package com.example.root.quotes;

import java.io.Serializable;

public class SerializableSentence implements Serializable
{
    private final String content;
    private final String title;
    private final boolean isDefault;
    private int id;

    public SerializableSentence(String content, String title, boolean isDefault)
    {
        this.content = content;
        this.title = title;
        this.isDefault = isDefault;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public int getId() {
        return id;
    }
}