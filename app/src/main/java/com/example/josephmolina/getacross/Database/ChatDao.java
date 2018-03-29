package com.example.josephmolina.getacross.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by josephmolina on 3/28/18.
 */
@Dao
public interface ChatDao {

    @Insert
    void addChat(Chat chat);

    @Query("SELECT * FROM chats")
    List<Chat> getChats();

}
