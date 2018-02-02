package com.example.josephmolina.getacross;

/**
 * Created by josephmolina on 1/31/18.
 */

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by DoguD on 01/07/2017.
 */

public class TranslatorBackgroundTask extends AsyncTask<String, Void, String> {
    public TranslatorBackgroundTask(Context cntxt) {
        Context context = cntxt;
    }

    @Override
    protected String doInBackground(String... params) {
        String textToBeTranslated = params[0];
        String languagePair = params[1];
        String jsonString;

        try {
            String yandexKey = BuildConfig.YANDEX_API_TOKEN;
            String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + yandexKey
                    + "&text=" + textToBeTranslated + "&lang=" + languagePair;
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parseTranslatedString(String jsonStringBuilder) {

        String resultString = jsonStringBuilder.toString().trim();

        resultString = resultString.substring(resultString.indexOf('[') + 1);
        resultString = resultString.substring(0, resultString.indexOf("]"));

        resultString = resultString.substring(resultString.indexOf("\"") + 1);
        resultString = resultString.substring(0, resultString.indexOf("\""));

        return resultString;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private static String request(String URL) throws IOException {
        URL url = new URL(URL);
        URLConnection urlConn = url.openConnection();
        urlConn.addRequestProperty("User-Agent", "Mozilla");

        InputStream inStream = urlConn.getInputStream();

        String recieved = new BufferedReader(new InputStreamReader(inStream)).readLine();

        inStream.close();
        return recieved;
    }

    public static String detectLanguage(String text) throws IOException {
        String response = request("https://translate.yandex.net/api/v1.5/tr.json/detect?key=" + BuildConfig.YANDEX_API_TOKEN + "&text=" + text);
        return response.substring(response.indexOf("lang") + 7, response.length() - 2);

    }
}