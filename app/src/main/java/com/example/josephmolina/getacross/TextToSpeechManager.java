package com.example.josephmolina.getacross;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by josephmolina on 1/9/18.
 */

public class TextToSpeechManager {

    private TextToSpeech textToSpeech = null;
    private boolean isInitialized = false;
    private Context context = null;

    public void initialize(Context context) {
        this.context = context;
        textToSpeech = new TextToSpeech(context, onInitListener);
    }

    // To be notified of when the textToSpeech instance has been initialized.
    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.ENGLISH);
                isInitialized = true;

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(context, context.getString(R.string.unsupported_language_error),
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, context.getString(R.string.textToSpeech_initialization_failed_error),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void shutDown() {
        textToSpeech.shutdown();
    }

    public void addQueue(String text) {
        speak(text, TextToSpeech.QUEUE_ADD);
    }

    public void initQueue(String text) {
        speak(text, TextToSpeech.QUEUE_FLUSH);
    }

    private void speak(String text, int queueMode) {
        if (isInitialized) {
            textToSpeech.speak(text, queueMode, null, null);
        } else {
            Toast.makeText(context, context.getString(R.string.textToSpeech_not_initialized_error),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
