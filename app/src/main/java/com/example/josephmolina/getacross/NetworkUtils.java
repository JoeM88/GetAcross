package com.example.josephmolina.getacross;

import okhttp3.OkHttpClient;

/**
 * Created by josephmolina on 2/16/18.
 */

public class NetworkUtils {
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
