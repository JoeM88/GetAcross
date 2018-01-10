package com.example.josephmolina.getacross;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextTranslateFragment extends Fragment {

    @BindView(R.id.textToTranslate)
    EditText textToTranslate;
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
        return view;
    }

    @OnClick(R.id.speakButton)
    public void onSpeak() {
        if (!textToTranslate.getText().toString().isEmpty()) {
            textToSpeechManager.initQueue(textToTranslate.getText().toString());
        } else {
            Toast.makeText(getActivity(), R.string.empty_translate_text_input, Toast.LENGTH_SHORT).show();
        }
    }

    public void initializeTextToSpeechManager(View view) {
        textToSpeechManager = new TextToSpeechManager();
        textToSpeechManager.initialize(view.getContext());
    }
}
