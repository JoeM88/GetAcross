package com.example.josephmolina.getacross.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by josephmolina on 3/28/18.
 */

@Entity(tableName = "chats", indices = {@Index(value = {"id"})})
public class Chat {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "originalText")
    private String originalText;

    @ColumnInfo(name = "translationText")
    private String translationText;


    public Chat(String title, String originalText, String translationText) {
        id = 0;
        this.title = title;
        this.originalText = originalText;
        this.translationText = translationText;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getTranslationText() {
        return translationText;
    }

    public void setId(int id) {
        this.id = id;
    }
}
