package com.example.josephmolina.getacross;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.josephmolina.getacross.Utils.Utility.GsonUtils;
import com.example.josephmolina.getacross.Models.DetectLanguageResponse;
import com.example.josephmolina.getacross.Models.YandexResponse;
import com.example.josephmolina.getacross.Utils.Utility.NetworkUtils;
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
    private final static String YANDEX_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    private final static String TRANSLATION_KEY_PARAMETER = "translate?key=";
    private final static String DETECTION_KEY_PARAMETER = "detect?key=";
    private final static String TEXT_QUERY_PARAMETER = "&text=";
    private final static String LANGUAGE_QUERY_PARAMETER = "&lang=";

    private final static OkHttpClient client = NetworkUtils.getOkHttpClient();
    private final static Gson gson = GsonUtils.getGsonInstance();

    public static void translateTextAPICall(String textToTranslate, String languagePair,
                                            YandexAPICallback yandexAPICallback) {
        String url = buildYandexTranslateUrl(textToTranslate, languagePair);
        Request request = buildYandexRequest(url);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("onFailure", "failure reached");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String bodyOfResponse = response.body().string();
                YandexResponse yandexResponse = gson.fromJson(bodyOfResponse, YandexResponse.class);
                String text = String.valueOf(yandexResponse.getText());
                yandexAPICallback.onResponseReceived(text);
            }
        });
    }

    public static void detectLanguageAPICall(String text, YandexAPICallback yandexAPICallback) {
        String url = createYandexDetectionUrl(text);
        Request request = buildYandexRequest(url);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure", "failure reached");
                Log.d("onFailure--->", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String apiResponse = response.body().string();
                DetectLanguageResponse yandexResponse = gson.fromJson(apiResponse, DetectLanguageResponse.class);
                String detectedLanguage = String.valueOf(yandexResponse.getLang());
                yandexAPICallback.onResponseReceived(detectedLanguage);
            }
        });
    }


    private static String buildYandexTranslateUrl(String textToTranslate, String languagePair) {
        return new StringBuilder().append(YANDEX_BASE_URL).
                append(TRANSLATION_KEY_PARAMETER).
                append(BuildConfig.YANDEX_API_KEY).
                append(TEXT_QUERY_PARAMETER).
                append(textToTranslate).
                append(LANGUAGE_QUERY_PARAMETER).
                append(languagePair).toString();
    }

    private static Request buildYandexRequest(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }

    private static String createYandexDetectionUrl(String inputtedText) {
        return new StringBuilder().
                append(YANDEX_BASE_URL).
                append(DETECTION_KEY_PARAMETER).
                append(BuildConfig.YANDEX_API_KEY).
                append(TEXT_QUERY_PARAMETER).
                append(inputtedText).toString();
    }
}
