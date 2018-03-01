package com.example.josephmolina.getacross;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by josephmolina on 2/13/18.
 */

public class DetectLanguageBackgroundTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String textToBeTranslated = params[0];
        String jsonString = null;

        try {
            String yandexKey = BuildConfig.YANDEX_API_KEY;
            String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/detect?key=" + yandexKey
                    + "&text=" + textToBeTranslated;
            URL yandexTranslateURL = new URL(yandexUrl);

            HttpURLConnection httpJsonConnection = (HttpURLConnection) yandexTranslateURL.openConnection();
            InputStream inputStream = httpJsonConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder jsonStringBuilder = new StringBuilder();
            while ((jsonString = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(jsonString + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpJsonConnection.disconnect();
            String resultString = parseTranslatedString(jsonStringBuilder.toString().trim());

            return resultString;
            //jsonString = jsonStringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parseTranslatedString(String jsonStringBuilder) {

        String resultString = jsonStringBuilder.toString().trim();
        Log.d("result string", resultString);

//        resultString = resultString.substring(resultString.indexOf('[') + 1);
//        resultString = resultString.substring(0, resultString.indexOf("]"));
//
//        resultString = resultString.substring(resultString.indexOf("\"") + 1);
//        resultString = resultString.substring(0, resultString.indexOf("\""));

        return resultString;
    }
}
