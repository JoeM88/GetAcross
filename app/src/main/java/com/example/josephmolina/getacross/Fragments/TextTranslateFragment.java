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
import com.example.josephmolina.getacross.YandexAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;


public class TextTranslateFragment extends Fragment {

    @BindView(R.id.textToTranslateEditText)
    EditText textToBeTranslated;
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

        startTextToSpeechManager(view);
        //startTextWatcher();

        return view;
    }

    private void startTextToSpeechManager(View view) {
        textToSpeechManager = new TextToSpeechManager();
        textToSpeechManager.startTextToSpeechManager(view.getContext());
    }

    @OnTextChanged(R.id.textToTranslateEditText)
    public void onTextToTranslate() {

        YandexAPI.makeTranslateTextAPICall(textToBeTranslated.getText().toString(), "en-es",
                response -> getActivity().runOnUiThread(() -> translatedText.setText(response)));
    }

    @OnClick(R.id.mic_button)
    public void onSpeak() {
        if (!textToBeTranslated.getText().toString().isEmpty()) {
            textToSpeechManager.startSpeakingText(translatedText.getText().toString());
        } else {
            Toast.makeText(getActivity(), R.string.empty_translate_text_input, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textToSpeechManager.shutDown();
    }
}
