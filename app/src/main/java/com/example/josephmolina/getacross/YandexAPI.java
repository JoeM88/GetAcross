package com.example.josephmolina.getacross;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by josephmolina on 2/1/18.
 */

public class YandexAPI {
    private static String languageDetectionURL = "https://translate.yandex.net/api/v1.5/tr.json/detect?key=";

    private static String request(String URL) throws IOException {
        java.net.URL url = new URL(URL);
        URLConnection urlConn = url.openConnection();

        InputStream inStream = urlConn.getInputStream();
        String received = new BufferedReader(new InputStreamReader(inStream)).readLine();

        inStream.close();
        return received;
    }

    public static String detectLanguage(String text) throws IOException {
        String response = request(generateURL(text));
        return response.substring(response.indexOf("lang") + 7, response.length() - 2);
    }

    private static String generateURL(String text) {
        return languageDetectionURL + BuildConfig.YANDEX_API_TOKEN + "&text=" + text;
    }
}
