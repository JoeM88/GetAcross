package com.example.josephmolina.getacross.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.josephmolina.getacross.R;
import com.example.josephmolina.getacross.TextToSpeechManager;
import com.example.josephmolina.getacross.TranslatorBackgroundTask;

import java.util.concurrent.ExecutionException;

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
    @BindView(R.id.translatedText)
    EditText translatedText;
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

    public void initializeTextToSpeechManager(View view) {
        textToSpeechManager = new TextToSpeechManager();
        textToSpeechManager.initialize(view.getContext());
    }

    @OnClick(R.id.translateButton)
    public void onSpeak() {
        test();
//        if (!textToTranslate.getText().toString().isEmpty()) {
//            textToSpeechManager.initQueue(textToTranslate.getText().toString());
//        } else {
//            Toast.makeText(getActivity(), R.string.empty_translate_text_input, Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textToSpeechManager.shutDown();
    }

    public void test(){
        String testString = textToTranslate.getText().toString();
        String languagePair = "en-es";
        Translate(testString, languagePair);
    }

    private void Translate(String textToTranslate, String languagePair) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            TranslatorBackgroundTask translatorBackgroundTask = new TranslatorBackgroundTask(getContext());
            String translationResult = null;
            try {
                translationResult = translatorBackgroundTask.execute(textToTranslate, languagePair).get();
                translatedText.setText(translationResult);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //Log.d("translate", translationResult);
        }
    }



}
