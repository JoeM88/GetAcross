package com.example.josephmolina.getacross.GsonUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by josephmolina on 2/26/18.
 */

public class GsonUtils {
    private static final Gson gson = new GsonBuilder().create();

    public static Gson getGsonInstance() {
        return gson;
    }

}
