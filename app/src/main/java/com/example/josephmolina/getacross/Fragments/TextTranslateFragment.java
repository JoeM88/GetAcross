package com.example.josephmolina.getacross.Fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josephmolina.getacross.Database.Chat;
import com.example.josephmolina.getacross.Database.ChatDatabase;
import com.example.josephmolina.getacross.MainActivity;
import com.example.josephmolina.getacross.Models.TextToSpeechManager;
import com.example.josephmolina.getacross.R;
import com.example.josephmolina.getacross.YandexAPI;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.josephmolina.getacross.MainActivity.chatDatabase;


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_translation:
                showSaveChatDialog();
                return true;

            case R.id.action_speak_translation:
                String text = translatedText.getText().toString();
                if (!text.isEmpty() && text.length() > 2) {
                    textToSpeechManager.startSpeakingText(translatedText.getText().toString());
                } else {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getActivity(), R.string.invalid_text_toast_message,
                                    Toast.LENGTH_SHORT).show());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showSaveChatDialog() {
        if (!textsAreEmpty()) {
             createDialog();
        } else {
            getActivity().runOnUiThread(() ->
                    Toast.makeText(getActivity(), R.string.empty_text_fields,
                            Toast.LENGTH_SHORT).show());
        }
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
        String languagePair = YandexAPI.determineLanguageToTranslateTo(detectedLanguage,
                getActivity());
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

    private boolean textsAreEmpty() {
        return userEnteredText.getText().toString().isEmpty() && translatedText.getText().toString().isEmpty();
    }

    private void saveChatTranslation(String chatTitle) {
        chatDatabase = ChatDatabase.getChatDatabaseInstance(getActivity());

        String title = "testing";
        String originalText = userEnteredText.getText().toString();
        String translationText = translatedText.getText().toString();

        Chat chat = new Chat(title, originalText, translationText);
        MainActivity.chatDatabase.chatDao().addChat(chat);
        Toast.makeText(getActivity(), "Chat saved", Toast.LENGTH_SHORT).show();
    }

    private void createDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View dialogLayout = layoutInflater.inflate(R.layout.save_chat_dialog_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.save_chat_dialog_title);
        builder.setCancelable(false);
        builder.setView(layoutInflater.inflate(R.layout.save_chat_dialog_layout, null));
        builder.setPositiveButton(R.string.save_chat_dialog_positiveButton,
                (dialogInterface, i) -> {
                    EditText titleEditText = dialogLayout.findViewById(R.id.chatTitle);
                    saveChatTranslation(titleEditText.getText().toString());
                });
        builder.setNegativeButton(R.string.chatDialog_negative_button, (dialogInterface, i) ->
                dialogInterface.dismiss());
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textToSpeechManager.shutDown();
        unbinder.unbind();
    }
}
