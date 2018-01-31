package com.example.josephmolina.getacross.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.josephmolina.getacross.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class VoiceTranslateFragment extends Fragment {

    @BindView(R.id.voice_recorder)
    Button voiceRecorder;
    @BindView(R.id.translatedSpeechText)
    TextView translatedSpeechText;

    private Unbinder unbinder;
    private final int VOICE_TRANSLATE_REQUESTCODE = 1;

    public VoiceTranslateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_translate, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.voice_recorder)
    public void onVoiceRecorder() {
        startSpeechRecording();
    }

    private void startSpeechRecording() {
        Intent speechRecognizerIntent = createSpeechRecognizerIntent();
        startActivityForResult(speechRecognizerIntent, VOICE_TRANSLATE_REQUESTCODE);
    }

    private Intent createSpeechRecognizerIntent() {
        Intent recognizeIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizeIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.voice_recorder_prompt));
        return recognizeIntent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data) {
            displayTranslatedSpeechToText(requestCode, data);
        }
    }

    private void displayTranslatedSpeechToText(int requestCode, Intent data) {
        switch (requestCode) {
            case VOICE_TRANSLATE_REQUESTCODE: {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                translatedSpeechText.setText(result.get(0));
                break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
