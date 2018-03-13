package com.example.josephmolina.getacross.Database;

import android.provider.BaseColumns;

/**
 * Created by josephmolina on 3/12/18.
 */

public class ChatContract {

    private ChatContract() {}

    public static class ChatEntry implements BaseColumns {

        public static final String TABLE_NAME = "SAVED_CHAT_TABLE";
        public static final String ID = "ID";
        public static final String TITLE = "TITLE";
        public static final String ORIGINAL_TEXT = "ORIGINAL_TEXT";
        public static final String TRANSLATION = "TRANSLATION_TEXT";
        public static final String DATE_TIME = "DATE_TIME";
    }
}
