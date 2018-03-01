package com.example.josephmolina.getacross;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.josephmolina.getacross.GsonUtils.GsonUtils;
import com.example.josephmolina.getacross.Models.YandexResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by josephmolina on 2/1/18.
 */

public class YandexAPI {
    private final static String YANDEX_TRANSLATE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    private final static String TRANSLATION_KEY_PARAMETER = "translate?key=";
    private final static String DETECTION_KEY_PARAMETER = "detect?key=";
    private final static String TEXT_QUERY_PARAMETER = "&text=";
    private final static String LANGUAGE_QUERY_PARAMETER = "&lang=";

    private final static OkHttpClient client = NetworkUtils.getOkHttpClient();
    private final static Gson gson = GsonUtils.getGsonInstance();

    public static void makeTranslateTextAPICall(String textToTranslate, String languagePair, YandexAPICallback yandexAPICallback) {
        String url = buildYandexTranslateUrl(textToTranslate,languagePair);

        Request request = buildYandexRequest(url.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("onFailure", "failure reached");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String apiResponse = response.body().string();
                YandexResponse yandexResponse = gson.fromJson(apiResponse, YandexResponse.class);
                yandexAPICallback.onResponseReceived(String.valueOf(yandexResponse.getText()));
            }
        });
    }

    private static String buildYandexTranslateUrl(String textToTranslate, String languagePair) {
        StringBuilder url = new StringBuilder(YANDEX_TRANSLATE_URL);
        url.append(TRANSLATION_KEY_PARAMETER);
        url.append(BuildConfig.YANDEX_API_KEY);
        url.append(TEXT_QUERY_PARAMETER);
        url.append(textToTranslate);
        url.append(LANGUAGE_QUERY_PARAMETER );
        url.append(languagePair);

        return url.toString();
    }

    private static Request buildYandexRequest(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }
}
