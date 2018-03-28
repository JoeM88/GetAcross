package com.example.josephmolina.getacross.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by josephmolina on 3/12/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SavedChat.db";
    private static final String SOL_CREATE_TABLE =
            "CREATE TABLE " + ChatContract.ChatEntry.TABLE_NAME + " (" +
                    ChatContract.ChatEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ChatContract.ChatEntry.TITLE + " TEXT," +
                    ChatContract.ChatEntry.ORIGINAL_TEXT + " TEXT," +
                    ChatContract.ChatEntry.TRANSLATION + " TEXT," +
                    ChatContract.ChatEntry.DATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    private static final String SOL_DELETE_TABLE = "DROP TABLE IF EXISTS " +
            ChatContract.ChatEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SOL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL(SOL_DELETE_TABLE);
     onCreate(db);
    }

    public static void addChat( String inputtedText, String translation, String title, SQLiteDatabase db) {
        ContentValues contentValues =  new ContentValues();

        contentValues.put(ChatContract.ChatEntry.TITLE, title);
        contentValues.put(ChatContract.ChatEntry.ORIGINAL_TEXT, inputtedText);
        contentValues.put(ChatContract.ChatEntry.TRANSLATION, translation);

        db.insert(ChatContract.ChatEntry.TABLE_NAME, null, contentValues);
        Log.d("AddChat", "New chat was added");
    }
}

