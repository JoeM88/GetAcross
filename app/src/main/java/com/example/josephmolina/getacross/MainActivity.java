package com.example.josephmolina.getacross;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.example.josephmolina.getacross.Fragments.PhotoTranslateFragment;
import com.example.josephmolina.getacross.Fragments.SavedChatsFragment;
import com.example.josephmolina.getacross.Fragments.TextTranslateFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    TextTranslateFragment textTranslateFragment = new TextTranslateFragment();
    PhotoTranslateFragment photoTranslateFragment = new PhotoTranslateFragment();
    SavedChatsFragment savedChatsFragment = new SavedChatsFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_photo_translate:
                        displayFragment(photoTranslateFragment);
                        return true;
                    case R.id.navigation__saved_chats:
                        displayFragment(savedChatsFragment);
                        return true;
                    case R.id.navigation_text_translate:
                        displayFragment(textTranslateFragment);
                        return true;
                    default:
                        displayFragment(textTranslateFragment);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_translation:
                return true;

            case R.id.action_speak_translation:
                textTranslateFragment.onActionSpeak();
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
