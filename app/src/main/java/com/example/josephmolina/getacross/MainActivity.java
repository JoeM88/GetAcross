package com.example.josephmolina.getacross;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.example.josephmolina.getacross.Fragments.PhotoTranslateFragment;
import com.example.josephmolina.getacross.Fragments.TextTranslateFragment;
import com.example.josephmolina.getacross.Fragments.VoiceTranslateFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

//    final TextTranslateFragment textTranslateFragment = new TextTranslateFragment();
//    final PhotoTranslateFragment photoTranslateFragment = new PhotoTranslateFragment();
//    final VoiceTranslateFragment voiceTranslateFragment = new VoiceTranslateFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_photo_translate:
                       //displayFragment(photoTranslateFragment);
                        displayFragment(new PhotoTranslateFragment());
                        return true;
                    case R.id.navigation__voice_translate:
                        //displayFragment(voiceTranslateFragment);
                        displayFragment(new VoiceTranslateFragment());
                        return true;
                    case R.id.navigation_text_translate:
                        //displayFragment(textTranslateFragment);
                        displayFragment(new TextTranslateFragment());
                        return true;
                    default:
                        displayFragment(new TextTranslateFragment());
                        //displayFragment(textTranslateFragment);
                        break;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            displayFragment(new TextTranslateFragment());
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void displayFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
