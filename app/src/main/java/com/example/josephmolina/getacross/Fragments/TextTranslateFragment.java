package com.example.josephmolina.getacross.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.josephmolina.getacross.Models.TextToSpeechManager;
import com.example.josephmolina.getacross.R;
import com.example.josephmolina.getacross.YandexAPI;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TextTranslateFragment extends Fragment {

    @BindView(R.id.textToTranslateEditText)
    EditText userEnteredText;
    @BindView(R.id.translatedTextResults)
    TextView translatedText;

    private Unbinder unbinder;
    private TextToSpeechManager textToSpeechManager = null;

    public TextTranslateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_translate_text, container, false);
        unbinder = ButterKnife.bind(this, view);
        startTextToSpeechManager(view);
        setupTextWatcher();

        return view;
    }

    private void setupTextWatcher() {
        RxTextView.textChanges(userEnteredText)
                .filter(charSequence -> charSequence.length() >= 2)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(charSequence -> {
                    String textToTranslate = userEnteredText.getText().toString();
                    YandexAPI.detectLanguage(textToTranslate, this::createLanguagePair);
                });
    }

    private void createLanguagePair(String detectedLanguage) {
        String languagePair = YandexAPI.determineLanguageToTranslateTo(detectedLanguage, getActivity());
        translateText(languagePair, userEnteredText.getText().toString());
    }

    private void translateText(String languagePair, String text) {
        YandexAPI.translateTextAPICall(text, languagePair, this::displayTranslatedText);
    }

    private void displayTranslatedText(String text) {
       getActivity().runOnUiThread(() -> translatedText.setText(text));
    }

    private void startTextToSpeechManager(View view) {
        textToSpeechManager = new TextToSpeechManager();
        textToSpeechManager.startTextToSpeechManager(view.getContext());
    }

    public String getTranslatedText(){
        return translatedText.getText().toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textToSpeechManager.shutDown();
        unbinder.unbind();
    }
}
