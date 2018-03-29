package com.example.josephmolina.getacross.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by josephmolina on 3/28/18.
 */

@Database(entities = {Chat.class}, version = 1)
public abstract class ChatDatabase extends RoomDatabase {
    private static ChatDatabase chatDatabaseInstance;

    public abstract ChatDao chatDao();

    public static ChatDatabase getChatDatabaseInstance(Context context) {
        if (chatDatabaseInstance == null) {
            chatDatabaseInstance = Room.databaseBuilder(context, ChatDatabase.class, "chatdb")
                    .allowMainThreadQueries().build();
        }
        return chatDatabaseInstance;
    }
}
