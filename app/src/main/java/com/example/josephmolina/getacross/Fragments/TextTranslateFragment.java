package com.example.josephmolina.getacross.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josephmolina.getacross.R;
import com.example.josephmolina.getacross.TextToSpeechManager;
import com.example.josephmolina.getacross.TranslatorBackgroundTask;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextTranslateFragment extends Fragment {

    @BindView(R.id.textToTranslateEditText)
    EditText textToTranslate;
    @BindView(R.id.translatedTextResults)
    TextView translatedText;
    @BindView(R.id.initialLanguageSelection)
    Spinner initialLanguageSelection;
    @BindView(R.id.translateToLanguageSelection)
    Spinner translateToLanguageSelection;

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

        initializeTextToSpeechManager(view);
        initializeTextWatcher();

        return view;
    }

    private void initializeTextWatcher() {
        RxTextView.textChanges(textToTranslate)
                .debounce(1, TimeUnit.SECONDS)
                .subscribe(textChanged -> {
                    getActivity().runOnUiThread(() ->
                            translateAndDisplayText(textToTranslate.getText().toString(),
                                    "en-es"));
                });
    }

    public void initializeTextToSpeechManager(View view) {
        textToSpeechManager = new TextToSpeechManager();
        textToSpeechManager.initialize(view.getContext());
    }

    @OnClick(R.id.mic_button)
    public void onSpeak() {
        if (!textToTranslate.getText().toString().isEmpty()) {
            textToSpeechManager.initQueue(textToTranslate.getText().toString());
        } else {
            Toast.makeText(getActivity(), R.string.empty_translate_text_input, Toast.LENGTH_SHORT).show();
        }
    }

    private void translateAndDisplayText(String textToTranslate, String languagePair) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            TranslatorBackgroundTask translatorBackgroundTask = new TranslatorBackgroundTask(getContext());
            String translationResult = null;
            try {
                translationResult = translatorBackgroundTask.execute(textToTranslate, languagePair).get();
                translatedText.setText(translationResult);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textToSpeechManager.shutDown();
    }
}
