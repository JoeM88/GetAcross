package com.example.josephmolina.getacross.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.josephmolina.getacross.R;
import com.example.josephmolina.getacross.TextToSpeechManager;
import com.example.josephmolina.getacross.YandexAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TextTranslateFragment extends Fragment {

    @BindView(R.id.textToTranslateEditText)
    EditText textToBeTranslated;
    @BindView(R.id.translatedTextResults)
    TextView translatedText;

    private Unbinder unbinder;
    private TextToSpeechManager textToSpeechManager = null;
    private final Handler handler = new Handler();
    private Runnable workRunnable;
    private final String english = "en";
    private final String spanish = "es";

    private final String INPUTTED_TEXT_KEY = "textToBeTranslated";
    private final String TRANSLATED_TEXT = "translatedText";


    public TextTranslateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_translate_text, container, false);
        unbinder = ButterKnife.bind(this, view);

        startTextToSpeechManager(view);
        textToBeTranslated.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(workRunnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                workRunnable = () -> {
                    if (textToBeTranslated.getText().length() >= 1) {
                        String inputtedText = textToBeTranslated.getText().toString();
                        YandexAPI.detectLanguageAPICall(inputtedText, response -> {
                            String languagePair = determineLanguageToTranslateTo(response);
                            translateText(languagePair);
                        });
                    }
                };
                handler.postDelayed(workRunnable, 500);
            }
        });
        return view;
    }

    private String determineLanguageToTranslateTo(String languageDetected) {
        String translationLanguage = null;

        if (languageDetected.equals(english)) {
            translationLanguage = spanish;
        } else if (languageDetected.equals(spanish)) {
            translationLanguage = english;
        }

        return languageDetected + "-" +
                translationLanguage;
    }

    private void translateText(String languagePair) {
        YandexAPI.translateTextAPICall(textToBeTranslated.getText().toString(), languagePair,
                response -> {
                    getActivity().runOnUiThread(() -> translatedText.setText(response));
                });
    }

    private void startTextToSpeechManager(View view) {
        textToSpeechManager = new TextToSpeechManager();
        textToSpeechManager.startTextToSpeechManager(view.getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textToSpeechManager.shutDown();
    }
}
