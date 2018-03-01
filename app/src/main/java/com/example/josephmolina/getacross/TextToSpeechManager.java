package com.example.josephmolina.getacross;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by josephmolina on 1/9/18.
 */

public class TextToSpeechManager {

    private TextToSpeech textToSpeechManager = null;
    private boolean isInitialized = false;
    private Context context = null;

    public void startTextToSpeechManager(Context context) {
        this.context = context;
        textToSpeechManager = new TextToSpeech(context, onInitListener);
    }

    // To be notified of when the textToSpeechManager instance has been initialized.
    private TextToSpeech.OnInitListener onInitListener = status -> {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeechManager.setLanguage(Locale.ENGLISH);
            isInitialized = true;

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, context.getString(R.string.unsupported_language_error),
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(context, context.getString(R.string.textToSpeech_initialization_failed_error),
                    Toast.LENGTH_SHORT).show();
        }
    };

    public void shutDown() {
        textToSpeechManager.shutdown();
    }

    public void startSpeakingText(String text) {
        if (isInitialized) {
            textToSpeechManager.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            Toast.makeText(context, context.getString(R.string.textToSpeech_not_initialized_error),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
